package com.example.secaidserver.model.program;

import com.example.secaidserver.model.enums.ImplementationTierEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Profile {

    private Long id;

    private ImplementationTierEnum implementationTier;

    private List<NistCoreSubcategory> nistCoreSubcategoryList;

    public Profile(ProfileDB profileDB) {
        this.id = profileDB.getId();
        this.implementationTier = profileDB.getImplementationTier();
        this.nistCoreSubcategoryList = NistCoreSubcategory.parseListOfNistCoreSubcategoryList(profileDB.getNistCoreSubcategoryDBList());
    }

}
