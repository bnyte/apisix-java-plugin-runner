FROM maven:3.8.4-openjdk-11
COPY maven/settings.xml /usr/share/maven/conf/settings.xml
COPY . .
RUN mvn clean package