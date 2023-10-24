package org.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString
@Document
public class Notebook {
    @Id
    private UUID id;
    private String name;
    @JsonIgnore
    private List<Note> notes;

    protected Notebook() {
        this.id = UUID.randomUUID();
        this.notes = new ArrayList<>();
    }

    public Notebook(String name) {
        this();
        this.name = name;
    }

    public Notebook(String id, String name) {
        this();
        if (id != null) {
            this.id = UUID.fromString(id);
        }
        this.name = name;
    }

}
