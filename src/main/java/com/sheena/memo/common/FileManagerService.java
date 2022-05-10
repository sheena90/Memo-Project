package com.sheena.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileManagerService {
	
	public final static String FILE_UPLOAD_PATH="C:\\@sheena\\Web\\Spring Project\\memo\\upload\\images/";
	
	// 로거 쌓는 방법
	private static Logger logger = LoggerFactory.getLogger(FileManagerService.class);
	
	// 파일 저장 후, 접근 경로 리턴
	public static String saveFile(int userId, MultipartFile file) {
		
		if(file == null) {
			
			logger.error("FileManagerService-saveFile : 파일 없음");
			
			return null;
		}
		
		// 파일 경로
		// 파일 이름이 겹치는 것을 방지하기 위해 사용자 별로 폴더를 구분한다.
		// 같은 사용자가 같은 파일 이름 겹치는 것을 방지하기 위해서 현재 시간을 폴더 이름에 포함 시킨다.
		// UNIX time:1970년 1월 1일 0시0분0초 기준으로 현재 몇 millisecond 초(1/1000)가 지났는지
		// /images/6_12912098/asdf.jpg
		
		String directoryName = userId + "_" + System.currentTimeMillis() + "/";
		
		// 실제로 파일이 저장되는 경로
		// 
		String filePath = FILE_UPLOAD_PATH + directoryName;
		
		// 디렉토리 생성
		File directory = new File(filePath);
		if(directory.mkdir() == false) {
			// 디렉토리 생성 에러
			logger.error("FileManagerService-saveFile : 디렉토리 생성 에러");
			return null;
		}
		
		// 파일 저장
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(filePath + file.getOriginalFilename());
			Files.write(path, bytes);
			
		} catch (IOException e) {
			
			e.printStackTrace();
			logger.error("FileManagerService-saveFile : 파일 저장 에러");
			return null;
		}
		
		// <img src="/images/6_12912098/test.png">
		// /images/6_12912098/test.png
		return "/images/" + directoryName + file.getOriginalFilename();
		
	}
	
	
	// 파일 삭제
	public static boolean removeFile(String filePath) {
		
		// 파일이 없는 경우의 예외처리
		if(filePath == null) {
			logger.error("FileManagerService-removeFile : 파일 없음");
			return false;
		}
		
		// filePath 경로:/images/2_35975145/test.png
		// 실제 파일 경로: C:\\@sheena\\Web\\Spring Project\\memo\\upload\\images\\2_35975145/test.png
		
		// 경로를 문자열로 만들기, 실제 파일 경로: FILE_UPLOAD_PATH + 지우고 싶은 문자열(2_35975145/test.png)
		String realFilePath = FILE_UPLOAD_PATH + filePath.replace("/images/", "");
		
		// 파일 삭제 (path는 전체 파일 경로)
		Path path = Paths.get(realFilePath);
		// 파일이 있는지 확인
		if(Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				logger.error("FileManagerService-removeFile : 파일 삭제 실패");
				e.printStackTrace();
				return false;
			}
		}
		
		// 실제 파일 경로: C:\\@sheena\\Web\\Spring Project\\memo\\upload\\images\\2_35975145/test.png
		// 디렉토리 삭제
		// 디렉토리 경로 : C:\\@sheena\\Web\\Spring Project\\memo\\upload\\images\\2_35975145
		// path가 위에서 이미 사용이 끝나서 새로운 객체를 생성하는데 사용, 즉 디렉토리 객체
		path = path.getParent();
		
		// 디렉토리 존재 여부 확인
		if(Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				logger.error("FileManagerService-removeFile : 디렉토리 삭제 실패");
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
		
	}
}
