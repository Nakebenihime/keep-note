package org.backend.mapper;

import org.backend.model.Note;
import org.backend.model.Notebook;
import org.backend.repository.NotebookRepository;
import org.backend.viewmodel.NoteViewModel;
import org.backend.viewmodel.NotebookViewModel;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Component that handles all mappings in this project
 * - entity to view model
 * - view model to entity
 * <p>
 * All mappings are handled here, but in production code this is not the
 * best approach. You can take a look at ModelMapper project or at least split mapping classes
 * across many files.
 */
@Component
public class Mapper {
    private final NotebookRepository notebookRepository;

    public Mapper(NotebookRepository notebookRepository) {
        this.notebookRepository = notebookRepository;
    }

    public NoteViewModel convertToNoteViewModel(Note entity) {
        var viewModel = new NoteViewModel();
        viewModel.setTitle(entity.getTitle());
        viewModel.setId(entity.getId().toString());
        viewModel.setLastModifiedOn(entity.getLastModifiedOn());
        viewModel.setText(entity.getText());
        viewModel.setNotebookId(entity.getNotebook().getId().toString());

        return viewModel;
    }

    public Note convertToNoteEntity(NoteViewModel viewModel) {
        var notebook = this.notebookRepository.findById(UUID.fromString(viewModel.getNotebookId())).get();
        return new Note(viewModel.getId(), viewModel.getTitle(), viewModel.getText(), notebook);
    }

    public NotebookViewModel convertToNotebookViewModel(Notebook entity) {
        var viewModel = new NotebookViewModel();
        viewModel.setId(entity.getId().toString());
        viewModel.setName(entity.getName());
        viewModel.setNbNotes(entity.getNotes().size());

        return viewModel;
    }

    public Notebook convertToNotebookEntity(NotebookViewModel viewModel) {

        return new Notebook(viewModel.getId(), viewModel.getName());
    }

}