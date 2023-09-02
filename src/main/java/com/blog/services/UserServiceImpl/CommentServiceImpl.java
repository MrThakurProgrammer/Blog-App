package com.blog.services.UserServiceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CommentDto;
import com.blog.repositories.CommentRepo;
import com.blog.repositories.PostRepo;
import com.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	CommentRepo commentRepo;
	
	@Autowired
	PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		Post posts = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post Id", postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(posts);
		
		Comment savedComment = this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		
		Comment comments = commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "comment id", commentId));
		
		this.commentRepo.delete(comments);	
	}

}
