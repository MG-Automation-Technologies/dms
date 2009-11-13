#/bin/bash

mvn install:install-file -DgroupId=entagged.audioformats -DartifactId=audioformats -Dversion=0.15 -Dpackaging=jar -Dfile=entagged-audioformats-0.15.jar
mvn install:install-file -DgroupId=weka -DartifactId=weka -Dversion=3.7 -Dpackaging=jar -Dfile=weka-3.7.jar
