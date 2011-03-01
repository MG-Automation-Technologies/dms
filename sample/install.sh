#/bin/bash

# PROPIETARY OPENKM EXTENSIONS LIBRARY NOT INCLUDED BY DEFAULT
mvn install:install-file -DgroupId=com.openkm.extension -DartifactId=sample-full -Dversion=5.1 -Dpackaging=jar -Dfile=target/sample-5.1-full.jar
