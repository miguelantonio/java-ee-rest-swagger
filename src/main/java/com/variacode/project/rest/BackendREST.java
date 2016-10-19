package com.variacode.project.rest;

import com.variacode.project.rest.interceptors.NotNullChecked;
import com.variacode.project.rest.interceptors.RESTInterceptor;
import com.variacode.project.rest.interceptors.SecurityChecked;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author miguel@variacode.com
 */
@Interceptors(value = RESTInterceptor.class)
@Stateless
@Api(value = "/backend", description = "User API")
@ApplicationPath("/rest")
@Path("/backend")
public class BackendREST extends AbstractREST {

    private static final Logger logger = LoggerFactory.getLogger(BackendREST.class);

    @GET
    @SecurityChecked
    @NotNullChecked
    @Path("/something")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get something",
            response = String.class, //put your entities here
            notes = "",
            responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad parameters")})
    public Response getSomething(
            @HeaderParam("token") String token,
            @ApiParam(name = "thing", required = true)
            @QueryParam("thing") Integer things
    ) throws RESTException {
        List<String> l = new ArrayList<>();
        for (int i = 0; i < things; i++) {
            l.add(i + "");
        }
        return Response.ok(l).build();
    }

}
