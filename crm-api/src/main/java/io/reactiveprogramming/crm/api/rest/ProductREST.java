package io.reactiveprogramming.crm.api.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.reactiveprogramming.commons.rest.WrapperResponse;
import io.reactiveprogramming.crm.api.services.ProductService;
import io.reactiveprogramming.crm.dto.ProductDTO;
import io.reactiveprogramming.crm.rabbit.RabbitSender;

@RestController
@RequestMapping("products")
public class ProductREST {
	
	private static final Logger logger = Logger.getLogger(ProductREST.class.getCanonicalName());

	@Autowired
	private ProductService productService;
	
//	@Autowired
//	private RabbitSender sender;
	
	@GetMapping
	public ResponseEntity<WrapperResponse> getAllProducts() {
		try {
//			sender.send();
			List<ProductDTO> products = productService.getAllProducts();
			WrapperResponse response = new WrapperResponse(true, "Consulta exitosa", products);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, e.getMessage());
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
	
	@RequestMapping(value = "thumbnail/{productId}", method = RequestMethod.GET)
	public void getImageAsByteArray(HttpServletResponse response, @PathVariable("productId") Long productId) throws IOException {
		try {
			byte[] image = productService.getProductImage(productId);
		    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		    response.getOutputStream().write(image);
		} catch (Exception e) {
			response.setStatus(500);
		}
	}
	
}
