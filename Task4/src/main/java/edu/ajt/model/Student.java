package edu.ajt.model;
import javax.persistence.*;
@Entity @Table(name="students")
public class Student {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(name="enrollment_no",nullable=false,unique=true) private String enrollmentNo;
 @Column(name="full_name",nullable=false) private String fullName;
 @Column(nullable=false,unique=true) private String email;
 @ManyToOne(optional=false) @JoinColumn(name="course_id") private Course course;
 public Long getId(){return id;} public String getEnrollmentNo(){return enrollmentNo;} public String getFullName(){return fullName;} public String getEmail(){return email;} public Course getCourse(){return course;}
 public void setId(Long x){id=x;} public void setEnrollmentNo(String x){enrollmentNo=x;} public void setFullName(String x){fullName=x;} public void setEmail(String x){email=x;} public void setCourse(Course x){course=x;}
}