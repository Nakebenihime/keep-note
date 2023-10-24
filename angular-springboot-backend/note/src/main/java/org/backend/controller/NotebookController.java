package org.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.backend.mapper.Mapper;
import org.backend.model.Note;
import org.backend.model.Notebook;
import org.backend.service.NoteServiceImpl;
import org.backend.service.NotebookServiceImpl;
import org.backend.viewmodel.NoteViewModel;
import org.backend.viewmodel.NotebookViewModel;
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
@RequestMapping(NotebookController.PATH)
public class NotebookController {

    public static final String PATH = "/api/v1/notebooks";

    private final Mapper mapper;
    private final NotebookServiceImpl notebookService;
    private final NoteServiceImpl noteService;

    @Autowired
    public NotebookController(Mapper mapper, NotebookServiceImpl notebookService, NoteServiceImpl noteService) {
        this.mapper = mapper;
        this.notebookService = notebookService;
        this.noteService = noteService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NotebookViewModel>> getAll() {
        List<Notebook> notebooks = this.notebookService.findAll();
        var notebooksViewModel = notebooks.stream()
                .map(notebook -> this.mapper.convertToNotebookViewModel(notebook))
                .collect(Collectors.toList());
        return ResponseEntity
                .ok(notebooksViewModel);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotebookViewModel> getById(@PathVariable String id) {
        log.info("fetching notebook with id {}", id);
        Optional<Notebook> notebook = this.notebookService.findById(id);
        if (notebook.isEmpty()) {
            log.error("notebook with id {} not found.", id);
            return ResponseEntity
                    .notFound()
                    .build();
        }
        var notebookViewModel = this.mapper.convertToNotebookViewModel(notebook.get());
        return ResponseEntity
                .ok(notebookViewModel);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Notebook> save(@RequestBody NotebookViewModel notebookViewModel, BindingResult bindingResult, UriComponentsBuilder uriComponentsBuilder) {
        if (bindingResult.hasErrors()) {
            log.error("unable to save notebook{} errors were found.", notebookViewModel);
            throw new ValidationException();
        }
        var notebookEntity = this.mapper.convertToNotebookEntity(notebookViewModel);
        log.info("creating notebook : {}", notebookEntity);
        UriComponents uriComponents = uriComponentsBuilder.path("/api/v1/notebooks/{id}").buildAndExpand(notebookEntity.getId());
        return ResponseEntity
                .created(uriComponents.toUri())
                .body(this.notebookService.save(notebookEntity));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        log.info("fetching & deleting notebook with id {}", id);
        if (this.notebookService.findById(id).isEmpty()) {
            log.error("unable to delete notebook with id {} not found.", id);
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        this.noteService.deleteAllById(id);
        log.info("deleting all notes inside notebook with id {}", id);
        this.notebookService.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping(value = "/{id}/notes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NoteViewModel>> getAllByNotebook(@PathVariable String id) {
        List<Note> notes;
        log.info("fetching notebook with id {}", id);
        Optional<Notebook> notebook = this.notebookService.findById(id);
        if (notebook.isEmpty()) {
            log.error("unable to fetch notebook with id {} not found.", id);
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        notes = this.noteService.getAllByNotebook(notebook.get());
        var notesViewModel = notes.stream()
                .map(note -> this.mapper.convertToNoteViewModel(note))
                .collect(Collectors.toList());
        return ResponseEntity
                .ok(notesViewModel);
    }
}
