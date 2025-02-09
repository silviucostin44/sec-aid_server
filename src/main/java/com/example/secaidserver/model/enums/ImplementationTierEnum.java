package com.example.secaidserver.model.enums;

public enum ImplementationTierEnum {
    PARTIAL(1),
    RISK_INFORMED(2),
    REPEATABLE(3),
    ADAPTIVE(4);

    private final int tier;

    ImplementationTierEnum(int tier) {
        this.tier = tier;
    }

    public int getTier() {
        return tier;
    }
}
