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

import com.tranhuudat.manager.dto.FilterDto;
import com.tranhuudat.manager.dto.ProductImportDto;
import com.tranhuudat.manager.dto.ResponseDto;
import com.tranhuudat.manager.service.ProductImportService;

@RestController
@RequestMapping(value = "api/productImport")
public class ProductImportRestController {
	@Autowired
	private ProductImportService  ProductImportService;
	
	@PostMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<ProductImportDto>> getAll(
			@RequestBody FilterDto searchDto) {

		Page<ProductImportDto> services = ProductImportService.findPage(searchDto);

		return new ResponseEntity<Page<ProductImportDto>>(services, HttpStatus.OK);
	}	

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductImportDto> saveOrUpdate(@RequestBody ProductImportDto dto) {

		if (dto == null) {
			return new ResponseEntity<>(new ProductImportDto(), HttpStatus.BAD_REQUEST);
		}

		dto = ProductImportService.saveOrUpdate(dto);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto<ProductImportDto>> delete(@PathVariable("id") Long id) {

		ResponseDto<ProductImportDto> services = ProductImportService.delete(id);

		return new ResponseEntity<>(services,HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto<ProductImportDto>> get(@PathVariable("id") Long id) {
		ResponseDto<ProductImportDto> dto = ProductImportService.findById(id);
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}
}
