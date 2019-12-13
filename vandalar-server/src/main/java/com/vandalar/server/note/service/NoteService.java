package com.vandalar.server.note.service;

import com.vandalar.server.note.converter.NoteConverter;
import com.vandalar.server.note.model.NoteCreationRequestDto;
import com.vandalar.server.note.model.NoteCreationResponseDto;
import com.vandalar.server.note.model.NoteDto;
import com.vandalar.server.note.persistence.model.NoteEntity;
import com.vandalar.server.note.persistence.repo.NoteRepository;
import com.vandalar.server.user.persistense.UserEntity;
import com.vandalar.server.user.persistense.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class NoteService {
	
	private final NoteRepository noteRepository;
	
	private final UserRepository userRepository;
	
	private final NoteConverter noteConverter;
	
	public NoteCreationResponseDto createNote(NoteCreationRequestDto request) {
		
		NoteEntity noteEntity = noteConverter.convertToNoteEntity(request);
		
		NoteEntity savedNote = noteRepository.save(noteEntity);
		
		return new NoteCreationResponseDto(savedNote.getId());
	}
	
	public NoteDto getNoteById(long id) {
		
		NoteEntity noteEntity = noteRepository.findById(id);
		
		return noteConverter.convertToNoteDto(noteEntity);
	}
	
	public List<NoteDto> getNotesByUser(String privateUserId) {
		
		return Optional.ofNullable(userRepository.getOne(privateUserId))
				.map(UserEntity::getPublicId)
				.map(noteRepository::findAllByUserId)
				.map(Collection::stream)
				.orElse(Stream.empty())
				.map(noteConverter::convertToNoteDto)
				.collect(Collectors.toList());
	}
}
