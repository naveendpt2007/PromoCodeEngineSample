package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.controller.PromoCodeService;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.ShoppingCartDto;

@SpringBootTest
public class PromoEngineApplicationTests {
	
	@Autowired
	private PromoCodeService promoCodeService;
	
	@Before
	public void before() {
		promoCodeService = new PromoCodeService();
	}

	@Test
	public void testGetPromoCode() {
		String testName = promoCodeService.getPromoCode("A").toString();
		Assert.assertEquals("PromoCode : [A] : 3 : 13 : false : and", testName);
	}
	
	@Test
	public void testSingleProduct() {
		ShoppingCartDto shopDto = new ShoppingCartDto();
		
		ProductDto prod = new ProductDto();
		prod.setBasPrice(50);
		prod.setProductType("A");
		prod.setQty(3);
		
		List<ProductDto> shoppingCartList = new ArrayList<ProductDto>();
		shoppingCartList.add(prod);
		shopDto.setProductList(shoppingCartList);
		
		promoCodeService.applyPromo(shopDto);
		Assert.assertEquals(130, shopDto.getTotal());
	}
	
	@Test
	public void testMultipleProductTotalValue() {
		ShoppingCartDto shopDto = new ShoppingCartDto();
		
		ProductDto prod = new ProductDto();
		prod.setBasPrice(50);
		prod.setProductType("A");
		prod.setQty(3);
		
		ProductDto prod2 = new ProductDto();
		prod2.setBasPrice(30);
		prod2.setProductType("B");
		prod2.setQty(2);
		
		List<ProductDto> shoppingCartList = new ArrayList<ProductDto>();
		shoppingCartList.add(prod);
		shoppingCartList.add(prod2);
		shopDto.setProductList(shoppingCartList);
		
		promoCodeService.applyPromo(shopDto);
		Assert.assertEquals(175, shopDto.getTotal());
	}
	
	@Test
	public void testEachProductFinalValue() {
		ShoppingCartDto shopDto = new ShoppingCartDto();
		
		ProductDto prod = new ProductDto();
		prod.setBasPrice(50);
		prod.setProductType("A");
		prod.setQty(3);
		
		ProductDto prod2 = new ProductDto();
		prod2.setBasPrice(30);
		prod2.setProductType("B");
		prod2.setQty(2);
		
		List<ProductDto> shoppingCartList = new ArrayList<ProductDto>();
		shoppingCartList.add(prod);
		shoppingCartList.add(prod2);
		shopDto.setProductList(shoppingCartList);
		
		promoCodeService.applyPromo(shopDto);
		Assert.assertEquals(130, shopDto.getProduct(0).getFinalPrice());
		Assert.assertEquals(45, shopDto.getProduct(1).getFinalPrice());
	}
	
	
	@Test
	public void test1() {
		ShoppingCartDto shopDto = new ShoppingCartDto();
		
		ProductDto prod = new ProductDto();
		prod.setBasPrice(50);
		prod.setProductType("A");
		prod.setQty(5);
		
		ProductDto prod2 = new ProductDto();
		prod2.setBasPrice(30);
		prod2.setProductType("B");
		prod2.setQty(2);
		
		List<ProductDto> shoppingCartList = new ArrayList<ProductDto>();
		shoppingCartList.add(prod);
		shoppingCartList.add(prod2);
		shopDto.setProductList(shoppingCartList);
		
		promoCodeService.applyPromo(shopDto);
		Assert.assertEquals(230, shopDto.getProduct(0).getFinalPrice());
		Assert.assertEquals(45, shopDto.getProduct(1).getFinalPrice());
	}
	
	@Test
	public void test2() {
		ShoppingCartDto shopDto = new ShoppingCartDto();
		
		ProductDto prod = new ProductDto();
		prod.setBasPrice(50);
		prod.setProductType("A");
		prod.setQty(5);
		
		ProductDto prod2 = new ProductDto();
		prod2.setBasPrice(30);
		prod2.setProductType("B");
		prod2.setQty(5);
		
		List<ProductDto> shoppingCartList = new ArrayList<ProductDto>();
		shoppingCartList.add(prod);
		shoppingCartList.add(prod2);
		shopDto.setProductList(shoppingCartList);
		
		promoCodeService.applyPromo(shopDto);
		Assert.assertEquals(230, shopDto.getProduct(0).getFinalPrice());
		Assert.assertEquals(120, shopDto.getProduct(1).getFinalPrice());
	}
	

}
