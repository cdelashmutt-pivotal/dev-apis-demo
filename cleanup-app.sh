#!/bin/bash

cf d bank-client-app -r -f
cf ds epay -f
cf ds myHystrixService -f
