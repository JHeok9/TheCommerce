package com.theCommerce.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
		// given
	    Users user = new Users();
	    user.setId("abcd");
	    user.setPw("abcd");

	    // when
	    String result = userService.join(user);

	    // then
	    assertThat(result).isEqualTo("회원 가입이 성공적으로 완료되었습니다.");
	}
	
	@Test
	public void 중복_회원_예외() {
		// 회원 1
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
		
		assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
		
	}
	
	@Test
	public void 회원_목록_조회() {
		
	}
}
