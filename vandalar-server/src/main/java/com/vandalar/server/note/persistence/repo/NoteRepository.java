package com.vandalar.server.note.persistence.repo;

import com.vandalar.server.note.persistence.model.NoteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends CrudRepository<NoteEntity, Long> {

	NoteEntity findById(long id);

	NoteEntity findByIdAndUserId(long id, String userId);

	List<NoteEntity> findAllByUserId(String userId);
}
