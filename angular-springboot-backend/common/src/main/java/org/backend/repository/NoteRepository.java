package org.backend.repository;

import org.backend.model.Note;
import org.backend.model.Notebook;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NoteRepository extends MongoRepository<Note, UUID> {
    List<Note> findAllByNotebook(Notebook noteBook);

    void deleteAllByNotebook_Id(UUID id);
}
