package com.loop.fidelicard.security.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.security.dto.token.LoginDTO;
import com.loop.fidelicard.security.dto.token.ResponseTokenAndEnterpriseDTO;
import com.loop.fidelicard.security.dto.token.ResponseTokenDTO;
import com.loop.fidelicard.security.utils.JwtTokenUtil;
import com.loop.fidelicard.service.EnterpriseService;
import com.loop.fidelicard.util.GenericsUtil;

@RestController
@RequestMapping("v1/login")
@CrossOrigin(origins = "*")
public class AuthenticationController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	// private static final String TOKEN_HEADER = "Authorization";
	// private static final String BEARER_PREFIX = "Bearer ";

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private EnterpriseService enterpriseService;

	/**
	 * Gera e retorna um novo token JWT.
	 * 
	 * @param authenticationDto
	 * @param result
	 * @return ResponseEntity<Response<TokenDto>>
	 * @throws AuthenticationException
	 */
	@PostMapping
	@SuppressWarnings("rawtypes")
	public ResponseEntity gerarTokenJwt(@Valid @RequestBody LoginDTO authenticationDto, BindingResult result)
			throws AuthenticationException {

		if (result.hasErrors()) {

			logger.error("Erro validando lançamento: {}", result.getAllErrors());
			List<String> errors = new ArrayList<>();
			result.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
			return GenericsUtil.errorsToResponse(errors);
		}

		logger.debug("Gerando token para o email {}.", authenticationDto.getUsername());
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				authenticationDto.getUsername(), authenticationDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDto.getUsername());
		String token = jwtTokenUtil.obterToken(userDetails);

		Enterprise enterprise = enterpriseService.findByOwnerEmail(authenticationDto.getUsername());
		if (enterprise != null) {
			ResponseTokenAndEnterpriseDTO dto = new ResponseTokenAndEnterpriseDTO(token, enterprise);
			return GenericsUtil.objectToResponse(dto);
		} else {
			ResponseTokenDTO dto = new ResponseTokenDTO(token);
			return GenericsUtil.objectToResponse(dto);
		}

	}

}
