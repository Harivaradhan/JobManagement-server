# Use a base image with Tomcat and a Java Runtime Environment
FROM tomcat:10.1-jdk17-temurin

# Copy your packaged WAR file to Tomcat's webapps directory
COPY target/management.war /usr/local/tomcat/webapps/ROOT.war

# Expose the port Tomcat runs on
EXPOSE 8080

# The command to start the Tomcat server
CMD ["catalina.sh", "run"]