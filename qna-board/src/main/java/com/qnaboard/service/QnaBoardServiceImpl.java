package com.qnaboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qnaboard.dao.QnaBoardDao;
import com.qnaboard.dto.QnaBoardDto;

@Service
public class QnaBoardServiceImpl implements QnaBoardService {

	@Autowired
	private QnaBoardDao qnaMapper;

	@Override
	public int maxNum() throws Exception {
		return qnaMapper.maxNum();
	}

	@Override
	public void insertData(QnaBoardDto qnaboarddto) throws Exception {
		qnaMapper.insertData();
	}

	@Override
	public int getDataCount(String searchKey, String searchValue) throws Exception {
		return qnaMapper.getDataCount(searchKey, searchValue);
	}

	@Override
	public List<QnaBoardDto> getLists(int start, int end, String searchKey, String searchValue) throws Exception {
		return qnaMapper.getLists(start, end, searchKey, searchValue);
	}

	@Override
	public void updateHitCount(int num) throws Exception {
		qnaMapper.updateHitCount(num);
	}
	
	@Override
	public QnaBoardDto getReadData(int num) throws Exception {
		return qnaMapper.getReadData(num);
	}

	
}
