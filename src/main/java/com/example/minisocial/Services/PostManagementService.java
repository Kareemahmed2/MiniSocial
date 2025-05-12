package com.example.minisocial.Services;

import com.example.minisocial.Entities.*;
import com.example.minisocial.Repositories.DataEngine;
import jakarta.ejb.Stateful;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@Stateful
public class PostManagementService {
    @Inject
    DataEngine dataEngine;

    public void createPost(PostRequest postRequest) {
        Post post=new Post();
        User user=dataEngine.findUserByUsername(postRequest.getAuthor());
        post.setAuthor(user);
        post.setContent(postRequest.getContent());
        dataEngine.createPost(post);
    }

    public List<PostDTO> getTimeline(String username) {
        User user = dataEngine.findUserByUsername(username);
        List<Post> timeline = new ArrayList<>();
        for (User friend : user.getFriends()) {
            List<Post> friendPosts = dataEngine.getPostsByUser(friend.getUsername());
            for (Post post : friendPosts) {
                if (post instanceof GroupPost) {
                    GroupPost groupPost = (GroupPost) post;
                    if (groupPost.getGroup().getMembers().contains(user)) {
                        timeline.add(groupPost);
                    }
                } else {
                    timeline.add(post);
                }
            }
        }
        timeline.addAll(dataEngine.getPostsByUser(username));

        List<PostDTO> result = new ArrayList<>();

        for (Post post : timeline) {
            PostDTO dto = new PostDTO();
            dto.setId(post.getId());
            dto.setContent(post.getContent());
            dto.setMediaURL(post.getMediaURL());
            dto.setAuthorUsername(post.getAuthor().getUsername());
            List<String> likedUsernames = new ArrayList<>();
            if (post.getLikedBy() != null) {
                for (User liker : post.getLikedBy()) {
                    likedUsernames.add(liker.getUsername());
                }
            }
            dto.setLikedByUsernames(likedUsernames);
            List<CommentDTO> commentDTOs = new ArrayList<>();
            if (post.getComments() != null) {
                for (Comment comment : post.getComments()) {
                    CommentDTO commentDTO = new CommentDTO();
                    commentDTO.setAuthor(comment.getAuthor().getUsername());
                    commentDTO.setContent(comment.getContent());
                    commentDTOs.add(commentDTO);
                }
            }
            dto.setComments(commentDTOs);
            result.add(dto);
        }
        return result;
    }


    public void editPost(String username,PostUpdateRequest postUpdateRequest) {
        if(!dataEngine.findPostById(postUpdateRequest.getPostId()).getAuthor().getUsername().equals(username)) {
            throw new IllegalArgumentException("You are not the author of this post");
        }
        Post post = dataEngine.findPostById(postUpdateRequest.getPostId());
        post.setContent(postUpdateRequest.getContent());
        dataEngine.updatePost(post);
    }

    public void likePost(LikeRequest likeRequest) {
        Post post = dataEngine.findPostById(likeRequest.getPostId());
        User user = dataEngine.findUserByUsername(likeRequest.getUsername());
        if(post.getLikedBy().contains(user)) {
            throw new IllegalArgumentException("Post already liked");
        };
        post.addLike(user);
        dataEngine.updatePost(post);
    }
    public void commentOnPost(CommentDTO commentDTO) {
        Post post = dataEngine.findPostById(commentDTO.getPostId());
        User user = dataEngine.findUserByUsername(commentDTO.getAuthor());
        Comment comment = new Comment();
        comment.setAuthor(user);
        comment.setContent(commentDTO.getContent());
        comment.setPost(post);
        post.addComment(comment);
        dataEngine.updatePost(post);
    }
}
