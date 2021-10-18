package br.com.dev.restwithspringbootudemy.security.jwt;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.dev.restwithspringbootudemy.exceptions.ExceptionResponse;
import br.com.dev.restwithspringbootudemy.exceptions.InvalidJwtAuthenticationException;

public class JwtTokenFilter extends GenericFilterBean {
	
	private JwtTokenProvider tokenProvider;
	
	public JwtTokenFilter(JwtTokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String token = tokenProvider.reolveToken((HttpServletRequest) request);
		try {
			if (Objects.nonNull(token) && tokenProvider.validateToken(token)) {
				Authentication authentication = tokenProvider.getAuthentication(token);
				if (Objects.nonNull(authentication))
					SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			chain.doFilter(request, response);
			
		} catch (InvalidJwtAuthenticationException ex) {
            logger.error(ex.getMessage());
            raiseException((HttpServletRequest)request, (HttpServletResponse)response, ex);
            return;
        } catch (Exception ex) {
            raiseException((HttpServletRequest)request, (HttpServletResponse)response, ex);
            return;
        }

	}
	
	private void raiseException(HttpServletRequest request, HttpServletResponse response, Exception ex)
	        throws IOException, ServletException {
	    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), ex.getMessage());
	    byte[] body = new ObjectMapper().writeValueAsBytes(exceptionResponse);
	    response.getOutputStream().write(body);
	}

}
