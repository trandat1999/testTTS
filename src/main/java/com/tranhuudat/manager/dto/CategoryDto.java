package com.tranhuudat.manager.dto;

import java.util.List;

import com.tranhuudat.manager.domain.Category;

import lombok.Data;

@Data
public class CategoryDto {

	private long id;

	private String name;

	private List<ProductDto> products;

	public CategoryDto() {
		super();
	}

	public CategoryDto(Category entity) {
		this.id = entity.getId();
		this.name = entity.getName();
	}
}
