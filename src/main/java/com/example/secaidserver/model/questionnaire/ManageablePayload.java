package com.example.secaidserver.model.questionnaire;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ManageablePayload<T> implements Serializable {
    private List<T> itemsToAdd;

    private List<T> itemsToEdit;

    private List<T> itemsToDelete;

    public ManageablePayload() {
        this.itemsToAdd = new ArrayList<>();
        this.itemsToEdit = new ArrayList<>();
        this.itemsToDelete = new ArrayList<>();
    }
}
