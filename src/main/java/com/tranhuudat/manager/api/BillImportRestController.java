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

import com.tranhuudat.manager.dto.BillImportDto;
import com.tranhuudat.manager.dto.FilterDto;
import com.tranhuudat.manager.dto.ResponseDto;
import com.tranhuudat.manager.service.BillImportService;

@RestController
@RequestMapping(value = "api/billImport")
public class BillImportRestController {
	@Autowired
	private BillImportService  billImportService;
	
	@PostMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<BillImportDto>> getAll(
			@RequestBody FilterDto searchDto) {

		Page<BillImportDto> services = billImportService.findPage(searchDto);

		return new ResponseEntity<Page<BillImportDto>>(services, HttpStatus.OK);
	}	

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BillImportDto> saveOrUpdate(@RequestBody BillImportDto dto) {

		if (dto == null) {
			return new ResponseEntity<>(new BillImportDto(), HttpStatus.BAD_REQUEST);
		}

		dto = billImportService.saveOrUpdate(dto);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto<BillImportDto>> delete(@PathVariable("id") Long id) {

		ResponseDto<BillImportDto> services = billImportService.delete(id);

		return new ResponseEntity<>(services,HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto<BillImportDto>> get(@PathVariable("id") Long id) {
		ResponseDto<BillImportDto> dto = billImportService.findById(id);
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}
}
