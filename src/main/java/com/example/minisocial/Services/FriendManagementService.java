package com.example.minisocial.Services;

import com.example.minisocial.Entities.FriendRequest;
import com.example.minisocial.Entities.User;
import com.example.minisocial.Repositories.UserRepo;
import jakarta.ejb.Stateful;
import jakarta.inject.Inject;

@Stateful
public class FriendManagementService {

    @Inject
    UserRepo userRepo;

    public void sendFriendRequest(String sender,String receiver) {
        if(userRepo.isFriendRequestSent(sender,receiver)){
            throw new IllegalArgumentException("Friend request already sent");
        }
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(userRepo.findUserByUsername(sender).getUsername());
        friendRequest.setReceiver(userRepo.findUserByUsername(receiver).getUsername());
        if (friendRequest.getSender() == null || friendRequest.getReceiver() == null) {
            throw new IllegalArgumentException("User not found");
        }
        friendRequest.setStatus(FriendRequest.RequestStatus.PENDING);
        userRepo.sendFriendRequest(friendRequest);
    }

    public void acceptFriendRequest(String sender, String receiver) {
        if (!userRepo.isFriendRequestSent(sender, receiver)) {
            throw new IllegalArgumentException("Friend request not sent");
        }

        User senderUser = userRepo.findUserByUsername(sender);
        User receiverUser = userRepo.findUserByUsername(receiver);

        if (senderUser == null || receiverUser == null) {
            throw new IllegalArgumentException("User not found");
        }

        userRepo.addFriend(sender, receiver);
        userRepo.updateFriendRequestStatus(sender, receiver, FriendRequest.RequestStatus.ACCEPTED);
    }

    public void rejectFriendRequest(String sender, String receiver) {
        if (!userRepo.isFriendRequestSent(sender, receiver)) {
            throw new IllegalArgumentException("Friend request not sent");
        }

        User senderUser = userRepo.findUserByUsername(sender);
        User receiverUser = userRepo.findUserByUsername(receiver);

        if (senderUser == null || receiverUser == null) {
            throw new IllegalArgumentException("User not found");
        }
        userRepo.updateFriendRequestStatus(sender, receiver, FriendRequest.RequestStatus.REJECTED);
    }

}
