package org.nstut.luvit.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponse {
    private String userName;
    private String content;
    private LocalDateTime createdAt;
    private Long totalFavourites;
    private Long totalComments;
    private String imageUrl;
}