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

        double distanceToSphereCenter = distanceBetween(note.getLat(), sphere.getLat(),
                                                        note.getLon(), sphere.getLon(),
                                                        note.getHeight(), sphere.getHeight());

        return distanceToSphereCenter <= sphere.getRadius();
    }

    private double distanceBetween(double lat1, double lat2,
                                   double lon1, double lon2,
                                   double h1, double h2) {

        int R = 6371009;

        double phi1 = Math.toRadians(lat1);
        double phi2 = Math.toRadians(lat2);

        double dPhi = Math.toRadians(lat2 - lat1);
        double dLambda = Math.toRadians(lon2 - lon1);

        double a = Math.pow(Math.sin(dPhi / 2), 2)
            + Math.cos(phi1) * Math.cos(phi2) * Math.pow(Math.sin(dLambda / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double d = R * c;

        double h = h1 - h2;

        return Math.sqrt(Math.pow(d, 2) + Math.pow(h, 2));
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