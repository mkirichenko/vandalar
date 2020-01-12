package com.vandalar.server.note.controller;

import com.vandalar.server.note.model.NoteWithAuthorDto;
import com.vandalar.server.note.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/note-search")
public class NoteSearchController {
	
	private static final String USER_ID_HEADER_NAME = "X_USER_ID";
	
	private final NoteService noteService;
	
	@GetMapping
	public List<NoteWithAuthorDto> searchNotes(@RequestHeader(USER_ID_HEADER_NAME) String privateUserId,
	                                           @RequestParam double lat,
	                                           @RequestParam double lon,
	                                           @RequestParam double height,
	                                           @RequestParam double radius) {
		
		return noteService.searchNotesByRadius(lat, lon, height, radius);
	}
}
