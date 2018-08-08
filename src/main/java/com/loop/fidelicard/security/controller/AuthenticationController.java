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

	private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
//	private static final String TOKEN_HEADER = "Authorization";
//	private static final String BEARER_PREFIX = "Bearer ";

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

			log.error("Erro validando lançamento: {}", result.getAllErrors());
			List<String> errors = new ArrayList<>();
			result.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
			return GenericsUtil.errorsToResponse(errors);
		}

		log.info("Gerando token para o email {}.", authenticationDto.getUsername());
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

	/**
	 * Gera um novo token com uma nova data de expiração.
	 * 
	 * @param request
	 * @return ResponseEntity<Response<TokenDto>>
	 */
	// @PostMapping(value = "/refresh")
	// public ResponseEntity<Response<TokenDto>>
	// gerarRefreshTokenJwt(HttpServletRequest request) {
	// log.info("Gerando refresh token JWT.");
	// Response<TokenDto> response = new Response<TokenDto>();
	// Optional<String> token =
	// Optional.ofNullable(request.getHeader(TOKEN_HEADER));
	//
	// if (token.isPresent() && token.get().startsWith(BEARER_PREFIX)) {
	// token = Optional.of(token.get().substring(7));
	// }
	//
	// if (!token.isPresent()) {
	// response.getErrors().add("Token não informado.");
	// } else if (!jwtTokenUtil.tokenValido(token.get())) {
	// response.getErrors().add("Token inválido ou expirado.");
	// }
	//
	// if (!response.getErrors().isEmpty()) {
	// return ResponseEntity.badRequest().body(response);
	// }
	//
	// String refreshedToken = jwtTokenUtil.refreshToken(token.get());
	// response.setData(new TokenDto(refreshedToken));
	//
	// return ResponseEntity.ok(response);
	// }

}
