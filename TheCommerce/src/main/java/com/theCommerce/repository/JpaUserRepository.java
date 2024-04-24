package com.theCommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.theCommerce.domain.Users;


public interface JpaUserRepository extends JpaRepository<Users, String>, UserRepository {
	
	@Override
	Optional<Users> findById(String id);
}
