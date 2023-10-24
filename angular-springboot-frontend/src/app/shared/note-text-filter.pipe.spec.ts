import {NoteTextFilterPipe} from './note-text-filter.pipe';
import {Note} from '../notes/model/note';

describe('NoteTextFilterPipe', () => {
  it('should create an instance', () => {
    const pipe = new NoteTextFilterPipe();
    expect(pipe).toBeTruthy();
  });

  it('should not filter notes if search filter is empty', () => {
    // setup()
    const pipe = new NoteTextFilterPipe();
    const emptySearchFilter = '';
    const notes: Note[] = [
      {id: '1', title: 'title1', text: 'text1', notebookId: '10', lastModifiedOn: ''},
      {id: '2', title: 'title2', text: 'text2', notebookId: '10', lastModifiedOn: ''}
    ];
    // run()
    const emptyFilter = pipe.transform(notes, emptySearchFilter);

    // assert()
    expect(emptyFilter.length).toBe(2);
  });
  it('should filter notes based on search filter ', () => {
    // setup()
    const pipe = new NoteTextFilterPipe();
    const SearchFilter = 'title1';
    const notes: Note[] = [
      {id: '1', title: 'title1', text: 'text1', notebookId: '10', lastModifiedOn: ''},
      {id: '2', title: 'title2', text: 'text2', notebookId: '10', lastModifiedOn: ''}
    ];
    // run()
    const filter = pipe.transform(notes, SearchFilter);

    // assert()
    expect(filter.length).toBe(1);
    expect(filter[0].id).toBe('1');

  });
});
