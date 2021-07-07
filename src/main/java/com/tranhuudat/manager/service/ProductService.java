package com.tranhuudat.manager.service;

import org.springframework.data.domain.Page;

import com.tranhuudat.manager.dto.FilterDto;
import com.tranhuudat.manager.dto.ProductDto;
import com.tranhuudat.manager.dto.ResponseDto;

public interface ProductService {
	ResponseDto<ProductDto> findById(long id);
	ResponseDto<ProductDto> delete(long id);
	Page<ProductDto> findPage(FilterDto filter);
	ProductDto saveOrUpdate(ProductDto dto);
}
