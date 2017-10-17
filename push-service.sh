#!/bin/bash
set -e

if [[ $# -ne 3 ]]
then
  echo "Usage: ./push-service.sh hostname-for-service hostname-for-service-broker domain"
  echo; echo
  echo "Example: ./push-service.sh epay-service servfin-service-broker cfapps.io"
  exit 1
fi

service_hostname=$1
service_broker_hostname=$2
service_domain=$3

cd servfin-epay-service
cf push -n $service_hostname

cd ../servfin-service-broker
cf push --no-start
cf set-env servfin-service-broker SERVICE_URL https://$service_hostname.$service_domain
cf start servfin-service-broker

cf create-service-broker fiserv user fiserv https://$service_broker_hostname.$service_domain --space-scoped

cd ..
