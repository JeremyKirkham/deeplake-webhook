#!/bin/bash -ex

lein uberjar
aws --region us-east-1 lambda update-function-code --function-name deeplakeWebhookEventHandler --zip-file fileb://./target/deeplakeWebhook-standalone.jar
