package com.theCommerce.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.theCommerce.domain.Users;
import com.theCommerce.repository.UserRepository;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

	@Autowired UserService userService;
	@Autowired UserRepository userRepository;
	
	@Test
	void 회원가입() {
		// 회원가입 정보
	    Users user = new Users();
	    user.setId("abcd");
	    user.setPw("abcd");

	    // 회원가입
	    String result = userService.join(user);

	    // 성공여부
	    assertThat(result).isEqualTo("회원 가입이 성공적으로 완료되었습니다.");
	}
	
	@Test
	public void 중복_회원_예외() {
		// 회원1
		Users user1 = new Users();
		user1.setId("abcd");
		user1.setPw("abcd");

		// 회원2
		Users user2 = new Users();
		user2.setId("abcd");
		user2.setPw("abcd");
		
		// 회원1 회원가입후 회원2 회원가입시도
		userService.join(user1);
		IllegalStateException e = assertThrows(IllegalStateException.class, () -> userService.join(user2));
		
		// 성공여부
		assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
		
	}
	
	@Test
	public void 회원_목록_조회() {
		// 정렬을 위한 Sort 객체 생성
	    Sort sortOptions = Sort.by("joinDate").ascending(); 
	    
	    // 페이지네이션을 위한 Pageable 객체 생성
	    Pageable pageable = PageRequest.of(0, 10, sortOptions);
		
	    // 회원 목록 조회
	    Page<Users> userList = userService.findUsers(pageable);
	    
	    // 조회된 회원 목록이 비어있지 않은지 확인
	    assertThat(userList).isNotEmpty();
	}
	
	@Test
	public void 회원_수정() {
		// 회원 생성
		Users user = new Users();
	    user.setId("abcd");
	    user.setPw("abcd");
	    user.setName("abcd");
	    userService.join(user);
	    
	    // 회원 수정
	    Users updateForm = new Users();
		updateForm.setId("abcd");
		updateForm.setName("abcd123");
		userService.updateUser(updateForm);
		
		// 수정된 회원 조회
	    Optional<Users> updatedUserOptional = userService.findOne("abcd");
	    assertThat(updatedUserOptional).isNotEmpty();
	    Users updatedUser = updatedUserOptional.get();

	    // 수정된 회원의 이름이 예상과 일치하는지 확인
	    assertThat(updatedUser.getName()).isEqualTo("abcd123");
		
	}
}
