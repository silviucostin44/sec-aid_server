package com.example.secaidserver.model.program;

import com.example.secaidserver.model.enums.AssetAttributeType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Asset attributes: regulations, vulnerabilities and threats.
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "asset_attribute")
public class AssetAttributeDB {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String description;

    private AssetAttributeType type;

    @ManyToOne(fetch = FetchType.LAZY)
    private AssetDB asset;

    public AssetAttributeDB(AssetAttribute assetAttribute, AssetAttributeType type, AssetDB assetDB) {
        this.id = assetAttribute.getId();
        this.description = assetAttribute.getDescription();
        this.type = type;
        this.asset = assetDB;
    }

    /**
     * Builds a list of asset attributes db objects.
     *
     * @param assetAttributes the list of asset attributes to build from.
     * @param type            the type of attributes.
     * @return the list of built attributes.
     */
    public static List<AssetAttributeDB> buildListOfAssetAttributesDB(final List<AssetAttribute> assetAttributes,
                                                                      final AssetAttributeType type,
                                                                      final AssetDB assetDB) {
        return assetAttributes.stream()
                              .map(attribute -> new AssetAttributeDB(attribute, type, assetDB))
                              .collect(Collectors.toList());
    }

}
