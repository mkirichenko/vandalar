package com.vandalar.server.note.converter;

import com.vandalar.server.note.model.NoteCreationRequestDto;
import com.vandalar.server.note.model.NoteDto;
import com.vandalar.server.note.persistence.model.NoteEntity;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class NoteConverter {

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
	
	public NoteEntity convertToNoteEntity(String userId, NoteCreationRequestDto note) {
		
		NoteEntity result = new NoteEntity();

		result.setUserId(userId);
		result.setContent(note.getContent());
		result.setLat(note.getLat());
		result.setLon(note.getLon());
		result.setHeight(note.getHeight());
		result.setCreationDateTime(LocalDateTime.now());
		
		return result;
	}
}
