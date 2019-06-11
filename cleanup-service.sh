#!/bin/bash

cf delete-service-broker servfin -f
cf d servfin-service-broker -f
cf ds db
cf d servfin-epay-service -f
