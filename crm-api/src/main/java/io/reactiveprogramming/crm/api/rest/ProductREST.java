package io.reactiveprogramming.crm.api.rest;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.reactiveprogramming.commons.rest.WrapperResponse;
import io.reactiveprogramming.crm.api.services.ProductService;
import io.reactiveprogramming.crm.dto.ProductDTO;

@RestController
@RequestMapping("products")
public class ProductREST {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductREST.class);

	@Autowired
	private ProductService productService;
	
	@GetMapping
	public ResponseEntity<WrapperResponse<ProductDTO>> getAllProducts() {
		try {
			List<ProductDTO> products = productService.getAllProducts();
			WrapperResponse response = new WrapperResponse(true, "Consulta exitosa", products);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			WrapperResponse response = new WrapperResponse(false, "Error al consultar los productos");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	
	@PostMapping 
	public ResponseEntity<WrapperResponse> createProduct(@RequestBody ProductDTO product){
		try {
			System.out.println("Request ==> " + ReflectionToStringBuilder.toString(product, ToStringStyle.MULTI_LINE_STYLE));
			Long id = productService.createProduct(product);
			WrapperResponse response = new WrapperResponse(true, "Producto guardado exitosamente", id);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch(Exception e) {
			WrapperResponse response = new WrapperResponse(false, "Error al guardar el productos");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
