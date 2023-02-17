package com.techtalentsouth.techtalentblog.blogposts;

import org.springframework.data.repository.CrudRepository;

public interface BlogPostRepository extends CrudRepository<BlogPost, Long> {
	
}
