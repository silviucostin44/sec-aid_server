package com.example.secaidserver.model.questionnaire;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@IdClass(QuestSectionDbPK.class)
@Table(name = "quest_section")
public class QuestSectionDB {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID questId;

    @Id
    private String sectionNumber;

    private Float rating;

    @ManyToOne(fetch = FetchType.LAZY)
    private QuestionnaireDB questionnaire;

    public QuestSectionDB(UUID questId, QuestSection questSection) {
        this.questId = questId;
        this.sectionNumber = questSection.getNumber();
        this.rating = questSection.getRating();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestSectionDB)) return false;
        QuestSectionDB that = (QuestSectionDB) o;
        return questId.equals(that.questId) && sectionNumber.equals(that.sectionNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questId, sectionNumber);
    }
}
