package io.reactiveprogramming.crm.api.rest;

import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.reactiveprogramming.crm.api.rest.util.WrapperResponse;
import io.reactiveprogramming.crm.api.services.ProductService;
import io.reactiveprogramming.crm.dto.ProductDTO;
import io.reactiveprogramming.crm.entity.Product;
import io.reactiveprogramming.crm.rabbit.RabbitSender;

@RestController("products")
public class ProductREST {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private RabbitSender sender;
	
	@GetMapping
	public ResponseEntity<WrapperResponse> getAllProducts() {
		try {
			sender.send();
			List<ProductDTO> products = productService.getAllProducts();
			WrapperResponse response = new WrapperResponse(true, "Consulta exitosa", products);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch(Exception e) {
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
