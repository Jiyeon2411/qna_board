package com.qnaboard.service;

import com.qnaboard.dto.QnaBoardDto;

public interface QnaBoardService {
	public int maxNum() throws Exception;
	
	public void insertData(QnaBoardDto qnaboardto) throws Exception;
	
	public int getDataCount(String searchKey, String searchValue, int start, int end) throws Exception;
}
