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
import com.tranhuudat.manager.dto.ProductDto;
import com.tranhuudat.manager.dto.ResponseDto;
import com.tranhuudat.manager.service.ProductService;

@RestController
@RequestMapping(value = "api/product")
public class ProductRestController {
	@Autowired
	private ProductService  productService;
	
	@PostMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<ProductDto>> getAll(
			@RequestBody FilterDto searchDto) {

		Page<ProductDto> services = productService.findPage(searchDto);

		return new ResponseEntity<Page<ProductDto>>(services, HttpStatus.OK);
	}	

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDto> saveOrUpdates(@RequestBody ProductDto dto) {

		if (dto == null) {
			return new ResponseEntity<>(new ProductDto(), HttpStatus.BAD_REQUEST);
		}

		dto = productService.saveOrUpdate(dto);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto<ProductDto>> deleteById(@PathVariable("id") Long id) {

		ResponseDto<ProductDto> services = productService.delete(id);

		return new ResponseEntity<>(services,HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto<ProductDto>> get(@PathVariable("id") Long id) {
		ResponseDto<ProductDto> dto = productService.findById(id);
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}
}
