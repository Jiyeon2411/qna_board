package com.qnaboard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnaBoardDto {
	private int num;
	private String name;
	private String subject;
	private String content;
	private String created;
	private int hitCount;
}
