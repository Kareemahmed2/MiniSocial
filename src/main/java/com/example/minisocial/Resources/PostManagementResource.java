package com.example.minisocial.Resources;
import com.example.minisocial.Entities.CommentDTO;
import com.example.minisocial.Entities.LikeRequest;
import com.example.minisocial.Entities.PostRequest;
import com.example.minisocial.Entities.PostUpdateRequest;
import com.example.minisocial.Services.PostManagementService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import security.JwtUtil;

@Path("/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostManagementResource {
    @Inject
    PostManagementService service;

    @POST
    @Path("/create")
    public Response createPost(@HeaderParam("Authorization")String token,PostRequest request) {
        try {
            JwtUtil.validateToken(token);
            String user = JwtUtil.getUsername(token);
            request.setAuthor(user);
            service.createPost(request);
            return Response.status(Response.Status.CREATED).build();
        }catch (Exception e){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @GET
    @Path("/timeline")
    public Response getTimeline(@HeaderParam("Authorization")String token) {
        JwtUtil.validateToken(token);
        String username = JwtUtil.getUsername(token);
        return Response.ok(service.getTimeline(username)).build();
    }

    @PUT
    @Path("/edit")
    public Response editPost(@HeaderParam("Authorization")String token, PostUpdateRequest request) {
        JwtUtil.validateToken(token);
        service.editPost(request);
        return Response.ok().build();
    }

    @POST
    @Path("/like")
    public Response likePost(@HeaderParam("Authorization")String token, LikeRequest request) {
        JwtUtil.validateToken(token);
        String username= JwtUtil.getUsername(token);
        request.setUsername(username);
        service.likePost(request);
        return Response.ok().build();
    }

    @POST
    @Path("/comment")
    public Response comment(@HeaderParam("Authorization")String token, CommentDTO request) {
        JwtUtil.validateToken(token);
        String username= JwtUtil.getUsername(token);
        request.setAuthor(username);
        service.commentOnPost(request);
        return Response.ok().build();
    }
}
