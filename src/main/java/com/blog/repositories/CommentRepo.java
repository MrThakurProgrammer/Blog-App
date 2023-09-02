package com.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.Comment;
import com.blog.payloads.CommentDto;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
