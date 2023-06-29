package com.qnaboard.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

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
		return "/index";
	}
	
	@RequestMapping(value = "/created", method = RequestMethod.GET)
	public String created() {
		return "bbs/created";
	}
	
	@RequestMapping(value = "/created", method = RequestMethod.POST)
	public String createdOK(QnaBoardDto qnaboarddto, HttpServletRequest request, Model model) {
		
		try {
			int maxNum = qnaBoardService.maxNum();
			qnaboarddto.setNum(maxNum + 1);
			qnaBoardService.insertData(qnaboarddto);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "글 작성 중 에러가 발생했습니다.");
			return "bbs/created";
		}
		return "redirect:/list";
	}
	
	@RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
	public String list(QnaBoardDto qnaboardto, HttpServletRequest request, Model model) {
		
		try {
			String pageNum = request.getParameter("pageNum");
			int currentPage = 1;
			
			if(pageNum!= null)
				currentPage = Integer.parseInt(pageNum);
			
			String searchKey = request.getParameter("searchKey");
			String searchValue = request.getParameter("searchValue");
			
			if(searchValue == null) {
				searchKey = "subject";
				searchValue = "";
			} else {
				if(request.getMethod().equalsIgnoreCase("GET")) {
					searchValue = URLDecoder.decode(searchValue, "UTF-8");
			}
			}
			
			int dataCount = qnaBoardService.getDataCount(searchKey, searchValue);
			
			int numPerPage = 5;
			int totalPage = Util.getPageCount(numPerPage, dataCount);
			
			if(currentPage > totalPage)
				currentPage = totalPage;
			
			int start = (currentPage-1) * numPerPage + 1;
			int end = currentPage * numPerPage;
			
			List<QnaBoardDto> lists = qnaBoardService.getLists(start, end, searchKey, searchValue);
			
			String param = "";
			
			if(searchValue != null&&!searchValue.equals("")) {
				param = "searchKey=" + searchKey;
				param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
			}
			
			String listUrl = "/list";
			
			if(!param.equals("")) {
				listUrl += "?" + param;
			}
			
			String pageIndexList = Util.pageIndexList(currentPage, totalPage, listUrl);
			
			String articleUrl = "/article?pageNum=" + currentPage;
			
			if(!param.equals("")) {
				articleUrl += "&" + param;
			}
			
			model.addAttribute("lists", lists);
			model.addAttribute("articleUrl", articleUrl);
			model.addAttribute("pageIndexList", pageIndexList);
			model.addAttribute("dataCount", dataCount);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "목록을 불러오는중 에러가 발생했습니다.");
		}
		
		return "bbs/list";
	}
	
	@RequestMapping(value = "/article",
			method = RequestMethod.GET)
	public String article(HttpServletRequest request, Model model) {
			
			try {
				int num = Integer.parseInt(request.getParameter("num"));
				String pageNum = request.getParameter("pageNum");
				String searchKey = request.getParameter("searchKey");
				String searchValue = request.getParameter("searchValue");
				
				if(searchValue != null) {
				searchValue = URLDecoder.decode(searchValue, "UTF-8");
				} 
				
				qnaBoardService.updateHitCount(num);
				
				QnaBoardDto qnaboarddto = qnaBoardService.getReadData(num);
				
				if(qnaboarddto == null) {
					return "redirect:/list?pageNum=" + pageNum;
				}
				
				String param = "pageNum=" + pageNum;
				if(searchValue != null && !searchValue.equals("")) {
					
					param += "&searchKey=" + searchKey;
					param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
				}
				
				model.addAttribute("qnaboarddto", qnaboarddto);
				model.addAttribute("params", param);
				model.addAttribute("pageNum", pageNum);
				
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("errorMessage", "게시글을 불러오는 중 에러가 발생했습니다.");
			}
				return "bbs/article";
	}
	
	@RequestMapping(value = "/updated", method = RequestMethod.GET)
	public String updated(HttpServletRequest request, Model model) {
		
		try {
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue != null) {
			searchValue = URLDecoder.decode(searchValue, "UTF-8");
		}
		
		QnaBoardDto qnaboarddto = qnaBoardService.getReadData(num);
		
		if(qnaboarddto == null) {
			return "redirect:/list?pageNum=" + pageNum;
		}
		
		String param = "pageNum=" + pageNum;
		
		if(searchValue != null && !searchValue.equals("")) {
			param += "&searchKey=" + searchKey;
			param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		model.addAttribute("qnaboarddto", qnaboarddto);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("params", param);
		model.addAttribute("searchKey", searchKey);
		model.addAttribute("searchValue", searchValue);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "bbs/updated";
	}
	
	@RequestMapping(value = "/updated_ok", method = RequestMethod.POST)
	public String updatedOK(QnaBoardDto qnaboarddto, HttpServletRequest request, Model model) {
		String pageNum = request.getParameter("pageNum"); 
		String searchKey = request.getParameter("searchKey"); 
		String searchValue = request.getParameter("searchValue"); 
		String param = "?pageNum=" + pageNum;
		
		try {
			qnaboarddto.setContent(qnaboarddto.getContent().replaceAll("<br/>", "\r\n"));
			qnaBoardService.updateData(qnaboarddto);
			
			if(searchValue != null && !searchValue.equals("")) {
				param += "&searchKey=" + searchKey;
				param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8"); 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
	
			try {
				param += "&errorMessage=" + URLEncoder.encode("수정 중 에러가 발생했습니다.", "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}
		
		return "redirect:/list" + param;
	}
	
	@RequestMapping(value = "/deleted_ok", method= {RequestMethod.GET})
	public String deleteOK(HttpServletRequest request, Model model) {
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		String searchKey = request.getParameter("searchKey"); 
		String searchValue = request.getParameter("searchValue"); 
		String param = "?pageNum=" + pageNum;
		
		try {
			qnaBoardService.deleteData(num);
			if(searchValue != null && !searchValue.equals("")) {
				param += "&searchKey=" + searchKey;
				param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			try {
				param += "&errorMessage=" + URLEncoder.encode("게시글 삭제 중 에러가 발생했습니다.", "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}
		
		return "redirect:/list" + param;
		}
	}
