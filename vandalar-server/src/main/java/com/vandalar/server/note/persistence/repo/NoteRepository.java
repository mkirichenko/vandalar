package com.vandalar.server.note.persistence.repo;

import com.vandalar.server.note.persistence.model.NoteEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends CrudRepository<NoteEntity, Long> {

	NoteEntity findById(long id);

	List<NoteEntity> findAllByUserId(String userId);
}
