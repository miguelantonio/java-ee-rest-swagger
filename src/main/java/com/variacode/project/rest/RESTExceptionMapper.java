package com.variacode.project.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author miguel
 */
@Provider
public class RESTExceptionMapper implements ExceptionMapper<RESTException> {

    @Override
    public Response toResponse(RESTException e) {
        return Response.status(e.getStatus()).type("text/plain")
                .entity(e.getMessage() == null ? e.getStatus().toString() : e.getMessage()).build();
    }
}
