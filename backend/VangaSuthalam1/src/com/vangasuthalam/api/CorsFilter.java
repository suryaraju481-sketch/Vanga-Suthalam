package com.vangasuthalam.api;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class CorsFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest =
                (HttpServletRequest) request;

        HttpServletResponse httpResponse =
                (HttpServletResponse) response;

        String origin = httpRequest.getHeader("Origin");

        if ("http://localhost:5173".equals(origin)
                || "http://localhost:5174".equals(origin)
                || "https://vanga-suthalam-3kes.vercel.app".equals(origin)) {

            httpResponse.setHeader(
                    "Access-Control-Allow-Origin",
                    origin
            );
        }

        httpResponse.setHeader(
                "Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS"
        );

        httpResponse.setHeader(
                "Access-Control-Allow-Headers",
                "Content-Type"
        );

        httpResponse.setHeader(
                "Access-Control-Allow-Credentials",
                "true"
        );

        if ("OPTIONS".equalsIgnoreCase(
                httpRequest.getMethod())) {

            httpResponse.setStatus(
                    HttpServletResponse.SC_OK
            );

            return;
        }

        chain.doFilter(request, response);
    }
}