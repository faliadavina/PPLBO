package com.blog.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.blog.repository.PostJpaRepository;
import com.blog.repository.PostRepository;
import com.blog.vo.Post;

@Service
public class PostService {
	private static List<Post> posts;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	PostJpaRepository jpaRepository;
	
	public Post getPost(Long id) {
		Post post = jpaRepository.findOneById(id);
		return post;
	}
	
	public List<Post> getPosts(){
//		posts = new ArrayList<>();
//		posts.add(new Post(1L, "Mike","Mike's Post", "Welcome to My Blog"));
//		posts.add(new Post(2L, "Jason", "It's Jason", "Hi, My name is Jason"));
		List<Post> posts = jpaRepository.findAllByOrderByUpdtDateDesc();
		
		return posts;
	}
	
	public List<Post> getPostsOrderByUpdtAsc() {
		List<Post> post = postRepository.findPostOrderByUpdtDateAsc();
		return post;
	}
	
	public List<Post> getPostsOrderByRegDesc() {
		List<Post> post = postRepository.findPostOrderByRegDateDesc();
		return post;
	}
	
	public boolean savePost(Post post) {
		Post result = jpaRepository.save(post);
		boolean isSuccess = true;
		
		if(result == null) {
			isSuccess = false;
		}
		return isSuccess;
	}
	
	public boolean deletePost(Long id) {
		Post result = jpaRepository.findOneById(id);
		
		if(result == null)
			return false;
		
		jpaRepository.deleteById(id);
		return true;
	}
	
	public boolean updatePost(Post post) {
		Post result = jpaRepository.findOneById(post.getId());
		
		if(result == null)
			return false;
		
		if(!StringUtils.isEmpty(post.getTitle())) {
			result.setTitle(post.getTitle());
		}
		
		if(!StringUtils.isEmpty(post.getContent())) {
			result.setContent(post.getContent());
		}
		
		jpaRepository.save(result);
		
		return true;
	}
	
	public List<Post> searchPostByTitle(String query){
		List<Post> posts = jpaRepository.findByTitleContainingOrderByUpdtDateDesc(query);
		return posts;
	}
}
