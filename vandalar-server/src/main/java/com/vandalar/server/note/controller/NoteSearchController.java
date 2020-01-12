package com.vandalar.server.note.controller;

import com.vandalar.server.note.model.NoteWithAuthorDto;
import com.vandalar.server.note.service.NoteSearchService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/note-search")
public class NoteSearchController {
	
	private static final String USER_ID_HEADER_NAME = "X_USER_ID";
	
	private final NoteSearchService noteSearchService;
	
	@GetMapping
	public List<NoteWithAuthorDto> searchNotes(@RequestHeader(USER_ID_HEADER_NAME) String privateUserId,
	                                           @RequestParam double lat,
	                                           @RequestParam double lon,
	                                           @RequestParam double height,
	                                           @RequestParam double radius) {
		
		return noteSearchService.searchNotesByRadius(privateUserId, lat, lon, height, radius);
	}
}