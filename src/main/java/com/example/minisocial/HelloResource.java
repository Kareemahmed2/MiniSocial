package com.example.minisocial;

import com.example.minisocial.Entities.AuthRequest;
import com.example.minisocial.Entities.User;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import java.util.List;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HelloResource {
    @Inject
    UserService userService;

    @POST
    @Path("register")
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
    public Response login(AuthRequest authRequest) {
        try {
            userService.login(authRequest);
            return Response.ok("Login successful").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("list")
    public List<User> list() {
        return userService.getAll();
    }

}
