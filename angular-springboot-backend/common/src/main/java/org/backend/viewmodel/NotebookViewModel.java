package org.backend.viewmodel;

import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotebookViewModel {
    private String id;

    @NotNull
    private String name;

    private int nbNotes;

}
