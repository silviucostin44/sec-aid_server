package com.example.secaidserver.model.questionnaire;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "questionnaire")
public class QuestionnaireDB {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "questionnaire", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sectionNumber")
    private List<QuestSectionDB> questsRatings = new LinkedList<>();

    @OneToMany(mappedBy = "questionnaire", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("globalIndex")
    private List<ResponseDB> responses = new LinkedList<>();

    public QuestionnaireDB(UUID id, String name) {
        // generate id if doesn't receive one
        if (id == null) {
            this.id = UUID.randomUUID();
        } else {
            this.id = id;
        }
        this.name = name;
    }

    public void addQuestsRating(QuestSectionDB questRating) {
        questsRatings.add(questRating);
        questRating.setQuestionnaire(this);
    }

    public void removeQuestsRating(QuestSectionDB questRating) {
        questsRatings.remove(questRating);
        questRating.setQuestionnaire(null);
    }

    public void addResponse(ResponseDB response) {
        responses.add(response);
        response.setQuestionnaire(this);
    }

    public void removeResponse(ResponseDB response) {
        responses.remove(response);
        response.setQuestionnaire(null);
    }
}
