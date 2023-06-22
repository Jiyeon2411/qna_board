package com.qnaboard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.qnaboard.dto.QnaBoardDto;

@Mapper
public interface QnaBoardDao {
	public int maxNum() throws Exception;
	
	public void insertData() throws Exception;
	
	public int getDataCount(String searchKey, String searchValue) throws Exception;
	
	public List<QnaBoardDto> getLists(int start, int end, String searchKey, String searchValue) throws Exception;
	
	public void updateHitCount(int num) throws Exception;
	
	public QnaBoardDto getReadData(int num) throws Exception;
}
