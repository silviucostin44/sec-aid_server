package com.example.secaidserver.model.program;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "program")
public class ProgramDB {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssetDB> assets = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "target_profile_id", referencedColumnName = "id")
    private ProfileDB targetProfile;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "current_profile_id", referencedColumnName = "id")
    private ProfileDB currentProfile;

    public ProgramDB(Program program) {
        this.id = program.getId();
        this.name = program.getName();
        this.assets = program.getAssets() == null
                ? null
                : AssetDB.buildListOfAssetsDB(program.getAssets(), this);
        this.targetProfile = program.getTargetProfile() == null
                ? null
                : new ProfileDB(program.getTargetProfile());
        this.currentProfile = program.getCurrentProfile() == null
                ? null
                : new ProfileDB(program.getCurrentProfile());
    }
}
