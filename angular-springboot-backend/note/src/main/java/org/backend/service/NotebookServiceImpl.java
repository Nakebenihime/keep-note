package org.backend.service;


import org.backend.model.Notebook;
import org.backend.repository.NotebookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotebookServiceImpl implements ServiceImpl<Notebook> {

    private NotebookRepository notebookRepository;

    @Autowired
    public void setNoteRepository(NotebookRepository notebookRepository) {
        this.notebookRepository = notebookRepository;
    }

    @Override
    public Notebook save(Notebook notebook) {
        return this.notebookRepository.save(notebook);
    }

    @Override
    public Optional<Notebook> findById(String id) {
        return this.notebookRepository.findById(UUID.fromString(id));
    }

    @Override
    public List<Notebook> findAll() {
        return this.notebookRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        this.notebookRepository.deleteById(UUID.fromString(id));
    }
}
