#! /bin/bash

set -e

docker-compose -f docker-compose-infra.yml down
docker-compose -f docker-compose-infra.yml up -d

until (echo select 1 | sh postgres-cli.sh -i > /dev/null)
do
  echo slepping for postgres
  sleep 5
done

sh gradlew integration-test:test

docker-compose -f docker-compose-infra.yml down