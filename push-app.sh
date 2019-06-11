#!/bin/bash

cd bank-client-app

cf create-service servfin epay epay

cf push --no-start

cf bs bank-client-app epay
cf start bank-client-app
