package com.example.minisocial.Entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "group_post")
public class GroupPost extends Post implements Serializable  {

    @ManyToOne
    private Group group;


    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}