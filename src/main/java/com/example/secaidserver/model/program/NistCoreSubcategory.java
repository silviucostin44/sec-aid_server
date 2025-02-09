package com.example.secaidserver.model.program;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
public class NistCoreSubcategory implements Serializable {

    private Long id;

    private String subcategory;
    /**
     * Values from 0 to 3 (NONE, LOW, MEDIUM, HIGH)
     */
    private int implementationLevel;

    private ActionAnalysis actionAnalysis;

    public NistCoreSubcategory(NistCoreSubcategoryDB nistCoreSubcategoryDB) {
        this.id = nistCoreSubcategoryDB.getId();
        this.subcategory = nistCoreSubcategoryDB.getSubcategory();
        this.implementationLevel = nistCoreSubcategoryDB.getImplementationLevel();
        this.actionAnalysis = nistCoreSubcategoryDB.getActionAnalysisDB() == null ? null
                : new ActionAnalysis(nistCoreSubcategoryDB.getActionAnalysisDB());
    }


    public static List<NistCoreSubcategory> parseListOfNistCoreSubcategoryList(List<NistCoreSubcategoryDB> nistCoreSubcategoryDBList) {
        return nistCoreSubcategoryDBList.stream()
                                        .map(NistCoreSubcategory::new)
                                        .collect(Collectors.toList());
    }
}
