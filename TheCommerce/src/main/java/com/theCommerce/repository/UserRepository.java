package com.theCommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.theCommerce.domain.Users;


public interface UserRepository {
	Users save(Users member);
	Optional<Users> findById(String id);
	Page<Users> findAll(Pageable pageable);

}
