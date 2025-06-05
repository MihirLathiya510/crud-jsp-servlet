FROM tomcat:9.0-jdk11

# Remove default Tomcat applications
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy your WAR file to webapps and extract it directly
COPY target/crud-jsp-servlet.war /usr/local/tomcat/webapps/ROOT.war

# Ensure the WAR is extracted at startup
ENV CATALINA_OPTS="-Dorg.apache.catalina.STRICT_SERVLET_COMPLIANCE=false -Ddao.implementation=hibernate"

# For debugging
RUN mkdir -p /usr/local/tomcat/logs
RUN chmod -R a+w /usr/local/tomcat/logs

EXPOSE 8080

CMD ["catalina.sh", "run"]