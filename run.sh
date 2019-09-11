#!/bin/bash
mkdir $HOST_PATH
cp target/scala-2.12/product-analysis-assembly-0.1.0-SNAPSHOT.jar $HOST_PATH/product-analysis.jar
cp case.csv $HOST_PATH/case.csv

docker-compose up -d

docker exec -it $JOBMANAGER_NAME chown -R flink $CONT_PATH

docker exec -it $JOBMANAGER_NAME flink run $CONT_PATH/product-analysis.jar --input-file $CONT_PATH/case.csv \
    --output-dir $CONT_PATH \
    --top-users 5 \
    --user-id-for-events 47 \
    --user-id-for-products 47 
