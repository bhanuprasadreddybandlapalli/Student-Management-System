package edu.ajt.model;
import javax.persistence.*;
@Entity @Table(name="courses")
public class Course {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true) private String code;
 @Column(nullable=false) private String name;
 public Long getId(){return id;} public String getCode(){return code;} public String getName(){return name;}
 public void setId(Long x){id=x;} public void setCode(String x){code=x;} public void setName(String x){name=x;}
}