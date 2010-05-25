#/bin/bash

mvn install:install-file -DgroupId=entagged.audioformats -DartifactId=audioformats -Dversion=0.15 -Dpackaging=jar -Dfile=entagged-audioformats-0.15.jar
mvn install:install-file -DgroupId=weka -DartifactId=weka -Dversion=3.7 -Dpackaging=jar -Dfile=weka-3.7.jar
mvn install:install-file -DgroupId=com.google.gwt -DartifactId=gwt-incubator -Dversion=20100204-r1747 -Dpackaging=jar -Dfile=gwt-incubator-20100204-r1747.jar
mvn install:install-file -DgroupId=com.google.code.gwt-log -DartifactId=gwt-log -Dversion=3.0.1 -Dpackaging=jar -Dfile=gwt-log-3.0.1.jar
mvn install:install-file -DgroupId=org.artofsolving.jodconverter -DartifactId=jodconverter-core -Dversion=3.0-beta-3 -Dpackaging=jar -Dfile=jodconverter-core-3.0-beta-3.jar
