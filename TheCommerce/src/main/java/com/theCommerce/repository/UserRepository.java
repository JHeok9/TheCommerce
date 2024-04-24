package com.theCommerce.repository;

import java.util.List;
import java.util.Optional;

import com.theCommerce.domain.Users;


public interface UserRepository {
	Users save(Users member);
	Optional<Users> findById(String id);
	List<Users> findAll();

}
