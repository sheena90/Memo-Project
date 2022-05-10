package com.sheena.memo.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sheena.memo.common.FileManagerService;
import com.sheena.memo.post.dao.PostDAO;
import com.sheena.memo.post.model.PostModel;

@Service
public class PostBO {
	
	@Autowired
	private PostDAO postDAO;
	
	public int addPost(int userId, String subject, String content, MultipartFile file) {
		
		// 파일을 저장하고, 경로를 만들어 낸다.
		String filePath = FileManagerService.saveFile(userId, file);
	
		return postDAO.insertPost(userId, subject, content, filePath);
	}
	
	
	
	public List<PostModel> getPostList(int userId) {
		return postDAO.selectPostList(userId);
	}
	
	
	public PostModel getPost(int id) {
		
		return postDAO.selectPost(id);
	}
	
	
	
	// 메모 수정
	public int updatePost(int postId, String subject, String content) {
		return postDAO.updatePost(postId, subject, content);
	}
	
	
	// 삭제
	public int deletePost(int postId, int userId) {
		
		PostModel post = this.getPost(postId);
		
		// 파일 삭제
		FileManagerService.removeFile(post.getImagePath());
		
		return postDAO.deletePost(postId, userId);
	}
}
