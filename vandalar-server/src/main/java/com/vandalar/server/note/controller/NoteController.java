package com.vandalar.server.note.controller;

import com.vandalar.server.note.model.NoteCreationRequestDto;
import com.vandalar.server.note.model.NoteCreationResponseDto;
import com.vandalar.server.note.model.NoteDto;
import com.vandalar.server.note.model.NoteEditRequestDto;
import com.vandalar.server.note.service.NoteService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/note")
public class NoteController {

	private static final String USER_ID_HEADER_NAME = "X_USER_ID";

	private final NoteService noteService;

	@PostMapping
	public NoteCreationResponseDto createNote(@RequestHeader(USER_ID_HEADER_NAME) String privateUserId,
	                                          @Validated @RequestBody NoteCreationRequestDto noteCreationRequest) {

		return noteService.createNote(privateUserId, noteCreationRequest);
	}

	@GetMapping("/{id}")
	public NoteDto getNoteById(@RequestHeader(USER_ID_HEADER_NAME) String privateUserId,
                               @PathVariable("id") long id) {

		return noteService.getNoteById(privateUserId, id);
	}

	@GetMapping
	public List<NoteDto> getNotesByUser(@RequestHeader(USER_ID_HEADER_NAME) String privateUserId) {
		return noteService.getNotesByUser(privateUserId);
	}

	@PutMapping("/{id}")
	public NoteCreationResponseDto updateNote(@RequestHeader(USER_ID_HEADER_NAME) String privateUserId,
											  @PathVariable("id") long id,
											  @Validated @RequestBody NoteEditRequestDto noteEditRequest) {

		return noteService.updateNote(privateUserId, id, noteEditRequest.getContent());
	}
}