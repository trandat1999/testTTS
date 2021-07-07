package com.tranhuudat.manager.dto;

import com.tranhuudat.manager.domain.Supplier;

import lombok.Data;

@Data
public class SupplierDto {
	private long id;
	private String name;

	private String phoneNumber;

	private String email;

	private String address;

	private String des;

	public SupplierDto() {
		super();
	}

	public SupplierDto(Supplier entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.phoneNumber = entity.getPhoneNumber();
		this.email = entity.getEmail();
		this.address = entity.getAddress();
		this.des = entity.getDes();
	}
}
