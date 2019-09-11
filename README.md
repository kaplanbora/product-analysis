# Product Analysis

## Run the tests
Make sure you have `sbt` installed
```bash
sbt test
```

## Run on local machine
Build the fat jar 
```bash
sbt assembly
```

Start the Flink cluster
```bash
start-cluster.sh
```

Submit the job
```bash
flink run product-analysis-assembly-0.1.0-SNAPSHOT.jar --input-file case.csv \
    --output-dir /tmp/results \
    --top-users 5 \
    --user-id-for-events 47 \
    --user-id-for-products 47 
```

## Run on docker containers
Build the fat jar
```bash
sbt assembly
```

Before submitting the job to a docker container we have to create jobmanager and taskmanager containers. For that we can use `docker-compose`. We also have to mount a volume to containers to put the jar and data to analyze. Flink job will generate requested reports to that volume as well. To do all these we use `run.sh`. 

Set these environment variables that will be used by `docker-compose` and `run.sh`.
```bash
export HOST_PATH='/tmp/flink'
export CONT_PATH='/data'
export JOBMANAGER_NAME='flink-jobmanager-1.9.0'
export TASKMANAGER_NAME='flink-taskmanager-1.9.0'
```

Run the script to create jobmanager and taskmanager containers, then submit the job to `jobmanager` container.
```bash
chmod +x run.sh
./run.sh
```

You can see the results in `$HOST_PATH` directory which has been set by the script
```bash
ls $HOST_PATH
# case.csv  problem1.txt  problem2.txt  problem3.txt  problem4.txt  problem5.txt  product-analysis.jar
```

## Clean up
Remove containers and attached volumes
```bash
docker-compose down --volumes
```
