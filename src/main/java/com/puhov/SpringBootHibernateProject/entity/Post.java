package com.puhov.SpringBootHibernateProject.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;

/** Сущность поста */
@Entity
@Table(name = "posts")
@Getter
@Setter
public class Post {

    /** Идентификатор поста */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** Идентификатор поста */
    private String title;

    /** Анонс поста */
    private String anons;

    /** Полный текст поста */
    private String full_text;

    /** Время создания поста */
    private OffsetDateTime created;

    /** Время изменения поста */
    private OffsetDateTime changed;

    /** Количество просмотров поста */
    private int views;
}

