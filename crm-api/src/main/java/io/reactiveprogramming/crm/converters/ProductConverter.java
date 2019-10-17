package io.reactiveprogramming.crm.converters;

import io.reactiveprogramming.crm.converters.utils.AbstractConverter;
import io.reactiveprogramming.crm.dto.ProductDTO;
import io.reactiveprogramming.crm.entity.Product;

public class ProductConverter extends AbstractConverter<Product, ProductDTO> {

	@Override
	public Product toEntity(ProductDTO dto) {
		Product p = new Product();
		p.setId(dto.getId());
		p.setName(dto.getName());
		p.setPrice(dto.getPrice());
		return p;
	}

	@Override
	public ProductDTO toDTO(Product entity) {
		ProductDTO p = new ProductDTO();
		p.setId(entity.getId());
		p.setName(entity.getName());
		p.setImage(entity.getImage());
		p.setPrice(entity.getPrice());
		return p;
	}

}
