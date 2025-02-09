package com.example.secaidserver.model.program;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class Program implements Serializable {

    private Long id;

    private String name;

    private List<Asset> assets;

    private Profile targetProfile;

    private Profile currentProfile;

    private Program(Long id, String name, List<Asset> assets, Profile targetProfile, Profile currentProfile) {
        this.id = id;
        this.name = name;
        this.assets = assets;
        this.targetProfile = targetProfile;
        this.currentProfile = currentProfile;
    }

    public static Program displayInstance(final ProgramDB programDB) {
        return new Program(programDB.getId(),
                           programDB.getName(),
                           null,
                           null,
                           null);
    }

    public static Program completeInstance(final ProgramDB programDB) {
        return new Program(programDB.getId(),
                           programDB.getName(),
                           Asset.parseListOfAssetsDB(programDB.getAssets()),
                           new Profile(programDB.getTargetProfile()),
                           new Profile(programDB.getCurrentProfile()));
    }

}
