package com.puhov.SpringBootHibernateProject.repository;

import com.puhov.SpringBootHibernateProject.entity.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
