package org.nstut.luvit.favourite;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "favourite")
@IdClass(FavouriteId.class)
public class Favourite {
    @Id
    private Long postId;

    @Id
    private Long userId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "Favourite{" +
                "postId=" + postId +
                ", userId=" + userId +
                ", createdAt=" + createdAt + "}";
    }
}
