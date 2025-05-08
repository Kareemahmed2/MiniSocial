package com.example.minisocial.Resources;

import com.example.minisocial.Entities.AuthRequest;
import com.example.minisocial.Entities.User;
import com.example.minisocial.Services.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import security.JwtUtil;

import java.util.List;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HelloResource {
    @Inject
    UserService userService;

    @POST
    @Path("register")
    @PermitAll
    public Response register(User user) {
        try {
            userService.register(user);
            return Response.created(
                    UriBuilder.fromPath("/users/{id}").build(user.getId())
            ).entity(user.getUsername()+" registered").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("login")
    @PermitAll
    public Response login(AuthRequest authRequest) {
        try {
            userService.login(authRequest);
            String token= JwtUtil.generateToken(authRequest.getUsername());
            return Response.ok("Login successful: "+token).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("list")
    @PermitAll
    public List<User> list(@HeaderParam("Authorization") String token) {
        JwtUtil.validateToken(token);
        return userService.getAll();
    }

}
