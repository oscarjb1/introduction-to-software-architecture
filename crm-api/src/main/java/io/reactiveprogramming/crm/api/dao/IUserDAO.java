package io.reactiveprogramming.crm.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.reactiveprogramming.crm.entity.Product;
import io.reactiveprogramming.crm.entity.User;

@Repository
public interface IUserDAO extends JpaRepository<User, String>{
	
}
