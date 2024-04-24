package com.theCommerce.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.theCommerce.domain.Users;
import com.theCommerce.service.UserService;


@Controller
public class UserController {
	
	private final UserService memberService;

	@Autowired
	public UserController(UserService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping("/api/user/join")
	public String createForm() {
		return "userJoinForm";
	}
	
	@PostMapping("/api/user/join")
	public String create(Users form) {
		memberService.join(form);
		
		return "redirect:/";
	}
	
	@GetMapping("/api/user/list")
	public String list(Model model) {
		List<Users> userList = memberService.findUsers();
		model.addAttribute("userList",userList);
		
		return "userList";
	}
	
}