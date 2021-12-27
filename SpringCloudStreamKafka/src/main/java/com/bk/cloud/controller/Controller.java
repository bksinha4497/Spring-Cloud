package com.bk.cloud.controller;

import java.lang.annotation.Annotation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
	@GetMapping
	public String getData() {
		return "";
	}
	

}
