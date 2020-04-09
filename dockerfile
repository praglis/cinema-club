FROM arm32v7/tomcat
                        COPY app.war /usr/local/tomcat/webapps
                        CMD ["catalina.sh", "run"]