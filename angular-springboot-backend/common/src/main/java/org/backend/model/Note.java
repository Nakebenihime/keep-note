package org.backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document
public class Note {

    @Id
    private UUID id;
    private String title;
    private String text;
    private Notebook notebook;
    private Date lastModifiedOn;

    protected Note() {
        this.id = UUID.randomUUID();
        this.lastModifiedOn = new Date();
    }

    public Note(String title, String text, Notebook notebook) {
        this();
        this.title = title;
        this.text = text;
        this.notebook = notebook;
    }

    public Note(String id, String title, String text, Notebook notebook) {
        this(title, text, notebook);
        if (id != null) {
            this.id = UUID.fromString(id);
        }
    }
}
