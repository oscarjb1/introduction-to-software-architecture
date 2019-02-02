package io.reactiveprogramming.crm.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.reactiveprogramming.commons.exceptions.GenericServiceException;
import io.reactiveprogramming.commons.exceptions.ValidateServiceException;
import io.reactiveprogramming.crm.api.dao.IOrderDAO;
import io.reactiveprogramming.crm.converters.SaleOrderConverter;
import io.reactiveprogramming.crm.dto.SaleOrderDTO;
import io.reactiveprogramming.crm.entity.SaleOrder;

@Service
public class OrderService {

	@Autowired
	private IOrderDAO orderDAO;
	
	public List<SaleOrderDTO> getAllOrders() throws GenericServiceException, ValidateServiceException{
		try {
			List<SaleOrder> orders = orderDAO.findAll();
			SaleOrderConverter orderConverter = new SaleOrderConverter();
			List<SaleOrderDTO> ordersDTO = orderConverter.toDtoList(orders);
			return ordersDTO;
		}catch (Exception e) {
			throw new GenericServiceException("Internal Server Error");
		}
	}
}
