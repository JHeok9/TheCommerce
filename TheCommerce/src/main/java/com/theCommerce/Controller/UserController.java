package com.theCommerce.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.theCommerce.domain.Users;
import com.theCommerce.service.UserService;


@Controller
@RequestMapping("/api/user")
public class UserController {
	
	private final UserService userService;

	@Autowired
	public UserController(UserService memberService) {
		this.userService = memberService;
	}
	
	// 회원가입 페이지 접근
	@GetMapping("/join")
	public String createForm() {
		return "userJoinForm";
	}
	
	// 회원가입 
	@PostMapping("/join")
    public ResponseEntity<String> joinUser(Users form) {
        try {
            String userId = userService.join(form);
            return ResponseEntity.status(HttpStatus.CREATED).body("회원 가입이 성공적으로 완료되었습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
	
	// 회원목록 조회
	@GetMapping("/list")
	@ResponseBody
	public List<Users> list() {
		List<Users> userList = userService.findUsers();
		
		return userList;
	}
	
}