package com.loop.fidelicard.security.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loop.fidelicard.security.dto.LoginUserDTO;
import com.loop.fidelicard.security.dto.ResponseLoginUserDTO;
import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.service.LoginUserService;
import com.loop.fidelicard.util.GenericsUtil;

@RestController
public class LoginUserController {
	@Autowired
	private LoginUserService loginUserService;

	// @PreAuthorize("hasAuthority('ROLE_FINAL_CLIENT')")

	// @PreAuthorize("hasAuthority('ROLE_ENTERPRISE')")
	// @RequestMapping(value = "/roleEnterprise", method = GET)
	// public Iterable<LoginUser> admin() {
	// return loginUserService.findAll();
	// }

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loginUser", method = GET)
	public ResponseEntity guest() {
		List<ResponseLoginUserDTO> responseLoginUserDTOList = new ArrayList<>();
		loginUserService.findAll().forEach(lu -> responseLoginUserDTOList.add(new ResponseLoginUserDTO(lu)));
		return GenericsUtil.objectToResponse(responseLoginUserDTOList);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loginUser", method = POST)
	public ResponseEntity save(@RequestBody LoginUserDTO loginUserDTO) {
		LoginUser loginUser = loginUserService.save(loginUserDTO);

		return GenericsUtil.objectToResponse(loginUser.toResponseLoginUserDTO());
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loginUser/{id}", method = DELETE)
	public ResponseEntity delete(@PathVariable("id") Long id) {
		loginUserService.deleteById(id);
		return GenericsUtil.objectToResponse("Login User with id " + id + " was deleted");
	}

}
