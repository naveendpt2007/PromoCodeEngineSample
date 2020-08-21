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
		String promo3 = "{\"productTypeArray\":[\"C\",\"D\"],\"minQty\":1,\"percentage\":13,\"isCombined\":true,\"operations\":\"and\"}";
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
	
	public void addPromoCode(PromoCodeDto promoCode) {
		promoCodeList.add(promoCode);
	}
	
	public void applyPromo(ShoppingCartDto shoppingCart)  {
		int cartTotal = 0;
		for(ProductDto productDto : shoppingCart.getProductList()) {
			String productType = productDto.getProductType();
			PromoCodeDto promoCodeDto = getPromoCode(productType);
//			if(promoCodeDto.getMinQty() == productDto.getQty()) {
//				cartTotal = cartTotal + calDiscount(productDto, promoCodeDto);
//			}else if (productDto.getQty() > promoCodeDto.getMinQty() ) {
//				cartTotal = cartTotal + calDiscount(productDto, promoCodeDto);
			if(promoCodeDto != null) {
				cartTotal = cartTotal + calDiscount(productDto, promoCodeDto);
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
		
		List<ProductDto> shoppingCartList = new ArrayList<ProductDto>();
		shoppingCartList.add(prodductDto);
		shoppingCartList.add(prodductDto2);
//		shoppingCartList.add(prodductDto3);
		shopDto.setProductList(shoppingCartList);
		
		PromoCodeService controller = new PromoCodeService();
		PromoCodeDto promoCode = new PromoCodeDto();
//		promoCode.setActive(true);
		promoCode.setCombined(false);
		promoCode.setMinQty(1);
		promoCode.setPercentage(10);
		promoCode.setProductTypeArray(new String[] {"C"});
		
		int value = 5 % 3;
		System.out.println(value);
		
		PromoCodeDto dto = controller.getPromoCode("A");
		System.out.println(dto);
//		controller.addPromoCode(promoCode);
		
		controller.applyPromo(shopDto);
		for(ProductDto productDto : shopDto.getProductList()) {
			System.out.println(productDto.toString());
		}
		System.out.println(shopDto.getTotal());
		

	}

}
