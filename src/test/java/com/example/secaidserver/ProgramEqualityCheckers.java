package com.example.secaidserver;

import com.example.secaidserver.model.program.*;
import org.junit.jupiter.api.Assertions;

public class ProgramEqualityCheckers {

    public static void checkNewProgramsEquality(final Program program1, final Program program2) {
        Assertions.assertEquals(program1.getName(), program2.getName());

        Assertions.assertEquals(program1.getAssets().size(), 1);
        Assertions.assertEquals(program2.getAssets().size(), 1);
        checkNewAssetsEquality(program1.getAssets().get(0), program2.getAssets().get(0));

        checkNewProfilesEquality(program1.getTargetProfile(), program2.getTargetProfile());
        checkNewProfilesEquality(program1.getCurrentProfile(), program2.getCurrentProfile());
    }

    public static void checkNewProfilesEquality(final Profile profile1, final Profile profile2) {
        Assertions.assertSame(profile1.getImplementationTier(), profile2.getImplementationTier());

        Assertions.assertEquals(profile1.getNistCoreSubcategoryList().size(), 1);
        Assertions.assertEquals(profile2.getNistCoreSubcategoryList().size(), 1);

        checkNistCoreSubcategoriesEquality(profile1.getNistCoreSubcategoryList().get(0),
                                           profile2.getNistCoreSubcategoryList().get(0));
    }

    public static void checkNistCoreSubcategoriesEquality(final NistCoreSubcategory core1,
                                                          final NistCoreSubcategory core2) {
        Assertions.assertEquals(core1.getSubcategory(), core2.getSubcategory());
        Assertions.assertSame(core1.getImplementationLevel(), core2.getImplementationLevel());

        checkActionAnalysisEquality(core1.getActionAnalysis(), core2.getActionAnalysis());

    }

    public static void checkActionAnalysisEquality(final ActionAnalysis action1, final ActionAnalysis action2) {
        Assertions.assertEquals(action1.getSubcategory(), action2.getSubcategory());
        Assertions.assertEquals(action1.getAction(), action2.getAction());
        Assertions.assertEquals(action1.getCostBenefitIndex(), action2.getCostBenefitIndex());
        Assertions.assertEquals(action1.getPriority(), action2.getPriority());
    }

    public static void checkNewAssetsEquality(final Asset asset1, final Asset asset2) {
        Assertions.assertEquals(asset1.getType(), asset2.getType());
        Assertions.assertEquals(asset1.getName(), asset2.getName());
        Assertions.assertEquals(asset1.getDescription(), asset2.getDescription());
        Assertions.assertEquals(asset1.getInfoType(), asset2.getInfoType());
        Assertions.assertEquals(asset1.isClientAsset(), asset2.isClientAsset());
        Assertions.assertEquals(asset1.getVisibility(), asset2.getVisibility());
        Assertions.assertEquals(asset1.getOwner(), asset2.getOwner());

        checkNewRiskAssessmentsEquality(asset1.getRiskAssessment(), asset2.getRiskAssessment());
        Assertions.assertEquals(asset1.getRegulations().size(), 1);
        Assertions.assertEquals(asset2.getRegulations().size(), 1);
        checkNewAssetAttributesEquality(asset1.getVulnerabilities().get(0), asset2.getVulnerabilities().get(0));
        Assertions.assertEquals(asset1.getVulnerabilities().size(), 1);
        Assertions.assertEquals(asset2.getVulnerabilities().size(), 1);
        checkNewAssetAttributesEquality(asset1.getRegulations().get(0), asset2.getRegulations().get(0));
        Assertions.assertEquals(asset1.getThreats().size(), 1);
        Assertions.assertEquals(asset2.getThreats().size(), 1);
        checkNewAssetAttributesEquality(asset1.getThreats().get(0), asset2.getThreats().get(0));
    }

    public static void checkNewRiskAssessmentsEquality(final RiskAssessment assessment1, final RiskAssessment assessment2) {
        Assertions.assertSame(assessment1.getProbability(), assessment2.getProbability());
        Assertions.assertSame(assessment1.getImpact(), assessment2.getImpact());
    }

    public static void checkNewAssetAttributesEquality(final AssetAttribute attribute1, AssetAttribute attribute2) {
        Assertions.assertEquals(attribute1.getDescription(), attribute2.getDescription());
    }
}
