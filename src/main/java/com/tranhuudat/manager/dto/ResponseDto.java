package com.tranhuudat.manager.dto;

import com.tranhuudat.manager.type.YESNO;

import lombok.Data;

@Data
public class ResponseDto<T> {

	private String message;
	private T object;
	private YESNO status;
}
