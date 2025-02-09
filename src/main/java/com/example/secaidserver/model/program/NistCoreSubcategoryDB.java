package com.example.secaidserver.model.program;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "nist_core_subcategory")
public class NistCoreSubcategoryDB {

    @Id
    @GeneratedValue
    private Long id;

    private String subcategory;
    /**
     * Values from 0 to 3 (NONE, LOW, MEDIUM, HIGH)
     */
    private int implementationLevel;

    @OneToOne(mappedBy = "nistCoreSubcategoryDB", cascade = CascadeType.ALL)
    private ActionAnalysisDB actionAnalysisDB;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProfileDB profile;

    public NistCoreSubcategoryDB(NistCoreSubcategory nistCoreSubcategory, ProfileDB profileDB) {
        this.id = nistCoreSubcategory.getId();
        this.subcategory = nistCoreSubcategory.getSubcategory();
        this.implementationLevel = nistCoreSubcategory.getImplementationLevel();
        this.actionAnalysisDB = nistCoreSubcategory.getActionAnalysis() == null ? null
                : new ActionAnalysisDB(nistCoreSubcategory.getActionAnalysis(), this);
        this.profile = profileDB;
    }

    public static List<NistCoreSubcategoryDB> buildListOfNistCoreSubcategoryList(
            final List<NistCoreSubcategory> nistCoreSubcategoryList,
            final ProfileDB profileDB) {
        return nistCoreSubcategoryList.stream()
                                      .map(core -> new NistCoreSubcategoryDB(core, profileDB))
                                      .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NistCoreSubcategoryDB)) return false;
        NistCoreSubcategoryDB that = (NistCoreSubcategoryDB) o;
        return getId().equals(that.getId()) && getSubcategory().equals(that.getSubcategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSubcategory());
    }
}
