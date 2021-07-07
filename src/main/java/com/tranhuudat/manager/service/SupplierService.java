package com.tranhuudat.manager.service;

import org.springframework.data.domain.Page;

import com.tranhuudat.manager.dto.FilterDto;
import com.tranhuudat.manager.dto.ResponseDto;
import com.tranhuudat.manager.dto.SupplierDto;

public interface SupplierService {
	ResponseDto<SupplierDto> findById(long id);
	ResponseDto<SupplierDto> delete(long id);
	Page<SupplierDto> findPage(FilterDto filter);
	SupplierDto saveOrUpdate(SupplierDto dto);
}
