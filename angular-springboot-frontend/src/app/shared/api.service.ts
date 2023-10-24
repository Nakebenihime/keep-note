import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {Notebook} from '../notes/model/notebook';
import {FeedbackViewModel} from '../feedback/model/feedbackviewmodel';
import {Note} from '../notes/model/note';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  public BASE_URL_NOTE_API = 'http://localhost:8080/api/v1';
  public BASE_URL_FEEDBACK_API = 'http://localhost:8181/api/v1';

  // FEEDBACK MANAGEMENT
  private SEND_FEEDBACK_URL = `${this.BASE_URL_FEEDBACK_API}\\feedback`;
  // NOTEBOOK MANAGEMENT
  private GET_ALL_NOTEBOOKS_URL = `${this.BASE_URL_NOTE_API}\\notebooks`;
  private SAVE_NOTEBOOK_URL = `${this.BASE_URL_NOTE_API}\\notebooks`;
  private DELETE_NOTEBOOK_URL = `${this.BASE_URL_NOTE_API}\\notebooks\\`;
  // NOTE MANAGEMENT
  private GET_ALL_NOTES_URL = `${this.BASE_URL_NOTE_API}\\notes`;
  private GET_ALL_NOTES_BY_NOTEBOOK_URL = `${this.BASE_URL_NOTE_API}\\notebooks\\`;
  private SAVE_NOTE_URL = `${this.BASE_URL_NOTE_API}\\notes`;
  private DELETE_NOTE_URL = `${this.BASE_URL_NOTE_API}\\notes\\`;

  constructor(private http: HttpClient) {

  }

  getAllNotebooks(): Observable<Notebook[]> {
    return this.http.get<Notebook[]>(this.GET_ALL_NOTEBOOKS_URL);
  }

  postFeedback(feedback: FeedbackViewModel): Observable<any> {
    return this.http.post(this.SEND_FEEDBACK_URL, feedback);
  }

  postNotebook(notebook: Notebook): Observable<Notebook> {
    return this.http.post<Notebook>(this.SAVE_NOTEBOOK_URL, notebook);
  }

  deleteNotebook(notebookId: string): Observable<any> {
    return this.http.delete(this.DELETE_NOTEBOOK_URL + notebookId);
  }

  getAllNotes(): Observable<Note[]> {
    return this.http.get<Note[]>(this.GET_ALL_NOTES_URL);
  }

  getAllNotesByNotebook(notebookId: string): Observable<Note[]> {
    return this.http.get<Note[]>(this.GET_ALL_NOTES_BY_NOTEBOOK_URL + notebookId + '/notes');
  }

  postNote(note: Note): Observable<Note> {
    return this.http.post<Note>(this.SAVE_NOTE_URL, note);
  }

  deleteNote(noteId: string): Observable<any> {
    return this.http.delete(this.DELETE_NOTE_URL + noteId);
  }

  handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'Unknown Error !';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else {
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    window.alert(errorMessage);
    return throwError(errorMessage);
  }

}
