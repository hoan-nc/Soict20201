package application.data.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Person {
    private int personId;
    private String personName;
    private String isActive;
    private String createdBy;
    private String createdDate;
    private String updatedBy;
    private String updatedDate;
    private String versionNo;
    private String height;
    private String weight;
    private String bloodPressure;
    private String eyes;
    private String insideMedical;
    private String outsideMedical;
    private String earNoseThroat;
    private String dentomaxillofacial;
    private String dermatology;
    private String nerve;
    private String bloodAnalysis;
    private String whiteBloodNumber;
    private String redBloodNumber;
    private String hemoglobin;
    private String plateletNumber;
    private String bloodUrea;
    private String bloodCreatinine;
    private String hepatitisB;
    private String healthType;
    private String advisory;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    @Basic
    @Column(name = "person_name")
    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    @Basic
    @Column(name = "is_active")
    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @Basic
    @Column(name = "created_by")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "created_date")
    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "updated_by")
    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Basic
    @Column(name = "updated_date")
    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Basic
    @Column(name = "version_no")
    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    @Basic
    @Column(name = "height")
    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Basic
    @Column(name = "weight")
    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "blood_pressure")
    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    @Basic
    @Column(name = "eyes")
    public String getEyes() {
        return eyes;
    }

    public void setEyes(String eyes) {
        this.eyes = eyes;
    }

    @Basic
    @Column(name = "inside_medical")
    public String getInsideMedical() {
        return insideMedical;
    }

    public void setInsideMedical(String insideMedical) {
        this.insideMedical = insideMedical;
    }

    @Basic
    @Column(name = "outside_medical")
    public String getOutsideMedical() {
        return outsideMedical;
    }

    public void setOutsideMedical(String outsideMedical) {
        this.outsideMedical = outsideMedical;
    }

    @Basic
    @Column(name = "ear_nose_throat")
    public String getEarNoseThroat() {
        return earNoseThroat;
    }

    public void setEarNoseThroat(String earNoseThroat) {
        this.earNoseThroat = earNoseThroat;
    }

    @Basic
    @Column(name = "dentomaxillofacial")
    public String getDentomaxillofacial() {
        return dentomaxillofacial;
    }

    public void setDentomaxillofacial(String dentomaxillofacial) {
        this.dentomaxillofacial = dentomaxillofacial;
    }

    @Basic
    @Column(name = "dermatology")
    public String getDermatology() {
        return dermatology;
    }

    public void setDermatology(String dermatology) {
        this.dermatology = dermatology;
    }

    @Basic
    @Column(name = "nerve")
    public String getNerve() {
        return nerve;
    }

    public void setNerve(String nerve) {
        this.nerve = nerve;
    }

    @Basic
    @Column(name = "blood_analysis")
    public String getBloodAnalysis() {
        return bloodAnalysis;
    }

    public void setBloodAnalysis(String bloodAnalysis) {
        this.bloodAnalysis = bloodAnalysis;
    }

    @Basic
    @Column(name = "white_blood_number")
    public String getWhiteBloodNumber() {
        return whiteBloodNumber;
    }

    public void setWhiteBloodNumber(String whiteBloodNumber) {
        this.whiteBloodNumber = whiteBloodNumber;
    }

    @Basic
    @Column(name = "red_blood_number")
    public String getRedBloodNumber() {
        return redBloodNumber;
    }

    public void setRedBloodNumber(String redBloodNumber) {
        this.redBloodNumber = redBloodNumber;
    }

    @Basic
    @Column(name = "hemoglobin")
    public String getHemoglobin() {
        return hemoglobin;
    }

    public void setHemoglobin(String hemoglobin) {
        this.hemoglobin = hemoglobin;
    }

    @Basic
    @Column(name = "platelet_number")
    public String getPlateletNumber() {
        return plateletNumber;
    }

    public void setPlateletNumber(String plateletNumber) {
        this.plateletNumber = plateletNumber;
    }

    @Basic
    @Column(name = "blood_urea")
    public String getBloodUrea() {
        return bloodUrea;
    }

    public void setBloodUrea(String bloodUrea) {
        this.bloodUrea = bloodUrea;
    }

    @Basic
    @Column(name = "blood_creatinine")
    public String getBloodCreatinine() {
        return bloodCreatinine;
    }

    public void setBloodCreatinine(String bloodCreatinine) {
        this.bloodCreatinine = bloodCreatinine;
    }

    @Basic
    @Column(name = "hepatitis_b")
    public String getHepatitisB() {
        return hepatitisB;
    }

    public void setHepatitisB(String hepatitisB) {
        this.hepatitisB = hepatitisB;
    }

    @Basic
    @Column(name = "health_type")
    public String getHealthType() {
        return healthType;
    }

    public void setHealthType(String healthType) {
        this.healthType = healthType;
    }

    @Basic
    @Column(name = "advisory")
    public String getAdvisory() {
        return advisory;
    }

    public void setAdvisory(String advisory) {
        this.advisory = advisory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return personId == person.personId &&
                Objects.equals(personName, person.personName) &&
                Objects.equals(isActive, person.isActive) &&
                Objects.equals(createdBy, person.createdBy) &&
                Objects.equals(createdDate, person.createdDate) &&
                Objects.equals(updatedBy, person.updatedBy) &&
                Objects.equals(updatedDate, person.updatedDate) &&
                Objects.equals(versionNo, person.versionNo) &&
                Objects.equals(height, person.height) &&
                Objects.equals(weight, person.weight) &&
                Objects.equals(bloodPressure, person.bloodPressure) &&
                Objects.equals(eyes, person.eyes) &&
                Objects.equals(insideMedical, person.insideMedical) &&
                Objects.equals(outsideMedical, person.outsideMedical) &&
                Objects.equals(earNoseThroat, person.earNoseThroat) &&
                Objects.equals(dentomaxillofacial, person.dentomaxillofacial) &&
                Objects.equals(dermatology, person.dermatology) &&
                Objects.equals(nerve, person.nerve) &&
                Objects.equals(bloodAnalysis, person.bloodAnalysis) &&
                Objects.equals(whiteBloodNumber, person.whiteBloodNumber) &&
                Objects.equals(redBloodNumber, person.redBloodNumber) &&
                Objects.equals(hemoglobin, person.hemoglobin) &&
                Objects.equals(plateletNumber, person.plateletNumber) &&
                Objects.equals(bloodUrea, person.bloodUrea) &&
                Objects.equals(bloodCreatinine, person.bloodCreatinine) &&
                Objects.equals(hepatitisB, person.hepatitisB) &&
                Objects.equals(healthType, person.healthType) &&
                Objects.equals(advisory, person.advisory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, personName, isActive, createdBy, createdDate, updatedBy, updatedDate, versionNo, height, weight, bloodPressure, eyes, insideMedical, outsideMedical, earNoseThroat, dentomaxillofacial, dermatology, nerve, bloodAnalysis, whiteBloodNumber, redBloodNumber, hemoglobin, plateletNumber, bloodUrea, bloodCreatinine, hepatitisB, healthType, advisory);
    }
}
