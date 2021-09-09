package org.backend.seeder;

import lombok.extern.slf4j.Slf4j;
import org.backend.model.Note;
import org.backend.model.Notebook;
import org.backend.repository.NoteRepository;
import org.backend.repository.NotebookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class Seeder implements CommandLineRunner {

    private final NotebookRepository noteBookRepository;
    private final NoteRepository noteRepository;

    public Seeder(NotebookRepository noteBookRepository, NoteRepository noteRepository) {
        this.noteBookRepository = noteBookRepository;
        this.noteRepository = noteRepository;
    }

    @Override
    public void run(String... args) {

        this.noteBookRepository.deleteAll();
        this.noteRepository.deleteAll();

        var defaultNoteBook = Notebook.builder().id(UUID.randomUUID()).name("DEFAULT").build();
        log.info(defaultNoteBook.getName() + " " + defaultNoteBook.getId().toString());
        this.noteBookRepository.save(defaultNoteBook);

        var quotesNoteBook = Notebook.builder().id(UUID.randomUUID()).name("QUOTES").build();
        log.info(quotesNoteBook.getName() + " " + quotesNoteBook.getId().toString());
        this.noteBookRepository.save(quotesNoteBook);

        var defaultNote = Note.builder().id(UUID.randomUUID()).title("Welcome").text("Welcome to ANGULAR NOTE").notebook(defaultNoteBook).lastModifiedOn(new Date()).build();
        log.info("default note : {}", defaultNote);
        this.noteRepository.save(defaultNote);
        log.info("INITIALIZED DATABASE");

    }
}
