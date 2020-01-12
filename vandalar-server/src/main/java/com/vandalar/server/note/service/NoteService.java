package com.vandalar.server.note.service;

import com.vandalar.server.note.converter.NoteConverter;
import com.vandalar.server.note.model.NoteCreationRequestDto;
import com.vandalar.server.note.model.NoteCreationResponseDto;
import com.vandalar.server.note.model.NoteDto;
import com.vandalar.server.note.persistence.model.NoteEntity;
import com.vandalar.server.note.persistence.repo.NoteRepository;
import com.vandalar.server.user.User;
import com.vandalar.server.user.UserService;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NoteService {
	
	private final NoteRepository noteRepository;

	private final NoteConverter noteConverter;

	private final UserService userService;
	
	public NoteCreationResponseDto createNote(String privateUserId, NoteCreationRequestDto request) {
		String publicUserId = userService.getUser(privateUserId)
			.getPublicId();

		NoteEntity noteEntity = noteConverter.convertToNoteEntity(publicUserId, request);
		
		NoteEntity savedNote = noteRepository.save(noteEntity);
		
		return new NoteCreationResponseDto(savedNote.getId());
	}
	
	public NoteDto getNoteById(String privateUserId, long id) {
		NoteEntity noteEntity = getNoteEntity(id);
		
		return noteConverter.convertToNoteDto(noteEntity);
	}
	
	public List<NoteDto> getNotesByUser(String privateUserId) {
		return Optional.ofNullable(userService.getUser(privateUserId))
				.map(User::getPublicId)
				.map(noteRepository::findAllByUserId)
				.map(Collection::stream)
				.orElse(Stream.empty())
				.map(noteConverter::convertToNoteDto)
				.collect(Collectors.toList());
	}

	@Transactional
	public NoteCreationResponseDto updateNote(String privateUserId, long id, String content) {
		NoteEntity noteEntity = getNoteEntity(id);

		String publicUserId = userService.getUser(privateUserId)
			.getPublicId();

		if (!noteEntity.getUserId().equals(publicUserId)) {
			throw new RuntimeException("note with id " + id + " does not belong to user with id " + privateUserId);
		}

		noteEntity.setContent(content);

		return new NoteCreationResponseDto(noteEntity.getId());
	}

	private NoteEntity getNoteEntity(long id) {
		NoteEntity noteEntity = noteRepository.findById(id);

		if (noteEntity == null) {
			throw new RuntimeException("note with id " + id + " is not found");
		}

		return noteEntity;
	}
}