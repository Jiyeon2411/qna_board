package com.qnaboard.dao;

public interface QnaBoardDao {
	public int maxNum() throws Exception;
	
	public void insertData() throws Exception;
	
	public int getDataCount() throws Exception;
}
