package com.example.secaidserver.model.questionnaire;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Questionnaire implements Serializable {
    private UUID id;

    private List<QuestSection> sections;

    private String name;

}
