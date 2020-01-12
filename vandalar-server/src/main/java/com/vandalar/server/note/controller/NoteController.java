package com.vandalar.server.note.controller;

import com.vandalar.server.note.model.NoteCreationRequestDto;
import com.vandalar.server.note.model.NoteCreationResponseDto;
import com.vandalar.server.note.model.NoteDto;
import com.vandalar.server.note.model.NoteEditRequestDto;
import com.vandalar.server.note.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/note")
public class NoteController {
	
	private static final String USER_ID_HEADER_NAME = "X_USER_ID";
	
	private final NoteService noteService;
	
	@PostMapping
	public NoteCreationResponseDto createNote(@RequestHeader(USER_ID_HEADER_NAME) String privateUserId,
	                                          @RequestBody NoteCreationRequestDto noteCreationRequest) {
		noteCreationRequest.setPrivateUserId(privateUserId);
		return noteService.createNote(noteCreationRequest);
	}
	
	@GetMapping("/{id}")
	public NoteDto getNoteById(@RequestHeader(USER_ID_HEADER_NAME) String privateUserId, @PathVariable("id") long id) {
		return noteService.getNoteById(id);
	}
	
	@GetMapping
	public List<NoteDto> getNotesByUser(@RequestHeader(USER_ID_HEADER_NAME) String privateUserId) {
		return noteService.getNotesByUser(privateUserId);
	}

	@PutMapping("/{id}")
	public ResponseEntity<NoteCreationResponseDto> updateNote(@RequestHeader(USER_ID_HEADER_NAME) String privateUserId,
															 @PathVariable("id") long id,
															 @RequestBody NoteEditRequestDto noteEditRequest) {
		return noteService.editNote(privateUserId, id, noteEditRequest.getContent());
	}

}
