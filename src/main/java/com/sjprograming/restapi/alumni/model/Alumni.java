package com.sjprograming.restapi.alumni.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alumni")
public class Alumni {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String combination;

    private String rollNo;

    @Column(nullable = false)
    private Integer yearOfPassout;

    @Column(nullable = false)
    private String designation;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCombination() {
		return combination;
	}

	public void setCombination(String combination) {
		this.combination = combination;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public Integer getYearOfPassout() {
		return yearOfPassout;
	}

	public void setYearOfPassout(Integer yearOfPassout) {
		this.yearOfPassout = yearOfPassout;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	@Column(nullable = false)
    private boolean approved = false;

    // getters & setters
}
