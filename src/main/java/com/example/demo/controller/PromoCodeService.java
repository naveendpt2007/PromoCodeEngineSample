package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.ProductDto;
import com.example.demo.dto.PromoCodeDto;
import com.example.demo.dto.ShoppingCartDto;
import com.google.gson.Gson;

@Service
public class PromoCodeService {

	List<PromoCodeDto> promoCodeList = new ArrayList<PromoCodeDto>();
	
	public PromoCodeService() {
		String promo1 = "{\"productTypeArray\":[\"A\"],\"minQty\":3,\"percentage\":13,\"isCombined\":false,\"operations\":\"and\"}";
		String promo2 = "{\"productTypeArray\":[\"B\"],\"minQty\":2,\"percentage\":25,\"isCombined\":false,\"operations\":\"and\"}";
//		String promo3 = "{\"productTypeArray\":[\"C\",\"D\"],\"minQty\":1,\"percentage\":13,\"isCombined\":true,\"operations\":\"and\"}";
		Gson gson = new Gson();
		promoCodeList.add(gson.fromJson(promo1, PromoCodeDto.class));
		promoCodeList.add(gson.fromJson(promo2, PromoCodeDto.class));
//		promoCodeList.add(gson.fromJson(promo3, PromoCodeDto.class));
	}
	
	public PromoCodeDto getPromoCode(String productName) {
		for(PromoCodeDto promoCode : promoCodeList) {
			String[] typeArray = promoCode.getProductTypeArray();
			int count = typeArray.length;
			if(count == 1 && typeArray[0].equals(productName) && promoCode.isActive()) {
				return promoCode;
			}else {
				for(String type : typeArray ) {
					if(type.equals(productName)) {
						return promoCode;
					}
				}
			}
		}
		return null;
	}
	
	public String getDependedProductType(PromoCodeDto promoCodeDto) {
		String[] typeArray = promoCodeDto.getProductTypeArray();
		String type = promoCodeDto.getProductTypeArray()[0];
		return typeArray[1];
	}
	
	public void addPromoCode(PromoCodeDto promoCode) {
		promoCodeList.add(promoCode);
	}
	
	public void applyPromo(ShoppingCartDto shoppingCart)  {
		int cartTotal = 0;
		for(ProductDto productDto : shoppingCart.getProductList()) {
			String productType = productDto.getProductType();
			PromoCodeDto promoCodeDto = getPromoCode(productType);
			if(promoCodeDto != null && !promoCodeDto.isCombined()) {
				cartTotal = cartTotal + calDiscount(productDto, promoCodeDto);
			}else if(promoCodeDto != null && promoCodeDto.isCombined() && promoCodeDto.getOperations().equals("and")) {
				String dependedProduct = getDependedProductType(promoCodeDto);
				System.out.println(dependedProduct);
				if(ifDependedProductInCart(shoppingCart, dependedProduct) && !productType.equals(dependedProduct)) {
					int price = productDto.getBasePrice();
					int totalPrice = price * productDto.getQty();
					productDto.setDiscountValue(totalPrice);
					productDto.setFinalPrice(0);
					cartTotal = cartTotal + 0;
				}else if(ifDependedProductInCart(shoppingCart, dependedProduct) && !productType.equals(dependedProduct) && promoCodeDto.isByValue()){
					int price = productDto.getBasePrice();
					int totalPrice = price * productDto.getQty();
					productDto.setDiscountValue(totalPrice);
					productDto.setFinalPrice(promoCodeDto.getValue());
					cartTotal = cartTotal + promoCodeDto.getValue();
				}
			}else {
				int price = productDto.getBasePrice();
				int totalPrice = price * productDto.getQty();
				productDto.setDiscountValue(0);
				productDto.setFinalPrice(totalPrice);
				cartTotal = cartTotal + totalPrice;
			}
		}
		shoppingCart.setTotal(cartTotal);
	}
	
	public boolean ifDependedProductInCart(ShoppingCartDto shoppingCart, String dependedProduct) {
		for(ProductDto productDto : shoppingCart.getProductList()) {
			if(productDto.getProductType().equals(dependedProduct)) {
				return true;
			}
		}
		return false;
	}
	
	public int calDiscount(ProductDto productDto, PromoCodeDto promoCodeDto) {
		int discountPercentage = promoCodeDto.getPercentage();
		int miniQty = promoCodeDto.getMinQty();
		int price = productDto.getBasePrice();
		int totalPrice = price * productDto.getQty();
		int productQty = productDto.getQty();
		int reminder = productQty % miniQty;
		int totalDiscount = 0;
		if(reminder != 0) {
			int q = productQty - reminder;
			int unitT = price * q;
			totalDiscount = totalDiscount + (int)Math.round((((double)discountPercentage ) / 100) * unitT);
		}else {
			totalDiscount = totalDiscount + (int)Math.round((((double)discountPercentage ) / 100) * totalPrice);
		}
		productDto.setDiscountValue(totalDiscount);
		int finalV = totalPrice - totalDiscount;
		productDto.setFinalPrice(finalV);
		return finalV;
//		return totalPrice - totalDiscount;
	}
	
	public static void main(String[] args) {
		
		ShoppingCartDto shopDto = new ShoppingCartDto();
		
		ProductDto prodductDto = new ProductDto();
		prodductDto.setBasPrice(50);
		prodductDto.setProductType("A");
		prodductDto.setQty(3);
		
		ProductDto prodductDto2 = new ProductDto();
		prodductDto2.setBasPrice(30);
		prodductDto2.setProductType("B");
		prodductDto2.setQty(5);
		
		ProductDto prodductDto3 = new ProductDto();
		prodductDto3.setBasPrice(20);
		prodductDto3.setProductType("C");
		prodductDto3.setQty(1);
		
		ProductDto prodductDto4 = new ProductDto();
		prodductDto4.setBasPrice(15);
		prodductDto4.setProductType("D");
		prodductDto4.setQty(1);
		
		List<ProductDto> shoppingCartList = new ArrayList<ProductDto>();
		shoppingCartList.add(prodductDto);
		shoppingCartList.add(prodductDto2);
		shoppingCartList.add(prodductDto3);
		shoppingCartList.add(prodductDto4);
		shopDto.setProductList(shoppingCartList);
		
		PromoCodeService controller = new PromoCodeService();
		PromoCodeDto promoCode = new PromoCodeDto();
//		promoCode.setActive(true);
		promoCode.setCombined(false);
		promoCode.setMinQty(1);
		promoCode.setPercentage(0);
		promoCode.setValue(30);
		promoCode.setByValue(true);;
		promoCode.setProductTypeArray(new String[] {"C","D"});
		
		int value = 5 % 3;
		System.out.println(value);
		
		PromoCodeDto dto = controller.getPromoCode("A");
		System.out.println(dto);
		controller.addPromoCode(promoCode);
		
		controller.applyPromo(shopDto);
		for(ProductDto productDto : shopDto.getProductList()) {
			System.out.println(productDto.toString());
		}
		System.out.println(shopDto.getTotal());
		

	}

}
