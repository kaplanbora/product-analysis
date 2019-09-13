# Product Analysis
This is an analysis of user events about their interactions on products. Written in Scala 2.12 using Apache Flink 1.9.0. 
These are the problems that are analyzed:

1. Unique Product View counts by ProductId
2. Unique Event counts
3. Top N Users who fulfilled all the events 
4. All events of a given user
5. Product views of a given user

## Run on Local Machine
Build the fat jar
```bash
sbt clean assembly
```

Start the Flink cluster
```bash
$FLINK_PATH/bin/start-cluster.sh
```

Submit the job
```bash
$FLINK_PATH/bin/flink run target/scala-2.12/product-analysis-assembly-1.0.0.jar --input-file case.csv \
    --output-dir /tmp/results \
    --top-users 5 \
    --user-id-for-events 47 \
    --user-id-for-products 47
```

You can see the results in `/tmp/results` directory
```bash
ls /tmp/results
# problem1.txt  problem2.txt  problem3.txt  problem4.txt  problem5.txt
```

## Run on Docker Containers
Build the fat jar
```bash
sbt clean assembly
```

Before submitting the job to a docker container we have to create jobmanager and taskmanager containers. For that we can use `docker-compose`. We also have to mount a volume to containers to put the jobs jar and the data to analyze. Flink job will generate requested reports to that volume as well. To do all these we use `run.sh`. 

Set these environment variables that will be used by `docker-compose`, `run.sh` and the Flink Job by
editing the `variables` file. Then source it to the shell
```bash
source ./variables
```

Run the script to create jobmanager and taskmanager containers, then submit the job to `jobmanager` container.
```bash
chmod +x run.sh
./run.sh
```

You can see the results in `$HOST_PATH` directory.
```bash
ls $HOST_PATH
# case.csv  problem1.txt  problem2.txt  problem3.txt  problem4.txt  problem5.txt  product-analysis.jar
```

## Clean up
Remove containers and attached volumes
```bash
docker-compose down --volumes
```
