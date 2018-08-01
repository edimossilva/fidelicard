package com.loop.fidelicard.security.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.security.dto.token.ResponseTokenAndEnterpriseDTO;
import com.loop.fidelicard.security.dto.token.ResponseTokenDTO;
import com.loop.fidelicard.service.EnterpriseService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenAuthenticationService {

	private static final long EXPIRATIONTIME = 864000000;
	private static final String SECRET = "(<h5$Y70x@1g*@s[ZjJC7AI*4%b;st(c";
	private static final String TOKEN_PREFIX = "Bearer";
	private static final String HEADER_STRING = "Authorization";

	@Autowired
	private EnterpriseService enterpriseService;

	public void addAuthentication(HttpServletResponse res, String username) {
		String JWT = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();

		String token = TOKEN_PREFIX + " " + JWT;
		// res.addHeader(HEADER_STRING, tokenAsJson);

		Enterprise enterprise = enterpriseService.findByOwnerEmail(username);

		String returnJson = "{}";
		if (enterprise != null) {
			System.out.println("eh do tipo enterprise");
			returnJson = tokenAndEnterpriseJson(token, enterprise);
		} else {
			System.out.println("NAO eh do tipo enterprise");
			returnJson = tokenToJson(token);
		}

		try {
			res.getOutputStream().print(returnJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String tokenAndEnterpriseJson(String token, Enterprise enterprise) {
		ObjectMapper mapper = new ObjectMapper();
		ResponseTokenAndEnterpriseDTO dto = new ResponseTokenAndEnterpriseDTO(token,enterprise);
		String jsonInString = null;

		try {
			jsonInString = mapper.writeValueAsString(dto);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		return jsonInString;
	}

	private String tokenToJson(String token) {
		ObjectMapper mapper = new ObjectMapper();
		ResponseTokenDTO dto = new ResponseTokenDTO(token);
		String jsonInString = null;

		try {
			jsonInString = mapper.writeValueAsString(dto);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		return jsonInString;
	}

	public Authentication getByToken(String token) {
		String user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody()
				.getSubject();

		return user != null ? new UsernamePasswordAuthenticationToken(user, null, null) : null;
	}

	public Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if (token != null) {
			return getByToken(token);
		}
		return null;
	}
}