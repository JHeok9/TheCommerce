package com.theCommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.theCommerce.domain.Users;
import com.theCommerce.repository.UserRepository;


@Transactional
public class UserService {
	
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// 회원가입
	public String join(Users member) {
		validateDuplicateMember(member); // 중복회원 검증
		
		userRepository.save(member);
		return "회원 가입이 성공적으로 완료되었습니다.";
	}

	// 중복회원 검증
	private void validateDuplicateMember(Users member) {
		userRepository.findById(member.getId()).ifPresent(m -> {
			throw new IllegalStateException("이미 존재하는 회원입니다");
		});
	}
	
	// 전체 회원 조회
	public Page<Users> findUsers(Pageable pageable){
		return userRepository.findAll(pageable);
	}
	
	// 회원조회
	public Optional<Users> findOne(String userId){
		return userRepository.findById(userId);
	}
	
	

}
