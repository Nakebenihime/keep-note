import {getTestBed, TestBed} from '@angular/core/testing';

import {ApiService} from './api.service';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {Note} from '../notes/model/note';
import {Notebook} from '../notes/model/notebook';
import {FeedbackViewModel} from '../feedback/model/feedbackviewmodel';

describe('API service', () => {
  let httpTestingController: HttpTestingController;
  let service: ApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ApiService]
    });
    httpTestingController = getTestBed().get(HttpTestingController);
    service = getTestBed().get(ApiService);
  });

  it('should created an instance', () => {
    expect(service).toBeTruthy();
  });

  /*NOTEBOOKS*/

  it('should get all notebooks via API', () => {
    // setup()
    const notebooks: Notebook[] = [
      {id: '1', name: 'default', nbOfNotes: 0}
    ];
    // run()
    service.getAllNotebooks().subscribe(res => {
      expect(res.length).toBe(1);
      expect(res).toEqual(notebooks);
    });
    // mock()
    const req = httpTestingController.expectOne(`${service.BASE_URL_NOTE_API}\\notebooks`);
    expect(req.request.method).toBe('GET');
    req.flush(notebooks);
  });

  it('should save a notebook via API', () => {
    // setup()
    const notebook: Notebook = {id: '1', name: 'default', nbOfNotes: 0};
    // run()
    service.postNotebook(notebook).subscribe(res => {
      expect(res).toEqual(notebook);
    });
    // mock()
    const req = httpTestingController.expectOne(`${service.BASE_URL_NOTE_API}\\notebooks`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(notebook);
    req.flush(notebook);
  });

  it('should delete a notebook via API', () => {
    // setup()
    const notebook: Notebook[] = [
      {id: '1', name: 'default', nbOfNotes: 0}
    ];
    const notebookId = '1';
    // run()
    service.deleteNotebook(notebookId).subscribe(res => {
    });
    // mock()
    const req = httpTestingController.expectOne(`${service.BASE_URL_NOTE_API}\\notebooks\\` + notebookId);
    expect(req.request.method).toBe('DELETE');
    req.flush(notebook);
  });

  /*NOTES*/

  it('should get all notes via API', () => {
    // setup()
    const notes: Note[] = [
      {id: '1', title: 'title', text: 'text', notebookId: '10', lastModifiedOn: ''}
    ];
    // run()
    service.getAllNotes().subscribe(res => {
      expect(res.length).toBe(1);
      expect(res).toEqual(notes);
    });
    // mock()
    const req = httpTestingController.expectOne(`${service.BASE_URL_NOTE_API}\\notes`);
    expect(req.request.method).toBe('GET');
    req.flush(notes);
  });

  it('should get all notes from a notebookId via API', () => {
    // setup()
    const notes: Note[] = [
      {id: '1', title: 'title1', text: 'text1', notebookId: '10', lastModifiedOn: ''},
      {id: '2', title: 'title2', text: 'text2', notebookId: '10', lastModifiedOn: ''}
    ];
    const notebookId = '10';
    // run()
    service.getAllNotesByNotebook(notebookId).subscribe(res => {
      expect(res.length).toBe(2);
      expect(res[0].id).toEqual('1');
      expect(res[1].id).toEqual('2');
    });
    // mock()
    const req = httpTestingController.expectOne(`${service.BASE_URL_NOTE_API}\\notebooks\\` + notebookId + '/notes');
    expect(req.request.method).toBe('GET');
    req.flush(notes);
  });

  it('should save a note via API', () => {
    // setup()
    const note: Note = {id: '1', title: 'title', text: 'text', notebookId: '10', lastModifiedOn: ''};
    // run()
    service.postNote(note).subscribe(res => {
      expect(res).toEqual(note);
    });
    // mock()
    const req = httpTestingController.expectOne(`${service.BASE_URL_NOTE_API}\\notes`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(note);
    req.flush(note);
  });

  it('should delete a note via API', () => {
    // setup()
    const note: Note[] = [
      {id: '1', title: 'title', text: 'text', notebookId: '10', lastModifiedOn: ''}
    ];
    const noteId = '1';
    // run()
    service.deleteNote(noteId).subscribe(res => {
    });
    // mock()
    const req = httpTestingController.expectOne(`${service.BASE_URL_NOTE_API}\\notes\\` + noteId);
    expect(req.request.method).toBe('DELETE');
    req.flush(note);
  });

  /*FEEDBACK*/

  it('should send feedback via API', () => {
    // setup()
    const feedback: FeedbackViewModel = {name: 'xxx', email: 'xxx@xxx.fr', feedback: 'Amazing feedback !'};
    // run()
    service.postFeedback(feedback).subscribe(res => {
      expect(res).toEqual(feedback);
      expect(res.name).toEqual('xxx');
    });
    // mock()
    const req = httpTestingController.expectOne(`${service.BASE_URL_FEEDBACK_API}\\feedback`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(feedback);
    req.flush(feedback);
  });

});
