# Forum (forum)
* This is a simple forum web application that we use as a starting point for building our example application.
* Good luck and have fun building it!


## Screenshots
* Feel free to look at [screenshots] (/screenshots.md "Forum Screenshots"). 


## Requirements
* Java 1.7
* Maven 3.x
* Tomcat 7.0.x
* HSQLDB database (see [hsqldb-runner] (https://github.com/javaclinic/hsqldb-runner) for easy database integration)


## How to start and configure database
* Look at [hsqldb-runner] (https://github.com/javaclinic/hsqldb-runner) project
* Clone the project, e.g. `git clone git@github.com:javaclinic/hsqldb-runner.git`
* Replace `hsqldb-runner/src/main/resources/database/schema.sql` with:
```sql
DROP TABLE forum_users IF EXISTS;
CREATE TABLE forum_users (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) NOT NULL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    organization VARCHAR(255),
    title VARCHAR(255),
    password VARCHAR(255),
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

DROP TABLE forum_categories IF EXISTS;
CREATE TABLE forum_categories (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255)
);
```
* If you prefer an empty database with no seed data, then remove `hsqldb-runner/src/main/resources/database/seed.sql` file contnets, or 
* If you prefer some seed data, then replace `hsqldb-runner/src/main/resources/database/seed.sql` contents with:
```sql
INSERT INTO forum_users (email,firstname,lastname,organization,title,password) VALUES ('john@email.com','John','Doe','My Organization','CFO','password');
INSERT INTO forum_users (email,firstname,lastname,organization,title,password) VALUES ('jane@email.com','Jane','Doe','My Organization','CEO','password');
INSERT INTO forum_users (email,firstname,lastname,organization,title,password) VALUES ('jack@email.com','Jack','Doe','My Organization','CIO','password');
INSERT INTO forum_users (email,firstname,lastname,organization,title,password) VALUES ('jill@email.com','Jill','Doe','Other Organization','CEO','password');
INSERT INTO forum_users (email,firstname,lastname,organization,title,password) VALUES ('jenn@email.com','Jenn','Doe','Other Organization','CIO','password');

INSERT INTO forum_categories (name,description) VALUES ('java','Java');
INSERT INTO forum_categories (name,description) VALUES ('javaee','Java EE');
INSERT INTO forum_categories (name,description) VALUES ('spring','Spring');
INSERT INTO forum_categories (name,description) VALUES ('spring-security','Spring Security');
INSERT INTO forum_categories (name,description) VALUES ('hibernate','Hibernate');
```


## How to configure HSQLDB datasource in Tomcat
* Download [HSQLDB JDBC Drivers 2.3.2] (http://central.maven.org/maven2/org/hsqldb/hsqldb/2.3.2/hsqldb-2.3.2.jar), e.g. http://mvnrepository.com/artifact/org.hsqldb/hsqldb/2.3.2
* Copy `hsqldb-2.3.2.jar` drivers to Tomcat shared library, e.g. `TOMCAT_HOME/lib`
* That's it!

**NOTE**: We have already configured `forum` application with the datasource resource in `forum/src/main/webapp/META-INF/context.xml`, e.g.

```xml
<?xml version="1.0" encoding="UTF-8"?>

<!-- 
    META-INF/context.xml file is Tomcat-specific deployment descriptor.
    This file will automatically configure resource references, and other
    Tomcat configuration options for this application.
 -->
 
<!DOCTYPE Context [ ]>
<Context>

    <!--
      Make sure you have copied the HSQLDB JDBC driver to TOMCAT_HOME/lib folder.
      You can download the latest driver from Maven Repository, e.g.
        http://central.maven.org/maven2/org/hsqldb/hsqldb/2.3.2/hsqldb-2.3.2.jar
    -->
    <Resource
        type="javax.sql.DataSource"
        auth="Container"
        name="jdbc/forum"
        url="jdbc:hsqldb:hsql://localhost:9002/mydb"
        username="sa"
        password=""
        driverClassName="org.hsqldb.jdbcDriver"
        initialSize="5"
        maxActive="20"
        maxIdle="10"
        maxWait="5000"
        minIdle="5"
        logAbandoned="true"
    />

</Context>
```


## How to build WAR package and deploy to Tomcat
* `mvn clean package` - builds a war file, e.g. `target/forum.war`
* Copy `target/forum.war` to your Tomcat deployments folder, e.g. `TOMCAT_HOME/webapps/forum.war`


## How to test the application
* Once the application has been deployed, try http://localhost:8080/forum


## How to checkout particular branch version
* Clone the repository, e.g. `git clone git@github.com:javaclinic/forum.git` or `git clone https://github.com/javaclinic/forum.git`
* Switch the branch, e.g. `git checkout master`
* Check the status, e.g. `git status`


## Known branches
* [master] (https://github.com/javaclinic/forum/tree/master) Master Branch
* [servlets-jdbc-plain] (https://github.com/javaclinic/forum/tree/servlets-jdbc-plain) Servlets, JSPs (plain), JDBC
* [servlets-jdbc-plain-with-security] (https://github.com/javaclinic/forum/tree/servlets-jdbc-plain-with-security) Servlets, JSPs (plain), JDBC with Servlet security
* [servlets-hibernate-with-xml] (https://github.com/javaclinic/forum/tree/servlets-hibernate-with-xml) Servlets, JSPs (plain), Hibernate with XML
* [servlets-hibernate-with-mixed-configuration] (https://github.com/javaclinic/forum/tree/servlets-hibernate-with-mixed-configuration) Servlets, JSPs (plain), Hibernate with mixed configuration (XML and Annotation-based)
* [servlets-hibernate-with-annotations] (https://github.com/javaclinic/forum/tree/servlets-hibernate-with-annotations) Servlets, JSPs (plain), Hibernate with annotation-based configuration
* [servlets-jpa-plain] (https://github.com/javaclinic/forum/tree/servlets-jpa-plain) Servlets, JSPs (plain), JPA
* [servlets-jdbc-plain-spring-with-xml] (https://github.com/javaclinic/forum/tree/servlets-jdbc-plain-spring-with-xml) Servlets, JSPs (plain), JDBC, Spring with XML configuration
* [servlets-jdbc-plain-spring-with-mixed-configuration] (https://github.com/javaclinic/forum/tree/servlets-jdbc-plain-spring-with-mixed-configuration) Servlets, JSPs (plain), JDBC, Spring with mixed XML configuration and annotations
* [servlets-jdbc-plain-spring-no-xml] (https://github.com/javaclinic/forum/tree/servlets-jdbc-plain-spring-no-xml) Servlets, JSPs (plain), JDBC, Spring with annotations (no-XML)
* [servlets-hibernate-spring-with-xml] (https://github.com/javaclinic/forum/tree/servlets-hibernate-spring-with-xml) Servlets, JSPs (plain), Hibernate, Spring with XML configuration
* [servlets-hibernate-spring-with-mixed-configuration] (https://github.com/javaclinic/forum/tree/servlets-hibernate-spring-with-mixed-configuration) Servlets, JSPs (plain), JDBC, Spring with mixed XML configuration and annotations
* [servlets-jdbc-plain-spring-no-xml] (https://github.com/javaclinic/forum/tree/servlets-jdbc-plain-spring-no-xml) Servlets, JSPs (plain), JDBC, Spring with annotations (no-XML)






