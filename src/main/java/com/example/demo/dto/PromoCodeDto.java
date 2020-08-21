package com.example.demo.dto;

import java.util.Arrays;

public class PromoCodeDto {
	
	private String[] productTypeArray;
	private int minQty;
	private int percentage;
	private boolean isCombined;
	private String operations = "";
	private boolean isActive = true;
	
	/**
	 * @return the productTypeArray
	 */
	public String[] getProductTypeArray() {
		return productTypeArray;
	}
	/**
	 * @param productTypeArray the productTypeArray to set
	 */
	public void setProductTypeArray(String[] productTypeArray) {
		this.productTypeArray = productTypeArray;
	}
	/**
	 * @return the minQty
	 */
	public int getMinQty() {
		return minQty;
	}
	/**
	 * @param minQty the minQty to set
	 */
	public void setMinQty(int minQty) {
		this.minQty = minQty;
	}
	/**
	 * @return the percentage
	 */
	public int getPercentage() {
		return percentage;
	}
	/**
	 * @param percentage the percentage to set
	 */
	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	/**
	 * @return the isCombined
	 */
	public boolean isCombined() {
		return isCombined;
	}
	/**
	 * @param isCombined the isCombined to set
	 */
	public void setCombined(boolean isCombined) {
		this.isCombined = isCombined;
	}
	/**
	 * @return the operations
	 */
	public String getOperations() {
		return operations;
	}
	/**
	 * @param operations the operations to set
	 */
	public void setOperations(String operations) {
		this.operations = operations;
	}
	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}
	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public String toString() {
		return "PromoCode : " + Arrays.toString(productTypeArray) + " : " + minQty + " : " + percentage + " : " + isCombined + " : " + operations;
	}

}
