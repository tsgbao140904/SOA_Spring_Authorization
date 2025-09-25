package com.example.jwt.filter;

import com.example.jwt.util.JWTUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class JWTFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();

        // Cho phép login và root (/) không cần token
        if (path.contains("/login") || path.equals("/jwt-mvc/")) {
            chain.doFilter(request, response);
            return;
        }

        // Lấy token trong Header
        String authHeader = req.getHeader("Authorization");
        if (authHeader == null) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("{\"error\":\"Missing Authorization header\"}");
            return;
        }

        if (!authHeader.startsWith("Bearer ")) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("{\"error\":\"Invalid Authorization header format\"}");
            return;
        }

        String token = authHeader.substring(7);
        String user = JWTUtil.validateToken(token);

        if (user != null) {
            chain.doFilter(request, response);
        } else {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("{\"error\":\"Invalid or expired token\"}");
        }
    }
}