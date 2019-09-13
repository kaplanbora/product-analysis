#!/bin/bash
mkdir ${HOST_PATH}
cp target/scala-2.12/product-analysis-assembly-1.0.0.jar ${HOST_PATH}/product-analysis.jar
cp case.csv ${HOST_PATH}/case.csv

docker-compose up -d

docker exec -it ${JOBMANAGER_NAME} chown -R flink ${CONT_PATH}

docker exec -it ${JOBMANAGER_NAME} flink run ${CONT_PATH}/product-analysis.jar --input-file ${CONT_PATH}/case.csv \
    --output-dir ${CONT_PATH} \
    --top-users ${TOP_USERS} \
    --user-id-for-events ${USER_ID_FOR_EVENTS} \
    --user-id-for-products ${USER_ID_FOR_PRODUCTS}
