#!/bin/bash

#cf d bank-client-app -r -f
#cf ds epay -f
cf delete-service-broker servfin -f
cf d servfin-service-broker -f
cf d servfin-epay-service -f
