package org.nstut.luvit.post;

import java.time.LocalDateTime;

public class PostResponse {
    private String userName;
    private String content;
    private LocalDateTime createdAt;
    private Long totalFavourites;
    private Long totalComments;
    private String imageUrl;

    public PostResponse(String userName, String content, LocalDateTime createdAt, Long totalFavourites, Long totalComments, String imageUrl) {
        this.userName = userName;
        this.content = content;
        this.createdAt = createdAt;
        this.totalFavourites = totalFavourites;
        this.totalComments = totalComments;
        this.imageUrl = imageUrl;
    }

    public PostResponse(Post post) {
        this.userName = post.getUser().getFullName();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.totalFavourites = post.getTotalFavourites();
        this.totalComments = post.getTotalComments();
        this.imageUrl = post.getImageUrl();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getTotalFavourites() {
        return totalFavourites;
    }

    public void setTotalFavourites(Long totalFavourites) {
        this.totalFavourites = totalFavourites;
    }

    public Long getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(Long totalComments) {
        this.totalComments = totalComments;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}