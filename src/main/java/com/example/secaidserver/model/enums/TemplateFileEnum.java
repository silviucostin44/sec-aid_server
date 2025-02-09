package com.example.secaidserver.model.enums;

public enum TemplateFileEnum {

    ASSETS_INVENTORY("1-Inventarul activelor - template.docx", 1),
    THREAT_ANALYSIS("2-Analiza amenințărilor - template.docx", 2),
    NIST_FRAMEWORK_CORE("3-1-Nucleu framework NIST.pdf", 3),
    IMPLEMENTATION_TIERS("3-2-Niveluri de implementare.pdf", 3),
    PROFILE("3-3-Profil - template.docx", 3),
    RISK_ASSESSMENT("4-Evaluarea riscului - template.docx", 4),
    ACTIONS_PRIORITY("6-1-Prioritatea actiunilor - template.docx", 6),
    IMPACT_RATES_PRIORITY_CODES("6-2-Rate impact și coduri prioritate.pdf", 6);

    private final String fileName;

    private final int step;

    TemplateFileEnum(String fileName, int step) {
        this.fileName = fileName;
        this.step = step;
    }

    public String getFileName() {
        return fileName;
    }

    public int getStep() {
        return step;
    }

    public static TemplateFileEnum valueOfFileName(String fileName) {
        for (TemplateFileEnum templateType : TemplateFileEnum.values()) {
            if (templateType.getFileName().equals(fileName)) {
                return templateType;
            }
        }
        throw new IllegalArgumentException("There is no template with the name: " + fileName);
    }
}
