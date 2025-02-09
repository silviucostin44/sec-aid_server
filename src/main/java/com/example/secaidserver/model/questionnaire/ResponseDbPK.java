package com.example.secaidserver.model.questionnaire;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class ResponseDbPK implements Serializable {
    private UUID questId;

    private int globalIndex;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponseDbPK)) return false;
        ResponseDbPK that = (ResponseDbPK) o;
        return globalIndex == that.globalIndex && Objects.equals(questId, that.questId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questId, globalIndex);
    }
}
