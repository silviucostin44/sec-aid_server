package com.example.secaidserver.model.questionnaire;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class QuestSectionDbPK implements Serializable {
    private UUID questId;

    private String sectionNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestSectionDbPK)) return false;
        QuestSectionDbPK that = (QuestSectionDbPK) o;
        return questId.equals(that.questId) && sectionNumber.equals(that.sectionNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questId, sectionNumber);
    }
}
