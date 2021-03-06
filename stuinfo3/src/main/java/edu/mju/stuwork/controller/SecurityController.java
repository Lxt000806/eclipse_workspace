/**
 * 
 */
package edu.mju.stuwork.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import edu.mju.stuwork.domain.User;
import edu.mju.stuwork.service.UserService;

/**
 * @author joeyang ong
 *
 */

@Controller
public class SecurityController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public String toLogin() throws Exception{
		return "login";
	}
	
	@PostMapping("/login")
	public String login(User user, Model model, HttpSession session) throws Exception{
		
//		System.out.println(user);
		User u = userService.getUserByNo(user.getUserNo());
		
		if(u==null){
		   model.addAttribute("errMsg", "没有这个用户，请检查！");	
		   return "login";
		}
		else if(!user.getUserPwd().equals(u.getUserPwd())){	   	
		  model.addAttribute("errMsg", "用户存在，但密码不正确！");
		  return "login";
		}
		else
		{
		  session.setAttribute("loginedUser", u);	
		  return "redirect:/students";
		}
			
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) throws Exception{
		
		session.removeAttribute("loginedUser");
		session.invalidate();
		
		return "login";
		
	}

}
