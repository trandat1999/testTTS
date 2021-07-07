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

import com.tranhuudat.manager.dto.CategoryDto;
import com.tranhuudat.manager.dto.FilterDto;
import com.tranhuudat.manager.dto.ResponseDto;
import com.tranhuudat.manager.service.CategoryService;

@RestController
@RequestMapping(value = "api/category")
public class CategoryRestController {
	@Autowired
	private CategoryService  categoryService;
	
	@PostMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<CategoryDto>> getAll(
			@RequestBody FilterDto searchDto) {

		Page<CategoryDto> services = categoryService.findPage(searchDto);

		return new ResponseEntity<Page<CategoryDto>>(services, HttpStatus.OK);
	}	

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CategoryDto> saveOrUpdate(@RequestBody CategoryDto dto) {

		if (dto == null) {
			return new ResponseEntity<>(new CategoryDto(), HttpStatus.BAD_REQUEST);
		}

		dto = categoryService.saveOrUpdate(dto);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto<CategoryDto>> delete(@PathVariable("id") Long id) {

		ResponseDto<CategoryDto> services = categoryService.delete(id);

		return new ResponseEntity<>(services,HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto<CategoryDto>> get(@PathVariable("id") Long id) {
		ResponseDto<CategoryDto> dto = categoryService.findById(id);
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}
}
