package com.example.minisocial.Entities;

public class LikeRequest {
    private String username;
    private Long postId;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
