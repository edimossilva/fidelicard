package com.loop.fidelicard.security.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loop.fidelicard.security.dto.LoginUserDTO;
import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.service.LoginUserService;

@RestController
public class LoginUserController {
	@Autowired
	private LoginUserService loginUserService;

	@PreAuthorize("hasAuthority('ROLE_FINAL_CLIENT')")
	@RequestMapping(value = "/roleFinalClient", method = GET)
	public Iterable<LoginUser> guest() {
		return loginUserService.findAll();
	}

	@PreAuthorize("hasAuthority('ROLE_ENTERPRISE')")
	@RequestMapping(value = "/roleEnterprise", method = GET)
	public Iterable<LoginUser> admin() {
		return loginUserService.findAll();
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@RequestMapping(value = "/loginUser", method = POST)
	public LoginUser save(@RequestBody LoginUserDTO loginUserDTO) {
		return loginUserService.save(loginUserDTO);
	}

	@RequestMapping(value = "/loginUser", method = GET)
	public List<LoginUser> index() {
		return loginUserService.findAll();
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@RequestMapping(value = "/loginUser/{id}", method = DELETE)
	public String delete(@PathVariable("id") Long id) {
		loginUserService.deleteById(id);
		return "ok";
	}

	@RequestMapping(value = "/none", method = GET)
	public Iterable<LoginUser> none() {
		return loginUserService.findAll();
	}

}
