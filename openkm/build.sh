#!/bin/bash

export STANDALONE=false

(cd ../scanner && ./build.sh && cp -v target/signed/scanner.jar ../openkm/src/main/webapp/scanner.jar)
(cd ../uploader && ./build.sh && cp -v target/signed/uploader.jar ../openkm/src/main/webapp/uploader.jar)
mvn -o -Dmaven.test.skip=true clean gwt:compile install
