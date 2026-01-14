package com.sjprograming.restapi.admission.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(
    name = "admission_application",
    uniqueConstraints = @UniqueConstraint(columnNames = {"mobile", "email"})
)
public class Admission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("fatherName")
    private String fatherName;

    @JsonProperty("motherName")
    private String motherName;

    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("email")
    private String email;


    @Column(columnDefinition = "TEXT", nullable = true)
    private String permanentAddress;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String residentialAddress;


   
    private String familyMobile;
 

    private String satsId;
    private String sslcRegNo;

    private Integer totalMarks;
    private Integer obtainedMarks;
    private Double percentage;

    private String combination;

    private Integer kannada;
    private Integer english;
    private Integer hindi;
    private Integer socialScience;
    private Integer science;
    private Integer mathematics;

    @Column(columnDefinition = "JSON")
    private String others;

    private String admissionId;
    private String status = "APPLICATION RECEIVED";

    private LocalDate dob;
    private LocalDateTime createdAt;

    // ---------- GETTERS & SETTERS ----------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFatherName() { return fatherName; }
    public void setFatherName(String fatherName) { this.fatherName = fatherName; }

    public String getMotherName() { return motherName; }
    public void setMotherName(String motherName) { this.motherName = motherName; }

    public String getPermanentAddress() { return permanentAddress; }
    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getResidentialAddress() { return residentialAddress; }
    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getFamilyMobile() { return familyMobile; }
    public void setFamilyMobile(String familyMobile) {
        this.familyMobile = familyMobile;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSatsId() { return satsId; }
    public void setSatsId(String satsId) { this.satsId = satsId; }

    public String getSslcRegNo() { return sslcRegNo; }
    public void setSslcRegNo(String sslcRegNo) {
        this.sslcRegNo = sslcRegNo;
    }

    public Integer getTotalMarks() { return totalMarks; }
    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }

    public Integer getObtainedMarks() { return obtainedMarks; }
    public void setObtainedMarks(Integer obtainedMarks) {
        this.obtainedMarks = obtainedMarks;
    }

    public Double getPercentage() { return percentage; }
    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public String getCombination() { return combination; }
    public void setCombination(String combination) {
        this.combination = combination;
    }

    public Integer getKannada() { return kannada; }
    public void setKannada(Integer kannada) { this.kannada = kannada; }

    public Integer getEnglish() { return english; }
    public void setEnglish(Integer english) {
        this.english = english;
    }

    public Integer getHindi() { return hindi; }
    public void setHindi(Integer hindi) { this.hindi = hindi; }

    public Integer getSocialScience() { return socialScience; }
    public void setSocialScience(Integer socialScience) {
        this.socialScience = socialScience;
    }

    public Integer getScience() { return science; }
    public void setScience(Integer science) {
        this.science = science;
    }

    public Integer getMathematics() { return mathematics; }
    public void setMathematics(Integer mathematics) {
        this.mathematics = mathematics;
    }

    public String getOthers() { return others; }
    public void setOthers(String others) { this.others = others; }

    public String getAdmissionId() { return admissionId; }
    public void setAdmissionId(String admissionId) {
        this.admissionId = admissionId;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
