package com.example.minisocial.Entities;

import java.util.List;

public class PostDTO {
    private Long id;
    private String content;
    private String authorUsername;
    private String mediaURL;
    private List<String> likedByUsernames;
    private List<CommentDTO> comments;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public String getMediaURL() {
        return mediaURL;
    }

    public void setMediaURL(String mediaURL) {
        this.mediaURL = mediaURL;
    }

    public List<String> getLikedByUsernames() {
        return likedByUsernames;
    }

    public void setLikedByUsernames(List<String> likedByUsernames) {
        this.likedByUsernames = likedByUsernames;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }
}
