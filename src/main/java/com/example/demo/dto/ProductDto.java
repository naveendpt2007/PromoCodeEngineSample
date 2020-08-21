package com.example.demo.dto;

public class ProductDto {
	
	private String productType;
	private int qty;
	private int basePrice;
	private int discountValue;
	private int finalPrice;
	
	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}
	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}
	/**
	 * @return the qty
	 */
	public int getQty() {
		return qty;
	}
	/**
	 * @param qty the qty to set
	 */
	public void setQty(int qty) {
		this.qty = qty;
	}
	/**
	 * @return the price
	 */
	public int getBasePrice() {
		return basePrice;
	}
	/**
	 * @param price the price to set
	 */
	public void setBasPrice(int basePrice) {
		this.basePrice = basePrice;
	}
	/**
	 * @return the discountValue
	 */
	public int getDiscountValue() {
		return discountValue;
	}
	/**
	 * @param discountValue the discountValue to set
	 */
	public void setDiscountValue(int discountValue) {
		this.discountValue = discountValue;
	}
	/**
	 * @return the finalPrice
	 */
	public int getFinalPrice() {
		return finalPrice;
	}
	/**
	 * @param finalPrice the finalPrice to set
	 */
	public void setFinalPrice(int finalPrice) {
		this.finalPrice = finalPrice;
	}
	
	public String toString() {
		return "ShoppingCart : [ProductName : " + productType + ", Quantity : " + qty + ", BasePrice : " + basePrice +
				", DiscountPrice : " + discountValue + ", FinalPrice : " + finalPrice;
	}

}
