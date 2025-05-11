package com.example.minisocial.Resources;
import com.example.minisocial.Entities.CreateGroupRequest;
import com.example.minisocial.Entities.GroupActionRequest;
import com.example.minisocial.Entities.PostRequest;
import com.example.minisocial.Entities.User;
import com.example.minisocial.Repositories.DataEngine;
import com.example.minisocial.Services.GroupManagementService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import security.JwtUtil;

@Path("/groups")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GroupManagementResource {

    @Inject
    GroupManagementService groupService;

    @POST
    @Path("/create")
    public Response createGroup(@HeaderParam("Authorization")String token,CreateGroupRequest request) {
        try {
            JwtUtil.validateToken(token);
            String username= JwtUtil.getUsername(token);
            groupService.createGroup(username,request);
            return Response.ok("Group created").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to create group: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/join")
    public Response joinGroup(@HeaderParam("Authorization")String token,GroupActionRequest request) {
        try {
            JwtUtil.validateToken(token);
            String username=JwtUtil.getUsername(token);
            request.setUsername(username);
            groupService.joinGroup(request);
            return Response.ok("User joined group").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to join group: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/leave")
    public Response leaveGroup(@HeaderParam("Authorization")String token,GroupActionRequest request) {
        try {
            JwtUtil.validateToken(token);
            String username=JwtUtil.getUsername(token);
            request.setUsername(username);
            groupService.leaveGroup(request);
            return Response.ok("User left group").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to leave group: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/post")
    public Response postInGroup(@HeaderParam("Authorization")String token,PostRequest request) {
        try {
            JwtUtil.validateToken(token);
            String username=JwtUtil.getUsername(token);
            request.setAuthorUsername(username);
            groupService.postInGroup(request);
            return Response.ok("Post created in group").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to post in group: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/list")
    public Response listGroups(@HeaderParam("Authorization")String token) {
        try {
            JwtUtil.validateToken(token);
            return Response.ok(groupService.getAllGroups()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to list groups: " + e.getMessage()).build();
        }
    }
}
