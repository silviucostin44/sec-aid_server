package com.example.secaidserver.model.program;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class AssetAttribute {

    private Long id;

    private String description;

    private Long assetId;

    public AssetAttribute(AssetAttributeDB assetAttributeDB) {
        this.id = assetAttributeDB.getId();
        this.description = assetAttributeDB.getDescription();
        this.assetId = assetAttributeDB.getId();
    }

    public static List<AssetAttribute> parseListOfAttributesDB(final List<AssetAttributeDB> assetAttributesDB) {
        return assetAttributesDB.stream()
                                .map(AssetAttribute::new)
                                .collect(Collectors.toList());
    }
}
