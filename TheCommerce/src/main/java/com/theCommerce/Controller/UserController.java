package com.theCommerce.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
            String message = userService.join(form);
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
	
	// 회원목록 조회
	@GetMapping("/list")
	@ResponseBody
	public List<Users> list(
			@RequestParam(name = "page", defaultValue = "0") int page,
	        @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
	        @RequestParam(name = "sort", defaultValue = "joinDate") String sort) {
		
		// 정렬을 위한 Sort 객체 생성
	    Sort sortOptions = Sort.by(sort).ascending(); 
	    if (sort.equals("name")) {
	        sortOptions = Sort.by(sort).ascending(); 
	    }
	    
	    // 페이지네이션을 위한 Pageable 객체 생성
	    Pageable pageable = PageRequest.of(page, pageSize, sortOptions);
		
	    // 회원 목록 조회
	    Page<Users> userList = userService.findUsers(pageable);
		
		return userList.getContent();
	}
	
	// 회원수정 페이지 접근
	@GetMapping("/{loginId}")
	public String updateUser(@PathVariable("loginId") String loginId, Model model) {
		Optional<Users> userOptional = userService.findOne(loginId);
		Users user = userOptional.orElse(null); 
	    model.addAttribute("user", user);
        return "modifyUser";
    }
	
	// 회원수정
	@PostMapping("{loginId}")
    public ResponseEntity<String> updateUser(Users updateForm) {
		boolean isSuccess = userService.updateUser(updateForm);
	    if (isSuccess) {
	        return ResponseEntity.ok("회원 정보가 성공적으로 수정되었습니다.");
	    } else {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 정보 수정에 실패하였습니다.");
	    }
    }
	
}