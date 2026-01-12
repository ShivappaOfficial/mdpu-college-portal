//package com.sjprograming.restapi.admission.model;
//
//import jakarta.persistence.*;
//import org.hibernate.annotations.CreationTimestamp;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "admissions")
//public class Admission {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//    private String mobile;
//    private String email;
//    private String course;
//
//    @Column(columnDefinition = "TEXT")
//    private String address;
//
//    @CreationTimestamp
//    private LocalDateTime createdAt;
//
//    // 🔹 Default Constructor
//    public Admission() {
//    }
//
//    // 🔹 Getters & Setters
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getMobile() {
//        return mobile;
//    }
//
//    public void setMobile(String mobile) {
//        this.mobile = mobile;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getCourse() {
//        return course;
//    }
//
//    public void setCourse(String course) {
//        this.course = course;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//}


package com.sjprograming.restapi.admission.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "admissions")
public class Admission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String mobile;
    private String email;
    private String course;
    private String address;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Admission() {}

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getMobile() { return mobile; }
    public String getEmail() { return email; }
    public String getCourse() { return course; }
    public String getAddress() { return address; }

    public void setName(String name) { this.name = name; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public void setEmail(String email) { this.email = email; }
    public void setCourse(String course) { this.course = course; }
    public void setAddress(String address) { this.address = address; }
}
