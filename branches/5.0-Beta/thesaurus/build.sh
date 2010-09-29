#!/bin/bash

mvn clean package
cp -v target/thesaurus-jar-with-dependencies.jar thesaurus.jar
