package com.qnaboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qnaboard.dto.QnaBoardDto;
import com.qnaboard.service.QnaBoardService;
import com.qnaboard.util.Util;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class QnaBoardController {
	@Autowired
	private QnaBoardService qnaBoardService;
	
	@Autowired
	Util util;
	
	@RequestMapping(value = "/")
	public String index() {
		return "/index2";
	}
	
	@RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
	public String list(QnaBoardDto qnaboardto, HttpServletRequest request, Model model) {
		return "bbs/list";
	}
}
