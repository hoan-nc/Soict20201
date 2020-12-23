package application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalExamForm {

    private Long id;

    private Long userId;

    private String fullName;

    private Long examinationId;

    private Long departmentExamId;

    private String createdDate;

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

    private Long year;

}
