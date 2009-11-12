#/bin/bash

JAVA_HOME="/usr/lib/jvm/java-6-sun"
mvn install:install-file -DgroupId=java -DartifactId=java-plugin -Dversion=1.6 -Dpackaging=jar -Dfile=$JAVA_HOME/jre/lib/plugin.jar
mvn install:install-file -DgroupId=org.ksoap2 -DartifactId=ksoap2-j2se-full -Dversion=2.1.2 -Dpackaging=jar -Dfile=ksoap2-j2se-full-2.1.2.jar
