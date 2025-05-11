package com.example.minisocial.Entities;

import java.util.List;
import java.util.stream.Collectors;

public class GroupDTO {
    public String name;
    public String description;
    public List<String> members;
    public List<String> admins;

    public GroupDTO() {}
    public GroupDTO(Group group) {
        this.name = group.getName();
        this.description = group.getDescription();
        this.members = group.getMembers().stream().map(User::getUsername).collect(Collectors.toList());
        this.admins = group.getAdmins().stream().map(User::getUsername).collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public List<String> getAdmins() {
        return admins;
    }

    public void setAdmins(List<String> admins) {
        this.admins = admins;
    }
}
