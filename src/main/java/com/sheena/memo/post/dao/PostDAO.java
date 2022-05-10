package com.sheena.memo.post.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sheena.memo.post.model.PostModel;

@Repository
public interface PostDAO {

	public int insertPost(
			@Param("userId") int userId,
			@Param("subject") String subject, 
			@Param("content") String content,
			@Param("filePath") String filePath);
	
	
	public List<PostModel> selectPostList(@Param("userId") int userId);
	
	
	public PostModel selectPost(@Param("id") int id);
	
	
	// 메모 수정
	public int updatePost(
			@Param("postId") int postId,
			@Param("subject") String subject, 
			@Param("content") String content);
	
	
	// 메모 삭제
	public int deletePost(@Param("postId") int postId, @Param("userId") int userId);
}
