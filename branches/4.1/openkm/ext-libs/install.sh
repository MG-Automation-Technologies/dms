#/bin/bash

mvn install:install-file -DgroupId=entagged.audioformats -DartifactId=audioformats -Dversion=0.15 -Dpackaging=jar -Dfile=entagged-audioformats-0.15.jar
mvn install:install-file -DgroupId=com.artofsolving -DartifactId=jodconverter -Dversion=2.2.2 -Dpackaging=jar -Dfile=jodconverter-2.2.2.jar
