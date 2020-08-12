FROM tomcat
MAINTAINER Markus Kreth <markus.kreth@web.de>
EXPOSE 8080
RUN rm -fr /usr/local/tomcat/webapps/ROOT
COPY target/webapp /usr/local/tomcat/webapps/ROOT 
