package com.example.demo.dto;

import java.util.List;

public class ShoppingCartDto {
	
	private int total;
	private List<ProductDto> productList;
	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	/**
	 * @return the productList
	 */
	public List<ProductDto> getProductList() {
		return productList;
	}
	/**
	 * @param productList the productList to set
	 */
	public void setProductList(List<ProductDto> productList) {
		this.productList = productList;
	}
	
	/**
	 * @return the ProductDto
	 */
	public ProductDto getProduct(int index) {
		if(productList == null ){
			throw new NullPointerException(); //TODO: check wht the exception should be???
		}
		
		return productList.get(index);
	}

}
