package com.tranhuudat.manager.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tranhuudat.manager.dto.SupplierDto;
import com.tranhuudat.manager.dto.FilterDto;
import com.tranhuudat.manager.dto.ResponseDto;
import com.tranhuudat.manager.service.SupplierService;

@RestController
@RequestMapping(value = "api/supplier")
public class SupplierRestController {
	@Autowired
	private SupplierService  supplierService;
	
	@PostMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<SupplierDto>> getAll(
			@RequestBody FilterDto searchDto) {

		Page<SupplierDto> services = supplierService.findPage(searchDto);

		return new ResponseEntity<Page<SupplierDto>>(services, HttpStatus.OK);
	}	

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SupplierDto> saveOrUpdates(@RequestBody SupplierDto dto) {

		if (dto == null) {
			return new ResponseEntity<>(new SupplierDto(), HttpStatus.BAD_REQUEST);
		}

		dto = supplierService.saveOrUpdate(dto);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto<SupplierDto>> deleteById(@PathVariable("id") Long id) {

		ResponseDto<SupplierDto> services = supplierService.delete(id);

		return new ResponseEntity<>(services,HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto<SupplierDto>> get(@PathVariable("id") Long id) {
		ResponseDto<SupplierDto> dto = supplierService.findById(id);
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}
}
