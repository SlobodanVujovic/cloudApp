package com.cloudApp.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = "/faces/sections/adminLoginPage.xhtml")
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        // Ovo prazni kes memoriju response-a tako da se svaki put proverava da li je korisnik izlogovan ili nije kada se pozove ovaj filter.
        // Poziva se cak i kada se radi Back i Forward na samom browser-u.
        httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        httpResponse.setDateHeader("Expires", 0); // Proxies.

        if (session == null || session.getAttribute("username") == null) {
            // No logged-in user found, so redirect to login page.
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/faces/sections/login.xhtml");
        } else {
            // Logged-in user found, so just continue request.
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

}
