#/bin/bash

JAVA_HOME="/usr/lib/jvm/java-6-sun"
mvn install:install-file -DgroupId=java -DartifactId=java-plugin -Dversion=1.6 -Dpackaging=jar -Dfile=$JAVA_HOME/jre/lib/plugin.jar
mvn install:install-file -DgroupId=com.openkm -DartifactId=ws-client -Dversion=2.1 -Dpackaging=jar -Dfile=okm-ws-client-2.1.jar
