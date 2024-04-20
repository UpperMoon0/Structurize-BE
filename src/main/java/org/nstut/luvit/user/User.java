package org.nstut.luvit.user;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "[users]")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "age")
    private Integer age;

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    private String role;

    @Column(name = "gender_id")
    private Long genderId;

    @Column(name = "preference_id")
    private Long preferenceId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "image_url")
    private String imageUrl;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", status='" + status + '\'' +
                ", role='" + role + '\'' + "}";
    }
}