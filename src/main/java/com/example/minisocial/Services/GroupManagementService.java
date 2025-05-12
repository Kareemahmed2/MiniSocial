package com.example.minisocial.Services;

import com.example.minisocial.Entities.*;
import com.example.minisocial.Repositories.DataEngine;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class GroupManagementService {

    @Inject
    DataEngine dataEngine;

    public void createGroup(String owner,CreateGroupRequest request) {
        User creator = dataEngine.findUserByUsername(owner);
        List<User> users = request.members.stream()
                .map(dataEngine::findUserByUsername)
                .collect(Collectors.toList());

        Group group = new Group();
        group.setCreator(creator);
        group.setAdmins(List.of(creator));
        group.setName(request.getName());
        group.setDescription(request.getDescription());
        group.setMembers(users);
        group.addMember(creator);
        group.setPrivate(request.getPrivate());

        dataEngine.createGroup(group);
    }

    public void joinGroup(GroupActionRequest request) {
        Group group = dataEngine.findGroupByName(request.groupName);
        User user = dataEngine.findUserByUsername(request.username);

        if (!group.getMembers().contains(user)) {
            group.addMember(user);
            dataEngine.updateGroup(group);
        }
        else{
            throw new IllegalArgumentException("User already in group");
        }
    }

    public void leaveGroup(GroupActionRequest request) {
        Group group = dataEngine.findGroupByName(request.groupName);
        User user = dataEngine.findUserByUsername(request.username);

        if (group.getMembers().contains(user)) {
            group.removeMember(user);
            group.getAdmins().remove(user);
            dataEngine.updateGroup(group);
        }
        else{
            throw new IllegalArgumentException("User not in group");
        }
    }

    public void postInGroup(GroupPostRequest request) {
        User author = dataEngine.findUserByUsername(request.authorUsername);
        Group group = dataEngine.findGroupByName(request.groupName);

        if (!group.getMembers().contains(author)) {
            throw new IllegalArgumentException("User not in group");
        }
        GroupPost groupPost = new GroupPost();
        groupPost.setAuthor(author);
        groupPost.setContent(request.content);
        groupPost.setGroup(group);
        group.addPost(groupPost);
        dataEngine.saveGroupPost(groupPost);
        dataEngine.updateGroup(group);
    }

    public List<GroupDTO> getAllGroups() {
        return dataEngine.findAllGroups().stream().map(group -> {
            GroupDTO dto = new GroupDTO();
            dto.name = group.getName();
            dto.description = group.getDescription();
            dto.members = group.getMembers().stream().map(User::getUsername).collect(Collectors.toList());
            dto.admins = group.getAdmins().stream().map(User::getUsername).collect(Collectors.toList());
            return dto;
        }).collect(Collectors.toList());
    }

    public void promoteMember(GroupManagementRequest request) {
        Group group = dataEngine.findGroupByName(request.getGroupName());
        User user = dataEngine.findUserByUsername(request.getUsername());
        User promoterUser = dataEngine.findUserByUsername(request.getAdmin());
        if(!group.getAdmins().contains(promoterUser)) {
            throw new IllegalArgumentException(promoterUser + " is not an admin");
        }
        group.addAdmin(user);
        group.removeMember(user);
        dataEngine.updateGroup(group);
    }
    public void removeMember(GroupManagementRequest request) {
        Group group = dataEngine.findGroupByName(request.getGroupName());
        User user = dataEngine.findUserByUsername(request.getUsername());
        User admin = dataEngine.findUserByUsername(request.getAdmin());
        if(!group.getAdmins().contains(admin)) {
            throw new IllegalArgumentException(admin + " is not an admin");
        }
        group.removeMember(user);
        dataEngine.updateGroup(group);
    }

    public void removePost(GroupManagementRequest request) {
        Group group = dataEngine.findGroupByName(request.getGroupName());
        User admin = dataEngine.findUserByUsername(request.getAdmin());
        if(!group.getAdmins().contains(admin)) {
            throw new IllegalArgumentException(admin + " is not an admin");
        }
        GroupPost post = dataEngine.findGroupPostById(request.getPostId());
        group.removePost(post);
        dataEngine.updateGroup(group);
    }
    public void deleteGroup(GroupManagementRequest request) {
        Group group = dataEngine.findGroupByName(request.getGroupName());
        User admin = dataEngine.findUserByUsername(request.getUsername());
        if(!group.getAdmins().contains(admin)) {
            throw new IllegalArgumentException(admin + " is not an admin");
        }
        dataEngine.deleteGroup(group);
    }
}

