package com.example.minisocial.Services;

import com.example.minisocial.Entities.FriendRequest;
import com.example.minisocial.Entities.User;
import com.example.minisocial.Repositories.DataEngine;
import jakarta.ejb.Stateful;
import jakarta.inject.Inject;

@Stateful
public class FriendManagementService {

    @Inject
    DataEngine dataEngine;

    public void sendFriendRequest(String sender,String receiver) {
        if(dataEngine.isFriendRequestSent(sender,receiver)){
            throw new IllegalArgumentException("Friend request already sent");
        }
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(dataEngine.findUserByUsername(sender).getUsername());
        friendRequest.setReceiver(dataEngine.findUserByUsername(receiver).getUsername());
        if (friendRequest.getSender() == null || friendRequest.getReceiver() == null) {
            throw new IllegalArgumentException("User not found");
        }
        friendRequest.setStatus(FriendRequest.RequestStatus.PENDING);
        dataEngine.sendFriendRequest(friendRequest);
    }

    public void acceptFriendRequest(String sender, String receiver) {
        if (!dataEngine.isFriendRequestSent(sender, receiver)) {
            throw new IllegalArgumentException("Friend request not sent");
        }

        User senderUser = dataEngine.findUserByUsername(sender);
        User receiverUser = dataEngine.findUserByUsername(receiver);

        if (senderUser == null || receiverUser == null) {
            throw new IllegalArgumentException("User not found");
        }

        dataEngine.addFriend(sender, receiver);
        dataEngine.updateFriendRequestStatus(sender, receiver, FriendRequest.RequestStatus.ACCEPTED);
    }

    public void rejectFriendRequest(String sender, String receiver) {
        if (!dataEngine.isFriendRequestSent(sender, receiver)) {
            throw new IllegalArgumentException("Friend request not sent");
        }

        User senderUser = dataEngine.findUserByUsername(sender);
        User receiverUser = dataEngine.findUserByUsername(receiver);

        if (senderUser == null || receiverUser == null) {
            throw new IllegalArgumentException("User not found");
        }
        dataEngine.updateFriendRequestStatus(sender, receiver, FriendRequest.RequestStatus.REJECTED);
    }

}
