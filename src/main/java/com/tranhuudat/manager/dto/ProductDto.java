package com.tranhuudat.manager.dto;

import java.util.ArrayList;
import java.util.List;

import com.tranhuudat.manager.domain.Category;
import com.tranhuudat.manager.domain.Product;

import lombok.Data;

@Data
public class ProductDto {
	private long id;
	private String name;
	private String shortDes;
	private String des;
	private float price;
	private List<CategoryDto> categories;

	public ProductDto() {
		super();
	}

	public ProductDto(Product entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.shortDes = entity.getShortDes();
		this.des = entity.getDes();
		this.price = entity.getPrice();
		if (entity.getCategories() != null && entity.getCategories().size() > 0) {
			List<CategoryDto> list = new ArrayList<CategoryDto>();
			for (Category cate : entity.getCategories()) {
				list.add(new CategoryDto(cate));
			}
			this.categories = list;
		}
	}
}
