package org.nstut.luvit.controller;

import org.nstut.luvit.post.Post;
import org.nstut.luvit.post.PostResponse;
import org.nstut.luvit.post.PostService;
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
            .map(PostResponse::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(postResponses);
    }
}