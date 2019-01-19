package io.reactiveprogramming.crm.converters.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractConverter<E, D> {

	
	public abstract E toEntity(D dto);
	
	public abstract D toDTO(E entity);
	
	public List<E> toEntityList(List<D> dtos){
		if(dtos == null)return null;
		
		List<E> results = new ArrayList<>();
		for(D dto : dtos) {
			results.add(toEntity(dto));
		}
		return results;
	}
	
	public List<D> toDtoList(List<E> entitys){
		if(entitys == null)return null;
		
		List<D> results = new ArrayList<>();
		for(E entity : entitys) {
			results.add(toDTO(entity));
		}
		return results;
	}
}
