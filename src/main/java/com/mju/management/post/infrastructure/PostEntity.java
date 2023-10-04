package com.mju.management.post.infrastructure;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "posts")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
