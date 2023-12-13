package com.famlynk.jwt;





import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.famlynk.security.RegUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;




@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private JWTService jwtService;
	@Autowired
	private RegUserDetailsService regUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            userName = jwtService.extractUsernameFromToken(token);
        }
        
        if (userName != null & SecurityContextHolder.getContext().getAuthentication() == null) {
        	UserDetails userDetails = regUserDetailsService.loadUserByUsername(userName);
        	if(jwtService.validateToken(token, userDetails)) {
                var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
		
	}

}










//@Component
//public class JWTAuthenticationFilter extends OncePerRequestFilter{
//	
//	private final JWTService jwtService;
//    private final RegUserDetailsService regUserDetailsService;
//    
//    public JWTAuthenticationFilter(RegUserDetailsService regUserDetailsService, JWTService jwtService) {
//		this.jwtService = jwtService;
//		this.regUserDetailsService = regUserDetailsService;
//    	
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//    	
//    	response.addHeader("Access-Control-Allow-Origin", "*");
//        response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, authorization");
//        response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, authorization");
//        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH");
//
//        String authHeader = request.getHeader("Authorization");
////        System.out.println(authHeader+" token  from end");
//        String token = null;
//        String userName = null;
//        if (authHeader != null && authHeader.startsWith("")){
//            token = authHeader.substring(7);
//            userName = jwtService.extractUsernameFromToken(token);
//        }
//        if (userName != null & SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = regUserDetailsService.loadUserByUsername(userName);
//            if(jwtService.validateToken(token, userDetails)) {
//                var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
//}
