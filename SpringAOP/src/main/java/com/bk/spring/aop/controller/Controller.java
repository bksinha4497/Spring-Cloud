package com.bk.spring.aop.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bk.spring.aop.model.LogModel;


@RestController
public class Controller {

	@PostMapping(value = "/getModel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public LogModel getSomething(@RequestBody LogModel model) {
		if (model.getName().startsWith("get")) {
			return new LogModel();
		}
		throw new RuntimeException("Run Time Exception");
	}

}
