package org.nstut.luvit.entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Repository
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "reply_id")
    private Comment reply;
}
