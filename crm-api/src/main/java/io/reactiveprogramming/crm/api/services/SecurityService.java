package io.reactiveprogramming.crm.api.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.DatatypeConverter;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.reactiveprogramming.commons.exceptions.GenericServiceException;
import io.reactiveprogramming.commons.exceptions.ValidateServiceException;
import io.reactiveprogramming.crm.api.dao.IUserDAO;
import io.reactiveprogramming.crm.dto.LoginDTO;
import io.reactiveprogramming.crm.dto.LoginResponseDTO;
import io.reactiveprogramming.crm.entity.User;



import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import io.jsonwebtoken.*;
import java.util.UUID;


@Service
public class SecurityService {
	
	private static final Logger logger = Logger.getLogger(SecurityService.class);

	private static final String TOKEN = "1234";
	private static final int TOKEN_VALIDA_TIME = 86_400_000;
	private static final String SUBJECT = "CRM-API";

	@Autowired
	private IUserDAO userDAO;
	
	public LoginResponseDTO login(LoginDTO request)throws ValidateServiceException, GenericServiceException {
		try {
			
			String username = null;
			boolean validToken = false;
			if(request.getToken() != null) {
				username = decriptToken(request.getToken());
				validToken = true;
			}else {
				username = request.getUsername();
			}
			
			System.out.println("username ==> " + username);

			Optional<User> userOpt = userDAO.findById(username);
			if (!userOpt.isPresent()) {
				throw new ValidateServiceException("Invalid user or password");
			}
			
			User user = userOpt.get();
			
			if(!validToken) {
				if(!user.getPassword().equals(request.getPassword())) {
					throw new ValidateServiceException("Invalid user or password");
				}
			}
			

			String token = createJWT(user.getUsername());
			
			LoginResponseDTO response = new LoginResponseDTO();
			response.setUsername(user.getUsername());
			response.setRol(user.getRol());
			response.setToken(token);
			return response;

		} catch(ValidateServiceException e) {
			logger.info(e.getMessage());
			throw e;
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GenericServiceException("Error al autenticar al usuario");
		}

	}
	
	public String decriptToken(String token) throws ValidateServiceException, GenericServiceException{

        if (token == null || !token.startsWith("Bearer ")) {
        	throw new ValidateServiceException("Token inválido");
        }

        // Extract the token from the HTTP Authorization header
        String decodeToken = token.substring("Bearer".length()).trim();
        try {
        	String username =  parseJWT(decodeToken);
        	return username;
        } catch (Exception e) {
        	throw new ValidateServiceException("Error al validar el token");
        }
	}

	
	
	
	private String createJWT(String username) {
	 
	    //The JWT signature algorithm we will be using to sign the token
	    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	 
	    long nowMillis = System.currentTimeMillis();
	    Date now = new Date(nowMillis);
	 
	    //We will sign our JWT with our ApiKey secret
	    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(TOKEN);
	    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
	 
	    //Let's set the JWT Claims
	    JwtBuilder builder = Jwts.builder().setId(UUID.randomUUID().toString())
	                                .setIssuedAt(now)
	                                .setSubject("SecurityService")
	                                .setIssuer(username)
	                                .signWith(signatureAlgorithm, signingKey);
	 
	    //if it has been specified, let's add the expiration
	    long expMillis = nowMillis + TOKEN_VALIDA_TIME;
	        Date exp = new Date(expMillis);
	        builder.setExpiration(exp);
	 
	    //Builds the JWT and serializes it to a compact, URL-safe string
	    return "Bearer " + builder.compact();
	}
	
	 
	public String parseJWT(String jwt)throws ValidateServiceException, GenericServiceException {
		try {
			//This line will throw an exception if it is not a signed JWS (as expected)
		    Claims claims = Jwts.parser()         
		       .setSigningKey(DatatypeConverter.parseBase64Binary(TOKEN))
		       .parseClaimsJws(jwt).getBody();
		    System.out.println("ID: " + claims.getId());
		    System.out.println("Subject: " + claims.getSubject());
		    System.out.println("Issuer: " + claims.getIssuer());
		    System.out.println("Expiration: " + claims.getExpiration());
		    return claims.getIssuer();
		} catch (Exception e) {
			throw new ValidateServiceException("Invalid Token");
		}
	    
	}
	
	public static void main(String[] args){
		String token = new SecurityService().createJWT("oscar");
		System.out.println("Token => " + token);
	}
}
