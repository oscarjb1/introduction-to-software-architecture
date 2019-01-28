package io.reactiveprogramming.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.reactiveprogramming.security.entity.User;


@Repository
public interface IUserDAO extends JpaRepository<User, String>{
	
}
