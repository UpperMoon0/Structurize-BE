package org.nstut.luvit.gender;

import jakarta.persistence.*;

@Entity
@Table(name="gender")
public class Gender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    @Column(name="name")
    private String name;

    // To String
    @Override
    public String toString() {
        return "Gender{" +
                "id=" + id +
                ", name='" + name + '\'' + "}";
    }
}
