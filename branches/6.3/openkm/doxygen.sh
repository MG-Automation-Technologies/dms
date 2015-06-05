#!/bin/bash

RED='\e[1;31m';
GREEN='\e[1;32m';
YELLOW='\e[1;33m';
BLUE='\e[1;34m';
WHITE='\e[1;37m';
RESET='\e[0m';
HOST="doxygen.openkm.com"

echo -e ${WHITE} "\n--> $HOST" ${RED};
knock -v $HOST 5701; sleep 1;
knock -v $HOST 5602; sleep 1;
knock -v $HOST 5503;
echo -ne ${RESET}

doxygen doxyfile.cfg
# rsync -apzhH --delete --copy-links --progress --stats doxygen/html/* root@$HOST:/home/openkm/doxygen/6.2.x/
