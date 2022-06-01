package com.devsuperior.dslearnbds.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dslearnbds.dtos.NotificationDTO;
import com.devsuperior.dslearnbds.services.NotificationService;

@RestController
@RequestMapping(value = "/notifications")
public class NotificationController {

	@Autowired
	private NotificationService service;
	
	@GetMapping
	public ResponseEntity<Page<NotificationDTO>> NotificationsPageByUser(
			@RequestParam(name = "unreadOnly", defaultValue = "false") Boolean unreadOnly, Pageable pageable) {
		
		Page<NotificationDTO> page = service.getNotifications(unreadOnly, pageable);
		return ResponseEntity.ok().body(page);
	}
}
