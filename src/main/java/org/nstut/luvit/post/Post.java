package org.nstut.luvit.post;

import jakarta.persistence.*;
import org.nstut.luvit.status.Status;
import org.nstut.luvit.user.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "total_favourites")
    private Long totalFavourites;

    @Column(name = "total_comments")
    private Long totalComments;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @Column(name = "image_url")
    private String imageUrl;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public Long getTotalFavourites() {
        return totalFavourites;
    }

    public Long getTotalComments() {
        return totalComments;
    }

    public Status getStatus() {
        return status;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
