package com.example.jwt.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class AuthController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().write("Hello World - Authenticated!");
    }
}