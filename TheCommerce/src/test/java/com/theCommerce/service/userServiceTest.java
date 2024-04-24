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
class userServiceTest {

	@Autowired UserService userService;
	@Autowired UserRepository userRepository;
	
	@Test
	void 회원가입() {
		// given
		Users user = new Users();
		user.setId("abcd");
		user.setPw("abcd");
		
		// when
		String saveId = userService.join(user);
		
		// then
		Users finduser = userService.findOne(saveId).get();
		assertThat(user.getName()).isEqualTo(finduser.getName());
	}
	
	@Test
	public void 중복_회원_예외() {
		// given
		Users user1 = new Users();
		user1.setId("abcd");
		user1.setPw("abcd");

		Users user2 = new Users();
		user2.setId("abcd");
		user2.setPw("abcd");
		
		// when
		userService.join(user1);
		IllegalStateException e = assertThrows(IllegalStateException.class, () -> userService.join(user2));
		
		assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
		
	}
}
