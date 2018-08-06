package com.loop.fidelicard.security.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loop.fidelicard.security.dto.LoginUserDTO;
import com.loop.fidelicard.security.dto.ResponseLoginUserDTO;
import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.model.LoginUserService;
import com.loop.fidelicard.util.GenericsUtil;

@RestController
@CrossOrigin(origins = "*")
public class LoginUserController {
	@Autowired
	private LoginUserService loginUserService;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loginUser", method = GET)
	public ResponseEntity guest() {
		List<ResponseLoginUserDTO> responseLoginUserDTOList = new ArrayList<>();
		loginUserService.findAll().forEach(lu -> responseLoginUserDTOList.add(new ResponseLoginUserDTO(lu)));
		return GenericsUtil.objectToResponse(responseLoginUserDTOList);
	}

	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/v1/loginUser", method = POST)
	public ResponseEntity save(@Valid @RequestBody LoginUserDTO loginUserDTO, BindingResult result) {
		if (result.hasErrors()) {
			return GenericsUtil.errorsToResponse(result);
		}

		List<String> errors = loginUserService.errorsToSave(loginUserDTO);
		if (!errors.isEmpty()) {
			return GenericsUtil.errorsToResponse(errors);
		}

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
