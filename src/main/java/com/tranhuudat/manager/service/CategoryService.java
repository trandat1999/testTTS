package com.tranhuudat.manager.service;

import org.springframework.data.domain.Page;

import com.tranhuudat.manager.dto.CategoryDto;
import com.tranhuudat.manager.dto.FilterDto;
import com.tranhuudat.manager.dto.ResponseDto;

public interface CategoryService {
	ResponseDto<CategoryDto> findById(long id);
	ResponseDto<CategoryDto> delete(long id);
	Page<CategoryDto> findPage(FilterDto filter);
	CategoryDto saveOrUpdate(CategoryDto dto);
}
