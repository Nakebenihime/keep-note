package org.backend.repository;

import org.backend.model.Notebook;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotebookRepository extends MongoRepository<Notebook, UUID> {
}
