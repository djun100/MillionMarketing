package com.yameng.bean;

import java.io.Serializable;
//方便通过其他类传递person对象给Variable中，而不用把person的每个属性都传递给Variable一遍
public class Commodity implements Serializable{
	@Override
	public String toString() {
		return "Commodity [id=" + id + ", seller=" + seller + ", title="
				+ title + ", description=" + description + ", price=" + price
				+ ", profit=" + profit + ", stock=" + stock + ", sales="
				+ sales + ", pictures=" + pictures + ", begin=" + begin
				+ ", end=" + end + "]";
	}
	private static final long serialVersionUID = 1L;  
	String id="";
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	String seller="";
	String title="";
	String description="";
	String price="";
	String profit="";
	String stock="";
	String sales="";
	public String getSales() {
		return sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}
	String pictures="";
	String begin="";
	String end="";
	public String getBegin() {
		return begin;
	}
	public void setBegin(String begin) {
		this.begin = begin;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String user) {
		this.seller = user;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getProfit() {
		return profit;
	}
	public void setProfit(String profit) {
		this.profit = profit;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getPictures() {
		return pictures;
	}
	public void setPictures(String pictures) {
		this.pictures = pictures;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
