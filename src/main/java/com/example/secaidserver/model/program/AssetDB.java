package com.example.secaidserver.model.program;

import com.example.secaidserver.model.enums.AssetAttributeType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "asset")
public class AssetDB {

    @Id
    @GeneratedValue
    private Long id;

    private String type;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * Type of informational asset
     */
    private String infoType;

    @Column(name = "client_asset")
    private boolean isClientAsset;

    private String visibility;

    private String owner;

    @OneToOne(mappedBy = "asset", cascade = CascadeType.ALL)
    private RiskAssessmentDB riskAssessment;

    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssetAttributeDB> attributes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private ProgramDB program;

    public AssetDB(Asset asset, ProgramDB programDB) {
        this.id = asset.getId();
        this.type = asset.getType();
        this.name = asset.getName();
        this.description = asset.getDescription();
        this.infoType = asset.getInfoType();
        this.isClientAsset = asset.isClientAsset();
        this.visibility = asset.getVisibility();
        this.owner = asset.getOwner();
        this.riskAssessment = new RiskAssessmentDB(asset.getRiskAssessment(), this);
        this.attributes.addAll(AssetAttributeDB.buildListOfAssetAttributesDB(asset.getRegulations(), AssetAttributeType.REGULATION, this));
        this.attributes.addAll(AssetAttributeDB.buildListOfAssetAttributesDB(asset.getVulnerabilities(), AssetAttributeType.VULNERABILITY, this));
        this.attributes.addAll(AssetAttributeDB.buildListOfAssetAttributesDB(asset.getThreats(), AssetAttributeType.THREAT, this));
        this.program = programDB;
    }

    public static List<AssetDB> buildListOfAssetsDB(final List<Asset> assets, final ProgramDB programDB) {
        return assets.stream()
                     .map(asset -> new AssetDB(asset, programDB))
                     .collect(Collectors.toList());
    }
}
