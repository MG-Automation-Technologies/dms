#/bin/bash

# PROPIETARY OPENKM EXTENSIONS LIBRARY NOT INCLUDED BY DEFAULT
mvn install:install-file -DgroupId=com.openkm -DartifactId=extensions-full -Dversion=LTE -Dpackaging=jar -Dfile=target/extensions-LTE-full.jar
