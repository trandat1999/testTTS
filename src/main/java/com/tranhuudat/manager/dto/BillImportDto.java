package com.tranhuudat.manager.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tranhuudat.manager.domain.BillImport;
import com.tranhuudat.manager.domain.ProductImport;
import com.tranhuudat.manager.type.Status;

import lombok.Data;

@Data
public class BillImportDto {

	private long id;
	private Date dateImport;
	private String note;
	private Status status;

	private List<ProductImportDto> productImports;

	private SupplierDto supplier;

	public BillImportDto() {
		super();
	}

	public BillImportDto(BillImport entity) {
		this.id = entity.getId();
		this.dateImport = entity.getDateImport();
		this.note = entity.getNote();
		this.status = entity.getStatus();
		if (entity.getSupplier() != null) {
			this.supplier = new SupplierDto(entity.getSupplier());
		}
		if (entity.getProductImports() != null && entity.getProductImports().size() > 0) {
			List<ProductImportDto> list = new ArrayList<>();
			for (ProductImport pi : entity.getProductImports()) {
				list.add(new ProductImportDto(pi));
			}
			this.productImports = list;
		}
	}

}
