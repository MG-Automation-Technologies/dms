#/bin/bash

# PROPIETARY OPENKM EXTENSIONS LIBRARY NOT INCLUDED BY DEFAULT
mvn install:install-file -DgroupId=com.openkm.extension -DartifactId=sample-full -Dversion=1.0 -Dpackaging=jar -Dfile=target/sample-1.0-full.jar
