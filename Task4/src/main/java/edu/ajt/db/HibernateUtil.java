package edu.ajt.db;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import edu.ajt.model.*;
public final class HibernateUtil {
 private static final SessionFactory FACTORY = build();
 private static SessionFactory build(){
  String url=System.getenv().getOrDefault("SMS_DB_URL","jdbc:mysql://localhost:3306/sms_db?useSSL=false&serverTimezone=UTC");
  String user=System.getenv().getOrDefault("SMS_DB_USER","root");
  String pass=System.getenv("SMS_DB_PASSWORD");
  if(pass==null) throw new IllegalStateException("Set SMS_DB_PASSWORD environment variable");
  SessionFactory factory = new Configuration().setProperty("hibernate.connection.driver_class","com.mysql.cj.jdbc.Driver")
   .setProperty("hibernate.connection.url",url).setProperty("hibernate.connection.username",user)
   .setProperty("hibernate.connection.password",pass).setProperty("hibernate.dialect","org.hibernate.dialect.MySQL8Dialect")
   .setProperty("hibernate.hbm2ddl.auto","update").setProperty("hibernate.show_sql","false")
   .addAnnotatedClass(Student.class).addAnnotatedClass(Course.class).addAnnotatedClass(Admin.class).buildSessionFactory();
  seedCourses(factory);
  return factory;
 }
 private static void seedCourses(SessionFactory factory){
  try(org.hibernate.Session session=factory.openSession()){
   org.hibernate.Transaction tx=session.beginTransaction();
   try{
    String[][] defaults={{"CSE","Computer Science and Engineering"},{"IT","Information Technology"},{"ECE","Electronics and Communication"}};
    for(String[] item:defaults){
     Long count=session.createQuery("select count(c) from Course c where c.code=:code",Long.class).setParameter("code",item[0]).uniqueResult();
     if(count==0){Course course=new Course();course.setCode(item[0]);course.setName(item[1]);session.save(course);}
    }
    tx.commit();
   }catch(RuntimeException e){if(tx.isActive())tx.rollback();throw e;}
  }
 }
 public static SessionFactory get(){return FACTORY;}
}