package com.example.secaidserver;

import com.example.secaidserver.model.enums.ImplementationTierEnum;
import com.example.secaidserver.model.program.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DummiesFactory {

    private static Long programId = 1L;
    private static Long profileId = 1L;
    private static Long coreId = 1L;
    private static Long actionId = 1L;
    private static Long assetsId = 1L;
    private static Long attributeId = 1L;

    public static Program createDummyProgram(final int assetNo, final boolean pure) {
        Program program = new Program();
        program.setId(1000L + programId);
        program.setName("Dummy Program " + programId++);

        if (!pure) {
            List<Asset> assetList = new ArrayList<>();
            for (int i = 0; i < assetNo; ++i) {
                assetList.add(createDummyAsset());
            }
            program.setAssets(assetList);
            program.setTargetProfile(createDummyProfile());
            program.setCurrentProfile(createDummyProfile());
        }

        return program;
    }

    public static Program createDummyProgram(final int assetNo) {
        return createDummyProgram(assetNo, false);
    }


    public static Profile createDummyProfile() {
        Profile profile = new Profile();
        profile.setId(3000L + profileId++);
        profile.setImplementationTier(ImplementationTierEnum.ADAPTIVE);
        List<NistCoreSubcategory> nistCoreSubcategoryList = new ArrayList<>(Collections.singletonList(
                createDummyNistCoreSubcategory()
        ));
        profile.setNistCoreSubcategoryList(nistCoreSubcategoryList);

        return profile;
    }

    public static NistCoreSubcategory createDummyNistCoreSubcategory() {
        NistCoreSubcategory nistCoreSubcategory = new NistCoreSubcategory();
        String subcategory = "A." + coreId++;
        nistCoreSubcategory.setSubcategory(subcategory);
        nistCoreSubcategory.setImplementationLevel(3);
        nistCoreSubcategory.setActionAnalysis(createDummyActionAnalysis(subcategory));

        return nistCoreSubcategory;
    }

    public static ActionAnalysis createDummyActionAnalysis(final String subcategory) {
        ActionAnalysis actionAnalysis = new ActionAnalysis();
        actionAnalysis.setSubcategory(subcategory);
        actionAnalysis.setAction("action " + actionId++);
        actionAnalysis.setCostBenefitIndex(1);
        actionAnalysis.setPriority(1);

        return actionAnalysis;
    }

    public static Asset createDummyAsset() {
        Asset asset = new Asset();
        Long id = 20L + assetsId;
        asset.setId(id);
        asset.setType("SOFTWARE");
        asset.setName("Dummy Asset " + assetsId++);
        asset.setDescription("desc");
        asset.setInfoType("type");
        asset.setClientAsset(true);
        asset.setVisibility("PUBLIC");
        asset.setOwner("owner");
        asset.setRiskAssessment(createDummyRiskAssessment(id));
        asset.setRegulations(Collections.singletonList(createDummyAttribute()));
        asset.setVulnerabilities(Collections.singletonList(createDummyAttribute()));
        asset.setThreats(Collections.singletonList(createDummyAttribute()));
        return asset;
    }

    public static AssetAttribute createDummyAttribute() {
        AssetAttribute attribute = new AssetAttribute();
        attribute.setId(40L + attributeId);
        attribute.setDescription("desc " + attributeId++);

        return attribute;
    }

    public static RiskAssessment createDummyRiskAssessment(final Long assetId) {
        RiskAssessment riskAssessment = new RiskAssessment();
        riskAssessment.setId(assetId);
        riskAssessment.setProbability(3);
        riskAssessment.setImpact(3);

        return riskAssessment;
    }
}
