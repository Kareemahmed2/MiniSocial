package com.example.minisocial.Resources;

import com.example.minisocial.Entities.FriendRequest;
import com.example.minisocial.Entities.FriendRequestDTO;
import com.example.minisocial.Services.FriendManagementService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import security.JwtUtil;

@Path("/friends")
@Consumes(MediaType.APPLICATION_JSON)
public class FriendsManagementResource {
    @Inject
    FriendManagementService friendManagementService;

    @POST
    @Path("request")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendFriendRequest(@HeaderParam("Authorization") String token, FriendRequestDTO receiver) {
        try{
            JwtUtil.validateToken(token);
            String sender=JwtUtil.getUsername(token);
            friendManagementService.sendFriendRequest(sender,receiver.getReceiver());
            return Response.ok("Friend request sent to "+ receiver.getReceiver()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("accept")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response acceptFriendRequest(@HeaderParam("Authorization") String token, FriendRequestDTO friendRequest) {
        try {
            JwtUtil.validateToken(token);
            String sender = JwtUtil.getUsername(token);
            System.out.println("FriendRequestDTO received: " + friendRequest.getReceiver());
            friendManagementService.acceptFriendRequest(friendRequest.getReceiver(), sender);
            return Response.ok("Friend request accepted, you and "+friendRequest.getReceiver()+" are now friends").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("reject")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response declineFriendRequest(@HeaderParam("Authorization") String token, FriendRequestDTO friendRequest) {
        try {
            JwtUtil.validateToken(token);
            String sender = JwtUtil.getUsername(token);
            friendManagementService.rejectFriendRequest(friendRequest.getReceiver(), sender);
            return Response.ok("Friend request rejected").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }
}
