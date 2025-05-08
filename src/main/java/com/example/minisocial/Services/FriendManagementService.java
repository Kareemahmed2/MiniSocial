package com.example.minisocial.Services;

import com.example.minisocial.Entities.FriendRequest;
import com.example.minisocial.Entities.User;
import jakarta.ejb.Stateful;
import jakarta.inject.Inject;

@Stateful
public class FriendManagementService {

    @Inject
    UserService userService;

    public void sendFriendRequest(FriendRequest friendRequest) {

    }
}
