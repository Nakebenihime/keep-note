import {Component, OnInit} from '@angular/core';
import {Notebook} from './model/notebook';
import {ApiService} from '../shared/api.service';
import {Note} from './model/note';

@Component({
  selector: 'app-notes',
  templateUrl: './notes.component.html',
  styleUrls: ['./notes.component.css']
})
export class NotesComponent implements OnInit {
  notebooks: Notebook[] = [];
  notes: Note[] = [];
  selectedNotebook: Notebook;
  searchFilter: string;

  constructor(private apiService: ApiService) {
  }

  ngOnInit(): void {
    this.getAllNotebooks();
    this.getAllNotes();
  }

  public getAllNotebooks(): void {
    this.apiService.getAllNotebooks().subscribe(
      res => {
        this.notebooks = res;
      },
      err => {
        alert('Error has occurred while trying to fetch all notebooks from database');
      }
    );
  }

  public createNotebook(): void {
    const notebook: Notebook = {
      id: null,
      name: 'New Notebook',
      nbOfNotes: 0
    };

    this.apiService.postNotebook(notebook).subscribe(
      res => {
        notebook.id = res.id;
        this.notebooks.push(notebook);
      },
      err => {
        alert('Error has occurred while trying to add a notebook');
      }
    );
  }

  public updateNotebook(notebook: Notebook): void {
    this.apiService.postNotebook(notebook).subscribe(
      res => {
      },
      err => {
        alert('Error has occurred while trying to update a notebook');
      }
    );
  }

  public deleteNotebook(notebook: Notebook): void {
    if (confirm('Are you sure you want to delete a notebook ?')) {
      this.apiService.deleteNotebook(notebook.id).subscribe(
        res => {
          const indexOfNotebook = this.notebooks.indexOf(notebook);
          this.notebooks.splice(indexOfNotebook, 1);
        },
        err => {
          alert('Error has occurred while trying to delete a notebook');
        }
      );
    }
  }

  public getAllNotes(): void {
    this.apiService.getAllNotes().subscribe(
      res => {
        this.notes = res;
      },
      err => {
        alert('Error has occurred while trying to fetch all notes from database');
      }
    );
  }

  public createNote(notebookId: string): void {
    const note: Note = {
      id: null,
      title: 'New Note',
      text: 'Write some text in here',
      notebookId,
      lastModifiedOn: null
    };

    this.apiService.postNote(note).subscribe(
      res => {
        note.id = res.id;
        this.notes.push(note);
      },
      err => {
        alert('Error has occurred while trying to add a note');
      }
    );
  }

  selectNotebook(notebook: Notebook): void {
    this.selectedNotebook = notebook;
    this.apiService.getAllNotesByNotebook(notebook.id).subscribe(
      res => {
        this.notes = res;
      },
      err => {
        alert('Error has occurred while trying to fetching the notes associated to this notebook');
      }
    );

  }

  public deleteNote(note: Note): void {
    if (confirm('Are you sure you want to delete a note?')) {
      this.apiService.deleteNote(note.id).subscribe(
        res => {
          const indexOfNote = this.notes.indexOf(note);
          this.notes.splice(indexOfNote, 1);
        },
        err => {
          alert('Error has occurred while trying to delete a note');
        }
      );
    }
  }

  public updateNote(note: Note): void {
    this.apiService.postNote(note).subscribe(
      res => {
      },
      err => {
        alert('Error has occurred while trying to update a note');
      }
    );
  }

  showAllNotes(): void {
    this.selectedNotebook = null;
    this.getAllNotes();
  }
}
