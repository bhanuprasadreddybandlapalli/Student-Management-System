# Local run instructions

1. Install Java 11+, Maven, MySQL 8, and Apache Tomcat 9 (javax.servlet, not Tomcat 10).
2. Run Task2/database/schema.sql in MySQL.
3. Set SMS_DB_PASSWORD, optionally SMS_DB_USER and SMS_DB_URL, and a strong random SMS_SETUP_TOKEN in the Tomcat process environment.
4. From Task4 run `mvn clean package`.
5. Copy `target/sms.war` to Tomcat 9 webapps and start Tomcat.
6. Visit `http://localhost:8080/sms/setup` once, create admin, then `/sms/login`.
7. After setup, remove SMS_SETUP_TOKEN from server environment and restart.

Do not expose an unsecured MySQL instance to the internet. A public live URL is **not** provided; this is a Tomcat/MySQL application, not a static GitHub Pages site.
