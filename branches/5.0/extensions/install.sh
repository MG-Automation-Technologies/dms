#/bin/bash

# PROPIETARY OPENKM EXTENSIONS LIBRARY NOT INCLUDED BY DEFAULT
mvn install:install-file -DgroupId=com.openkm -DartifactId=extensions-full -Dversion=5.0 -Dpackaging=jar -Dfile=target/extensions-5.0-full.jar