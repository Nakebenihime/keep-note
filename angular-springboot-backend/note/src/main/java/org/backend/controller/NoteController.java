package org.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.backend.mapper.Mapper;
import org.backend.model.Note;
import org.backend.service.NoteServiceImpl;
import org.backend.viewmodel.NoteViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/notes")
public class NoteController {

    private final NoteServiceImpl noteService;
    private final Mapper mapper;

    @Autowired
    public NoteController(NoteServiceImpl noteService, Mapper mapper) {
        this.noteService = noteService;
        this.mapper = mapper;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NoteViewModel>> getAll() {
        List<Note> notes = this.noteService.findAll();
        var notesViewModel = notes.stream()
                .map(note -> this.mapper.convertToNoteViewModel(note))
                .collect(Collectors.toList());
        return ResponseEntity
                .ok(notesViewModel);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NoteViewModel> getById(@PathVariable String id) {
        log.info("fetching note with id {}", id);
        Optional<Note> note = this.noteService.findById(id);
        if (note.isEmpty()) {
            log.error("note with id {} not found.", id);
            return ResponseEntity
                    .notFound()
                    .build();
        }
        var noteViewModel = this.mapper.convertToNoteViewModel(note.get());
        return ResponseEntity
                .ok(noteViewModel);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Note> save(@RequestBody NoteViewModel noteViewModel, BindingResult bindingResult, UriComponentsBuilder uriComponentsBuilder) {
        if (bindingResult.hasErrors()) {
            log.error("unable to save note{} errors were found.", noteViewModel);
            throw new ValidationException();
        }
        var noteEntity = this.mapper.convertToNoteEntity(noteViewModel);
        log.info("creating note : {}", noteEntity);
        UriComponents uriComponents = uriComponentsBuilder.path("/api/v1/notes/{id}").buildAndExpand(noteEntity.getId());
        return ResponseEntity
                .created(uriComponents.toUri())
                .body(this.noteService.save(noteEntity));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        log.info("fetching & deleting note with id {}", id);
        if (this.noteService.findById(id).isEmpty()) {
            log.error("unable to delete note with id {} not found.", id);
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        this.noteService.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
