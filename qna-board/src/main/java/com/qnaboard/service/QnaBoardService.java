package com.qnaboard.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qnaboard.dto.QnaBoardDto;

@Service
public interface QnaBoardService {
	public int maxNum() throws Exception;
	
	public void insertData(QnaBoardDto qnaboarddto) throws Exception;
	
	public int getDataCount(String searchKey, String searchValue) throws Exception;
	
	public List<QnaBoardDto> getLists(int start, int end, String searchKey, String searchValue) throws Exception;
	
	public void updateHitCount(int num) throws Exception;
	
	public QnaBoardDto getReadData(int num) throws Exception;
	
	public void updateData(QnaBoardDto qnaboarddto) throws Exception;
}
