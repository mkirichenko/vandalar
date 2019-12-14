package com.vandalar.server.note.converter;

import com.vandalar.server.note.model.NoteCreationRequestDto;
import com.vandalar.server.note.model.NoteDto;
import com.vandalar.server.note.persistence.model.NoteEntity;
import com.vandalar.server.user.persistense.UserEntity;
import com.vandalar.server.user.persistense.UserRepository;
import lombok.AllArgsConstructor;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class NoteConverter {
	
	private final UserRepository userRepository;
	
	public NoteDto convertToNoteDto(NoteEntity noteEntity) {
		
		if (noteEntity == null) {
			return null;
		}
		
		NoteDto result = new NoteDto();
		
		result.setId(noteEntity.getId());
		result.setContent(noteEntity.getContent());
		result.setLat(noteEntity.getLat());
		result.setLon(noteEntity.getLon());
		result.setHeight(noteEntity.getHeight());
		
		String localDateTime = Optional.of(noteEntity)
				.map(NoteEntity::getCreationDateTime)
				.map(LocalDateTime::toString)
				.orElse(null);
		
		result.setCreated(localDateTime);
		
		return result;
	}
	
	public NoteEntity convertToNoteEntity(NoteCreationRequestDto note) {
		
		NoteEntity result = new NoteEntity();
		
		Optional.ofNullable(userRepository.getOne(note.getPrivateUserId()))
				.map(UserEntity::getPublicId)
				.ifPresent(result::setUserId);
		
		result.setContent(note.getContent());
		result.setLat(note.getLat());
		result.setLon(note.getLon());
		result.setHeight(note.getHeight());
		result.setCreationDateTime(LocalDateTime.now());
		
		return result;
	}
}
