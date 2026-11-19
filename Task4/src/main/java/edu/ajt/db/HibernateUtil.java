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
  return new Configuration().setProperty("hibernate.connection.driver_class","com.mysql.cj.jdbc.Driver")
   .setProperty("hibernate.connection.url",url).setProperty("hibernate.connection.username",user)
   .setProperty("hibernate.connection.password",pass).setProperty("hibernate.dialect","org.hibernate.dialect.MySQL8Dialect")
   .setProperty("hibernate.hbm2ddl.auto","validate").setProperty("hibernate.show_sql","false")
   .addAnnotatedClass(Student.class).addAnnotatedClass(Course.class).addAnnotatedClass(Admin.class).buildSessionFactory();
 }
 public static SessionFactory get(){return FACTORY;}
}