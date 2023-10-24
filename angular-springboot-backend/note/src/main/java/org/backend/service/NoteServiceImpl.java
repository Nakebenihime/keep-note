package org.backend.service;


import org.backend.model.Note;
import org.backend.model.Notebook;
import org.backend.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NoteServiceImpl implements ServiceImpl<Note> {

    private NoteRepository noteRepository;

    @Autowired
    public void setNoteRepository(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public Note save(Note note) {
        return this.noteRepository.save(note);
    }

    @Override
    public Optional<Note> findById(String id) {
        return this.noteRepository.findById(UUID.fromString(id));
    }

    @Override
    public List<Note> findAll() {
        return this.noteRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        this.noteRepository.deleteById(UUID.fromString(id));
    }

    public void deleteAllById(String id) {
        this.noteRepository.deleteAllByNotebook_Id(UUID.fromString(id));
    }

    public List<Note> getAllByNotebook(Notebook notebook) {
        return this.noteRepository.findAllByNotebook(notebook);
    }
}
