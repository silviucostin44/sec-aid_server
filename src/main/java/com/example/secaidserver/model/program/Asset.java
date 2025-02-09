package com.example.secaidserver.model.program;

import com.example.secaidserver.model.enums.AssetAttributeType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class Asset {

    private Long id;
    /**
     * Possible values: HARDWARE, SOFTWARE, INFORMATION, HUMAN RESOURCE, EXTERNAL SERVICE, ETC.
     */
    private String type;

    private String name;

    private String description;

    private String infoType;

    private boolean isClientAsset;

    private String visibility;

    private String owner;

    private RiskAssessment riskAssessment;

    private List<AssetAttribute> regulations;

    private List<AssetAttribute> vulnerabilities;

    private List<AssetAttribute> threats;

    public Asset(AssetDB assetDB) {
        this.id = assetDB.getId();
        this.type = assetDB.getType();
        this.name = assetDB.getName();
        this.description = assetDB.getDescription();
        this.infoType = assetDB.getInfoType();
        this.isClientAsset = assetDB.isClientAsset();
        this.visibility = assetDB.getVisibility();
        this.owner = assetDB.getOwner();
        this.riskAssessment = new RiskAssessment(assetDB.getRiskAssessment());
        this.regulations = AssetAttribute.parseListOfAttributesDB(filterAttributeByType(assetDB.getAttributes(),
                                                                                        AssetAttributeType.REGULATION));
        this.vulnerabilities = AssetAttribute.parseListOfAttributesDB(filterAttributeByType(assetDB.getAttributes(),
                                                                                            AssetAttributeType.VULNERABILITY));
        this.threats = AssetAttribute.parseListOfAttributesDB(filterAttributeByType(assetDB.getAttributes(),
                                                                                    AssetAttributeType.THREAT));
    }

    public static List<Asset> parseListOfAssetsDB(final List<AssetDB> assetsDB) {
        return assetsDB.stream()
                       .map(Asset::new)
                       .collect(Collectors.toList());
    }

    private List<AssetAttributeDB> filterAttributeByType(final List<AssetAttributeDB> assetAttributesDB,
                                                         final AssetAttributeType attributeType) {
        return assetAttributesDB.stream()
                                .filter(attribute -> attribute.getType() == attributeType)
                                .collect(Collectors.toList());
    }
}
