package io.reactiveprogramming.crm.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.reactiveprogramming.crm.api.dao.IProductDAO;
import io.reactiveprogramming.crm.converters.ProductConverter;
import io.reactiveprogramming.crm.dto.ProductDTO;
import io.reactiveprogramming.crm.entity.Product;

@Component
public class ProductService {

	@Autowired
	private IProductDAO productDAO;
	
	public List<ProductDTO> getAllProducts() throws Exception{
		try {
			ProductConverter converter= new ProductConverter();
			return converter.toDtoList(productDAO.findAll());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
	}
	
	public Long createProduct(ProductDTO product) {
		ProductConverter converter = new ProductConverter();
		Product newProduct = converter.toEntity(product);
		newProduct = productDAO.save(newProduct);
		return newProduct.getId();
	}
}
