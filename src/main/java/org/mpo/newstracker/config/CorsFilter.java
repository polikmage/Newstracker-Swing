/*
package org.mpo.newstracker.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(CorsFilter.class);
    //private String exposedHeader;

    public CorsFilter() {
        log.info("SimpleCORSFilter init");
        //this.exposedHeader = exposedHeader;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException,
            IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        //response.setHeader("Access-Control-Expose-Headers",exposedHeader);

        if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE,OPTIONS");
            response.setHeader(
                    "Access-Control-Allow-Headers","accept, x-requested-with,Authorization, Content-Type, Accept-Language, Accept-Encoding ," +
                            "Origin, Access-Control-Request-Method ,Access-Control-Request-Headers, Last-Modified, Cookie, Referer, username");
            response.setHeader("Access-Control-Expose-Headers",
                    "Access-Control-Allow-Origin,Authorization,Accept-Ranges,Content-Encoding,Content-Length,Content-Range");
            response.setHeader("Access-Control-Max-Age", "100");
        }

        filterChain.doFilter(request, response);
    }

}
*/
