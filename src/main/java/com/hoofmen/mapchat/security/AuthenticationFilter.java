package com.hoofmen.mapchat.security;

import com.hoofmen.mapchat.shared.AppConstants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * Filter class to authenticate the rest calls for DataService.
 * TODO: Token for the calls should be in Base64 Encoded (E.g "X-AUTH-TOKEN:ENCODED_SECRET_TOKEN")
 *
 */
public class AuthenticationFilter implements Filter {

	AuthenticationService authenticationService = new AuthenticationService();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filter) throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			String authToken = httpServletRequest.getHeader(AppConstants.HEADER_TOKEN);

			if (authenticationService.authenticate(authToken)){				
				filter.doFilter(request, response);
			} else {
				if (response instanceof HttpServletResponse) {
					HttpServletResponse httpServletResponse = (HttpServletResponse) response;
					httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					httpServletResponse.getOutputStream().write("UNAUTHORIZED. CTM".getBytes());
				}
			}
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}