#!/bin/bash

doxygen doxyfile.cfg
# rsync -apzhH --delete --copy-links --progress --stats doxygen/html/* root@$HOST:/home/openkm/doxygen/6.2.x/
