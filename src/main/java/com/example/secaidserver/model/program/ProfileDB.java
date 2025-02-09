package com.example.secaidserver.model.program;

import com.example.secaidserver.model.enums.ImplementationTierEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "profile")
public class ProfileDB implements Serializable {

    private static Long temporaryIdCounter = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private ImplementationTierEnum implementationTier;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NistCoreSubcategoryDB> nistCoreSubcategoryDBList = new ArrayList<>();

    public ProfileDB(Profile profile) {
        this.id = profile.getId() == null ? temporaryIdCounter++ : profile.getId();
        this.implementationTier = profile.getImplementationTier();
        this.nistCoreSubcategoryDBList = NistCoreSubcategoryDB.buildListOfNistCoreSubcategoryList(
                profile.getNistCoreSubcategoryList(),
                this
        );
    }
}
