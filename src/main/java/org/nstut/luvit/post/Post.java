package org.nstut.luvit.post;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "total_favourites")
    private Long totalFavourites;

    @Column(name = "total_comments")
    private Long totalComments;

    @Column(name = "status")
    private Byte status;

    @Column(name = "image_url")
    private String imageUrl;
}
