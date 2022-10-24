FROM tomee:8.0.12-jre17-alpine-plus
COPY tomcat_users.xml /usr/local/tomee/conf/tomcat-users.xml
COPY Jogo_da_Memoria.war /usr/local/tomee/webapps/Jogo_da_Memoria.war