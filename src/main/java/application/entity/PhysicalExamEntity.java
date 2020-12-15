package application.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "physical_exam_profile")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalExamEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Column(name = "name_user", nullable = false)
    private String nameUser;

    @ManyToOne
    @JoinColumn(name = "examination_id", referencedColumnName = "id")
    private ExaminationEntity examination;

    @ManyToOne
    @JoinColumn(name = "department_exam_id", referencedColumnName = "id")
    private DepartmentExamEntity departmentExam;

    @Basic
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Basic
    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Basic
    @Column(name = "year", nullable = false)
    private Long year;

    @Basic
    @Column(name = "version_no")
    private String versionNo;

    @Basic
    @Column(name = "height")
    private Long height;

    @Basic
    @Column(name = "weight")
    private Long weight;

    @Basic
    @Column(name = "blood_pressure")
    private String bloodPressure;

    @Basic
    @Column(name = "eyes")
    private String eyes;

    @Basic
    @Column(name = "inside_medical")
    private String insideMedical;

    @Basic
    @Column(name = "outside_medical")
    private String outsideMedical;

    @Basic
    @Column(name = "ear_nose_throat")
    private String earNoseThroat;

    @Basic
    @Column(name = "dentomaxillofacial")
    private String dentomaxillofacial;

    @Basic
    @Column(name = "dermatology")
    private String dermatology;

    @Basic
    @Column(name = "nerve")
    private String nerve;

    @Basic
    @Column(name = "blood_analysis")
    private String bloodAnalysis;

    @Basic
    @Column(name = "white_blood_number")
    private Double whiteBloodNumber;

    @Basic
    @Column(name = "red_blood_number")
    private Double redBloodNumber;

    @Basic
    @Column(name = "hemoglobin")
    private Double hemoglobin;

    @Basic
    @Column(name = "platelet_number")
    private Long plateletNumber;

    @Basic
    @Column(name = "blood_urea")
    private Long bloodUrea;

    @Basic
    @Column(name = "blood_creatinine")
    private Long bloodCreatinine;

    @Basic
    @Column(name = "hepatitis_b")
    private String hepatitisB;

    @Basic
    @Column(name = "health_type")
    private Long healthType;

    @Basic
    @Column(name = "advisory")
    private String advisory;
}
