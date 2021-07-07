package com.tranhuudat.manager.service;

import org.springframework.data.domain.Page;

import com.tranhuudat.manager.dto.FilterDto;
import com.tranhuudat.manager.dto.ProductImportDto;
import com.tranhuudat.manager.dto.ResponseDto;

public interface ProductImportService {
	ResponseDto<ProductImportDto> findById(long id);
	ResponseDto<ProductImportDto> delete(long id);
	Page<ProductImportDto> findPage(FilterDto filter);
	ProductImportDto saveOrUpdate(ProductImportDto dto);
}
