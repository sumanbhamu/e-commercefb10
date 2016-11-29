package com.suman.Controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.suman.ecom.dao.UserDAO;
import com.suman.ecom.model.User;

@Controller
public class HomeController {
	@Autowired
	private UserDAO userDAO;

	@Autowired
	User user;

	@RequestMapping("/")
	public String showHome() {
		return "index";
	}

	@RequestMapping("/home")
	public String ShowHome() {
		return "index";
	}

	@RequestMapping("/aboutus")
	public String showAboutUs() {
		return "aboutus";
	}

	@RequestMapping("/login")
	public String showLogin() {
		System.out.println("loginnnnnnnn");
		// ModelAndView mv = new ModelAndView("login");

		return "login";
	}

	@RequestMapping("/register")
	public ModelAndView ShowRegister() {
		System.out.println("registerrrr");
		ModelAndView mv = new ModelAndView("register");
		return mv;
	}

	@ModelAttribute
	public User returnObject() {
		return new User();
	}

	@RequestMapping(value = "/addus", method = RequestMethod.POST)
	public String addUser(@ModelAttribute("user") User user, BindingResult result,HttpServletRequest request) {
		System.out.println(user.getConfirmpassword());
		System.out.println(user.getPassword());
		//ModelAndView mv = new ModelAndView("register");
		user.setEnabled("true");
		user.setRole("ROLE_USER");

		if (user.getConfirmpassword().equals(user.getPassword())) {
			userDAO.saveOrUpdate(user);
		}

		return "login";

	}

	/*
	 * \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	 * \\\
	 */

	/*@RequestMapping("/validate")
	public ModelAndView checkUser(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv;
		String s1, s2;
		s1 = request.getParameter("username");
		s2 = request.getParameter("password");
		mv = new ModelAndView("/login");
		System.out.println(s1 + "" + s2);
		user = userDAO.get(s1);
		System.out.println(user.getEmailid());
		
		//if role is admin then return admin
		if (user.getRole().equals("ROLE_ADMIN")) {
			mv = new ModelAndView("adminhome");
		} 
		//if role is user then return index
		else if (user.getRole().equals("ROLE_USER")) {
			mv = new ModelAndView("index");

		}
		return mv;
	}
*/
	
	
	/*//////////////////////////////////////////////////////////////////////////////////////////
*/
	

	@RequestMapping(value="/login_session_attributes")
	/*getting values from textbox*/
	
	public String login_session_attributes(HttpSession session,Model model,@RequestParam(value="username")String id)
	{
		String name=SecurityContextHolder.getContext().getAuthentication().getName();
		
		System.out.println("inside security check");
		
		session.setAttribute("name", name);
		System.out.println(name);
		
		//user=userDAO.get(name);
		session.setAttribute("loggedInUser", user.getEmailid());
    	session.setAttribute("loggedInUserID", user.getPassword());
    	
		session.setAttribute("LoggedIn", "true");
		
		@SuppressWarnings("unchecked")
		/*getting values from database*/
		Collection<GrantedAuthority> authorities =(Collection<GrantedAuthority>)
		 SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		
		String role="ROLE_USER";
		for(GrantedAuthority authority : authorities)
		{
			/*comparing both the values from txtbox and database*/
			if(authority.getAuthority().equals(role))
			{
				System.out.println(role);
				return "index";
			}
			else
			{
				session.setAttribute("isAdmin", "true");
			}
			}
		return "adminhome" ;
		
		
	}
	


}
