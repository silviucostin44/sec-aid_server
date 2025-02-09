package com.example.secaidserver.model.enums;

public enum UploadFileEnum {

    ASSETS_INVENTORY(1),
    THREAT_ANALYSIS(2),
    TARGET_PROFILE(3),
    RISK_ASSESSMENT(4),
    CURRENT_PROFILE(5),
    ACTIONS_PRIORITY(6),
    IMPLEMENTATION_DOC(7);

    private final int step;

    UploadFileEnum(int step) {
        this.step = step;
    }

    public int getStep() {
        return step;
    }
}
