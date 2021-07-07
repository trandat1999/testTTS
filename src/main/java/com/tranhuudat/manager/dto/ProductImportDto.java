package com.tranhuudat.manager.dto;

import com.tranhuudat.manager.domain.ProductImport;

import lombok.Data;

@Data
public class ProductImportDto {
	private long id;
	private double priceImport;
	private int quantity;

	private ProductDto product;

	private BillImportDto billImport;

	public ProductImportDto() {
		super();
	}

	public ProductImportDto(ProductImport entity) {
		this.id = entity.getId();
		this.priceImport = entity.getPriceImport();
		this.quantity = entity.getQuantity();
		if (entity.getProduct() != null) {
			this.product = new ProductDto(entity.getProduct());
		}
		if (entity.getBillImport() != null) {
			BillImportDto bill = new BillImportDto();
			bill.setId(entity.getBillImport().getId());
			this.billImport=bill;
		}
	}
}
