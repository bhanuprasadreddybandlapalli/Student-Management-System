package edu.ajt.model;
import javax.persistence.*;
@Entity @Table(name="admins")
public class Admin {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true) private String username;
 @Column(name="password_hash",nullable=false) private String passwordHash;
 public Long getId(){return id;} public String getUsername(){return username;} public String getPasswordHash(){return passwordHash;}
 public void setId(Long x){id=x;} public void setUsername(String x){username=x;} public void setPasswordHash(String x){passwordHash=x;}
}