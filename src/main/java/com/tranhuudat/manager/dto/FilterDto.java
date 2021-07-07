package com.tranhuudat.manager.dto;

import lombok.Data;

@Data
public class FilterDto {

	private int pageSize;
	private int pageIndex;
	private String keyWord;
}
