#!/bin/bash

cd bank-client-app

cf cs p-circuit-breaker-dashboard standard myHystrixService
cf create-service fiserv epay epay

cf push --no-start

hystrix_guid=`cf service myHystrixService --guid`

#Wait for service
echo -n "Waiting for myHystrixService to finish creating."
hystrix_status=`cf curl /v2/service_instances/$hystrix_guid | jq .entity.last_operation.state -r`
while [[ "$hystrix_status" = "in progress" ]]
do
  sleep 2
  echo -n "."
  hystrix_status=`cf curl /v2/service_instances/$hystrix_guid | jq .entity.last_operation.state -r`
done
echo

if [[ "$hystrix_status" != "succeeded" ]]
then
  echo "Error creating myHystrixService"
  exit 1
fi

cf bs bank-client-app epay
cf bs bank-client-app myHystrixService
cf start bank-client-app
