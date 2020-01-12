package com.vandalar.server.note.service;

import com.vandalar.server.note.converter.NoteConverter;
import com.vandalar.server.note.model.NoteCreationRequestDto;
import com.vandalar.server.note.model.NoteCreationResponseDto;
import com.vandalar.server.note.model.NoteDto;
import com.vandalar.server.note.model.NoteWithAuthorDto;
import com.vandalar.server.note.persistence.model.NoteEntity;
import com.vandalar.server.note.persistence.repo.NoteRepository;
import com.vandalar.server.user.User;
import com.vandalar.server.user.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	
	public List<NoteWithAuthorDto> searchNotesByRadius(double lat, double lon, double height, double radius) {
		
		List<NoteWithAuthorDto> result = new ArrayList<>();
		
		for(NoteEntity note : noteRepository.findAll()) {
			
			Sphere sphere = new Sphere(lat, lon, height, radius);
			
			if (!isNoteInSphere(note, sphere)) {
				continue;
			}
			
			NoteWithAuthorDto noteWithAuthorDto = createNoteWithAuthorDtoByNote(note);
			
			result.add(noteWithAuthorDto);
		}
		
		return result;
	}
	
	private boolean isNoteInSphere(NoteEntity note, Sphere sphere) {
		
		if (note == null || sphere == null) {
			return false;
		}
		
		double squaredDistanceToSphereCenter = Math.pow(note.getLat() - sphere.getLat(), 2)
				+ Math.pow(note.getLon() - sphere.getLon(), 2)
				+ Math.pow(note.getHeight() - sphere.getHeight(), 2);
		
		double distanceToSphereCenter = Math.sqrt(squaredDistanceToSphereCenter);
		
		return distanceToSphereCenter <= sphere.getRadius();
	}
	
	private NoteWithAuthorDto createNoteWithAuthorDtoByNote(NoteEntity note) {
		
		String publicUserId = note.getUserId();
		
		NoteWithAuthorDto result = new NoteWithAuthorDto();
		
		result.setId(note.getId());
		result.setContent(note.getContent());
		result.setUser_id(publicUserId);
		result.setLat(note.getLat());
		result.setLon(note.getLon());
		result.setHeight(note.getHeight());
		
		Optional.of(note)
				.map(NoteEntity::getCreationDateTime)
				.map(LocalDateTime::toString)
				.ifPresent(result::setCreated);
		
		if (publicUserId != null) {
			Optional.ofNullable(userService.getByPublicId(publicUserId))
					.map(User::getName)
					.ifPresent(result::setUser_name);
		}
		
		return result;
	}
	
	@Data
	@AllArgsConstructor
	private static class Sphere {
		
		double lat;
		double lon;
		double height;
		double radius;
	}
}
