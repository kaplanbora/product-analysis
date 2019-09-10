# Product Analysis

## How to run locally
Build the fat jar 
```bash
sbt pack
```

Start the Flink cluster
```bash
start-cluster.sh
```

Submit the job
```bash
flink run target/scala-2.12/product-analysis_2.12-0.1.0-SNAPSHOT.jar --input-file /tmp/flink/case.csv \
    --output-dir /tmp/flink \
    --top-users 5 \
    --user-id-for-events 47 \
    --user-id-for-products 47 
```
