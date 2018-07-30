package com.loop.fidelicard.security.jwt.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loop.fidelicard.security.dto.ResponseTokenDTO;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationService {

	private static final long EXPIRATIONTIME = 864000000;
	private static final String SECRET = "MySecreteApp";
	private static final String TOKEN_PREFIX = "Bearer";
	private static final String HEADER_STRING = "Authorization";

	public static void addAuthentication(HttpServletResponse res, String username) {
		String JWT = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();

		String token = TOKEN_PREFIX + " " + JWT;

		String jsonInString = tokenToJson(token);
		res.addHeader(HEADER_STRING, jsonInString);

		try {
			res.getOutputStream().print(jsonInString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String tokenToJson(String token) {
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

	public static Authentication getByToken(String token) {
		String user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody()
				.getSubject();

		return user != null ? new UsernamePasswordAuthenticationToken(user, null, null) : null;
	}

	public static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if (token != null) {
			return getByToken(token);
		}
		return null;
	}
}