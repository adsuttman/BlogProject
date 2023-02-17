package com.techtalentsouth.techtalentblog.blogposts;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BlogPostController {
	@Autowired
	private BlogPostRepository repository;
	
	
//	@RequestMapping(path="/",method = {RequestMethod.GET, RequestMethod.POST})
	@GetMapping(path="/")
	public String index(Model model) {
		List<BlogPost> posts = new ArrayList<>();
		for(BlogPost post :repository.findAll()) {
			posts.add(post);
		}
		
		BlogPost myBlogPost = new BlogPost();
		model.addAttribute("blogPost", myBlogPost);
		model.addAttribute("posts", posts);
		return "blogpost/index";
	}
	
	@GetMapping(path="/blogposts/new")
	public String newBlog(Model model) {
		model.addAttribute("blogPost", new BlogPost());
		return "blogpost/new";
	}
	
	@PostMapping(path="/blogposts")
	public String addNewBlogPost(BlogPost blogPost, Model model) {
		BlogPost newPost = repository.save(blogPost);
		model.addAttribute("blogPost", newPost);
		return "blogpost/result";
	}
	
	@GetMapping(path="/blogposts/{id}")
	public String editPostWithId(@PathVariable Long id, Model model) {
		Optional<BlogPost> postBox = repository.findById(id);
		if(postBox.isPresent()) {
			BlogPost post = postBox.get();
			model.addAttribute("blogPost", post);
		}
		return "blogpost/edit";
	}
	
	@PostMapping(path="/blogposts/{id}")
	public String updateExistingPost(@PathVariable Long id, BlogPost blogPost, Model model) {
		Optional<BlogPost> postBox = repository.findById(id);
		if(postBox.isPresent()) {
			BlogPost actualPost = postBox.get();
			actualPost.setTitle(blogPost.getTitle());
			actualPost.setAuthor(blogPost.getAuthor());
			actualPost.setBlogEntry(blogPost.getBlogEntry());
			repository.save(actualPost);
			model.addAttribute("blogPost", actualPost);
		}
		return "blogpost/result";
	}
	
	@GetMapping(path="/blogposts/delete/{id}")
	public String deletePostById(@PathVariable Long id) {
		repository.deleteById(id);
		return "blogpost/delete";
	}
}
