package com.vandalar.server.note.service;

import com.vandalar.server.note.converter.NoteConverter;
import com.vandalar.server.note.model.NoteWithAuthorDto;
import com.vandalar.server.note.persistence.model.NoteEntity;
import com.vandalar.server.note.persistence.repo.NoteRepository;
import com.vandalar.server.user.UserService;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
public class NoteSearchService {

    private final NoteRepository noteRepository;

    private final NoteConverter noteConverter;

    private final UserService userService;

    public NoteSearchService(NoteRepository noteRepository, NoteConverter noteConverter, UserService userService) {
        this.noteRepository = noteRepository;
        this.noteConverter = noteConverter;
        this.userService = userService;
    }

    public List<NoteWithAuthorDto> searchNotesByRadius(String privateUserId,
                                                       double lat,
                                                       double lon,
                                                       double height,
                                                       double radius) {

        Sphere sphere = new Sphere(lat, lon, height, radius);
        List<NoteWithAuthorDto> result = new ArrayList<>();

        for (NoteEntity note : noteRepository.findAll()) {
            if (!isNoteInSphere(note, sphere)) {
                continue;
            }

            String publicUserId = note.getUserId();
            String userName = userService.getUserByPublicId(publicUserId)
                .getName();

            NoteWithAuthorDto noteWithAuthorDto = noteConverter
                .convertToNoteWithAuthorDto(note, publicUserId, userName);

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

    @Data
    @AllArgsConstructor
    private static class Sphere {

        double lat;
        double lon;
        double height;
        double radius;
    }
}