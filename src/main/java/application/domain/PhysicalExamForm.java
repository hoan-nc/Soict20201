package application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalExamForm {

    private Long userId;

    private Long examinationId;

    private Long departmentExamId;

    private Long height;

    private Long weight;

    private String bloodPressure;

    private String eyes;

    private String insideMedical;

    private String outsideMedical;

    private String earNoseThroat;

    private String dentomaxillofacial;

    private String dermatology;

    private String nerve;

    private String bloodAnalysis;

    private Double whiteBloodNumber;

    private Double redBloodNumber;

    private Double hemoglobin;

    private Long plateletNumber;

    private Long bloodUrea;

    private Long bloodCreatinine;

    private String hepatitisB;

    private Long healthType;

    private String advisory;

}
