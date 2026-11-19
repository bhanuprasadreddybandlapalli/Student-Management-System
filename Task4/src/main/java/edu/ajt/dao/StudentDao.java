package edu.ajt.dao;
import java.util.List;
import org.hibernate.*;
import edu.ajt.db.HibernateUtil;
import edu.ajt.model.*;
public class StudentDao {
 public List<Student> list(String query){
  try(Session s=HibernateUtil.get().openSession()){
   String hql="select s from Student s join fetch s.course where lower(s.fullName) like :q or lower(s.enrollmentNo) like :q order by s.id desc";
   return s.createQuery(hql,Student.class).setParameter("q","%"+(query==null?"":query.trim().toLowerCase())+"%").getResultList();
  }
 }
 public List<Course> courses(){try(Session s=HibernateUtil.get().openSession()){return s.createQuery("from Course order by name",Course.class).list();}}
 public Student get(long id){try(Session s=HibernateUtil.get().openSession()){Student st=s.get(Student.class,id);if(st!=null) org.hibernate.Hibernate.initialize(st.getCourse());return st;}}
 public void save(Student st,long courseId){
  try(Session s=HibernateUtil.get().openSession()){Transaction tx=s.beginTransaction();
   try{Course c=s.get(Course.class,courseId);if(c==null)throw new IllegalArgumentException("Invalid course");st.setCourse(c);s.saveOrUpdate(st);tx.commit();}
   catch(RuntimeException e){if(tx.isActive())tx.rollback();throw e;}
  }
 }
 public void delete(long id){try(Session s=HibernateUtil.get().openSession()){Transaction tx=s.beginTransaction();try{Student st=s.get(Student.class,id);if(st!=null)s.delete(st);tx.commit();}catch(RuntimeException e){if(tx.isActive())tx.rollback();throw e;}}}
}