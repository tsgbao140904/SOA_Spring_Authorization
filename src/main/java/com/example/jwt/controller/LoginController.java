package com.example.jwt.controller;

import javax.servlet.http.*;
import java.io.IOException;

public class LoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("userName");
        String password = req.getParameter("password");

        if (username == null || password == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"error\":\"Missing userName or password\"}");
            return;
        }

        if ("admin".equals(username) && "123".equals(password)) {
            String token = com.example.jwt.util.JWTUtil.generateToken(username);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"token\":\"" + token + "\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"error\":\"Invalid credentials\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.getWriter().write(
                "<!DOCTYPE html>" +
                        "<html>" +
                        "<head><title>Login</title></head>" +
                        "<body>" +
                        "<h1>Login</h1>" +
                        "<form method='POST' action='login'>" +
                        "<label>User Name: <input type='text' name='userName'></label><br>" +
                        "<label>Password: <input type='password' name='password'></label><br>" +
                        "<input type='submit' value='Login'>" +
                        "</form>" +
                        "</body></html>"
        );
    }
}