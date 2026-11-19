package edu.ajt.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import edu.ajt.db.HibernateUtil;

/**
 * Initializes Hibernate when Tomcat deploys the application.
 * Hibernate creates missing schema tables and seeds default courses.
 */
@WebListener
public class DatabaseStartupListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            HibernateUtil.get();
            event.getServletContext().log("SMS database initialized; schema and courses ready.");
        } catch (Throwable ex) {
            event.getServletContext().log("SMS database initialization FAILED. Check SMS_DB_URL, SMS_DB_USER and SMS_DB_PASSWORD.", ex);
        }
    }
    @Override
    public void contextDestroyed(ServletContextEvent event) {}
}
