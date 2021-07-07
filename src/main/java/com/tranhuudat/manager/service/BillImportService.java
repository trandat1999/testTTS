package com.tranhuudat.manager.service;

import org.springframework.data.domain.Page;

import com.tranhuudat.manager.dto.BillImportDto;
import com.tranhuudat.manager.dto.FilterDto;
import com.tranhuudat.manager.dto.ResponseDto;

public interface BillImportService {
	ResponseDto<BillImportDto> findById(long id);
	ResponseDto<BillImportDto> delete(long id);
	Page<BillImportDto> findPage(FilterDto filter);
	BillImportDto saveOrUpdate(BillImportDto dto);
}
