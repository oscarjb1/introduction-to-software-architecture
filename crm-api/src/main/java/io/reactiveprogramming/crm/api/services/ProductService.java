package io.reactiveprogramming.crm.api.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.reactiveprogramming.commons.exceptions.GenericServiceException;
import io.reactiveprogramming.commons.exceptions.ValidateServiceException;
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
	
	/*
	public byte[] getProductImage(Long id)throws ValidateServiceException, GenericServiceException {
		try {
			Optional<Product> productOpt = productDAO.findById(id);
			if(!productOpt.isPresent()) {
				throw new ValidateServiceException("Product not found");
			}
			Product product = productOpt.get();
			return product.getImage();
		} catch(ValidateServiceException e) {
			throw e;
		}catch (Exception e) {
			e.printStackTrace();
			throw new GenericServiceException("Internal server error");
		}
	}
	*/
}
