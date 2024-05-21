package org.nstut.luvit.controller;

import org.nstut.luvit.dto.PostResponse;
import org.nstut.luvit.entity.Post;
import org.nstut.luvit.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        System.out.println("Getting all posts");
        List<Post> posts = postService.getAllPosts();
        List<PostResponse> postResponses = posts.stream()
                .map(post -> {
                    PostResponse postResponse = new PostResponse();
                    postResponse.setUserName(post.getUser().getFullName());
                    postResponse.setContent(post.getContent());
                    postResponse.setCreatedAt(post.getCreatedAt());
                    postResponse.setTotalFavourites(post.getTotalFavourites());
                    postResponse.setTotalComments(post.getTotalComments());
                    postResponse.setImageUrl(post.getImageUrl());
                    return postResponse;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(postResponses);
    }
}