package com.tranhuudat.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tranhuudat.manager.domain.Category;
import com.tranhuudat.manager.domain.Product;
import com.tranhuudat.manager.dto.CategoryDto;
import com.tranhuudat.manager.dto.FilterDto;
import com.tranhuudat.manager.dto.ProductDto;
import com.tranhuudat.manager.dto.ResponseDto;
import com.tranhuudat.manager.repository.CategoryRepository;
import com.tranhuudat.manager.repository.ProductRepository;
import com.tranhuudat.manager.service.ProductService;
import com.tranhuudat.manager.type.YESNO;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	public EntityManager manager;
	
	@Autowired
	private CategoryRepository categoryRepository;


	@Cacheable("product")
	@Override
	public ResponseDto<ProductDto> findById(long id) {
		ResponseDto<ProductDto> res= new ResponseDto<>();
		Product entity= productRepository.findById(id).get();
		if(entity!=null) {
			res.setStatus(YESNO.YES);
			res.setObject(new ProductDto(entity));
		}else {
			res.setStatus(YESNO.NO);
			res.setMessage("not found");
		}
		return res;
	}

	@CacheEvict("product")
	@Override
	public ResponseDto<ProductDto> delete(long id) {
		ResponseDto<ProductDto> res= new ResponseDto<>();
		Product entity= productRepository.findById(id).get();
		if(entity!=null) {
			productRepository.delete(entity);
			res.setStatus(YESNO.YES);
			res.setObject(new ProductDto(entity));
			res.setMessage("delete success");
		}else {
			res.setStatus(YESNO.NO);
			res.setMessage("not found");
		}
		return res;
	}

	@Override
	public Page<ProductDto> findPage(FilterDto filter) {
		if(filter!=null) {
			String SQL = " SELECT new com.tranhuudat.manager.dto.ProductDto(s) from Product s WHERE 1=1 ";
			String countSQL = " SELECT COUNT(s.id) from Product s WHERE 1=1 ";
			String whereClause = "";
			Query q = manager.createQuery(SQL + whereClause, ProductDto.class);
			Query qCount = manager.createQuery(countSQL + whereClause);
			Long count = (long) qCount.getSingleResult();
			if(filter.getPageIndex()>-1 && filter.getPageSize()>0) {
				int startPosition = filter.getPageIndex() * filter.getPageSize();
				q.setFirstResult(startPosition);
				q.setMaxResults(filter.getPageSize());
			}else {
				q.setFirstResult(0);
				if(count!=null && count>0) {
					q.setMaxResults(count.intValue());
					filter.setPageSize(count.intValue());
				}
				else {
					q.setMaxResults(10);
					filter.setPageSize(10);
				}
			}
			@SuppressWarnings("unchecked")
			List<ProductDto> entities = q.getResultList();
			Pageable pageable = PageRequest.of(filter.getPageIndex(), filter.getPageSize());
			Page<ProductDto> result = new PageImpl<ProductDto>(entities, pageable, count);

			return result;
		}else {
			return null;
		}
	}

	@CachePut(value = "product")
	@Override
	public ProductDto saveOrUpdate(ProductDto dto) {
		if (dto != null) {

			Product entity = null;
			if (dto.getId() > 0) {
				entity = productRepository.findById(dto.getId()).get();
			}
			if (entity == null) {
				entity = new Product();
			}
			entity.setName(dto.getName());
			entity.setDes(dto.getDes());
			entity.setPrice(dto.getPrice());
			entity.setShortDes(dto.getShortDes());
			
			if(dto.getCategories()!=null && dto.getCategories().size()>0) {
				List<Category> categories = new ArrayList<Category>();
				for(CategoryDto cate: dto.getCategories()) {
					if(cate.getId()>0) {
						categories.add(categoryRepository.findById(cate.getId()).get());
					}
				}
				entity.setCategories(categories);
			}
			entity = productRepository.save(entity);
			if (entity != null) {
				return new ProductDto(entity);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

}
