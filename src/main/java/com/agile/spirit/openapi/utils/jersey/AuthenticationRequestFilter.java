package com.agile.spirit.openapi.utils.jersey;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sun.jersey.core.util.Base64;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

public class AuthenticationRequestFilter implements ContainerRequestFilter {

    // Exception thrown if user is unauthorized.
    private final static WebApplicationException unauthorized = new WebApplicationException(Response
            .status(Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"realm\"")
            .entity("Page requires login.").build());

    @Override
    public ContainerRequest filter(ContainerRequest containerRequest) throws WebApplicationException {

        // Automatically allow certain requests.
        String method = containerRequest.getMethod();
        String path = containerRequest.getPath(true);
        if (method.equals("GET") && path.equals("application.wadl"))
            return containerRequest;

        // Get the authentication passed in HTTP headers parameters
        String encodedCredentials = containerRequest.getHeaderValue("authorization");
        if (encodedCredentials == null)
            throw unauthorized;

        encodedCredentials = encodedCredentials.replaceFirst("[Bb]asic ", "");
        String decodedCredentials = Base64.base64Decode(encodedCredentials);

        // TODO : manage true User authentication
        if (!decodedCredentials.equals("admin:admin")) {
            throw unauthorized;
        }

        return containerRequest;
    }
}