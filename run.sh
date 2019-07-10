#!/bin/bash
while :
do
    cp target/fast-ticket-1.0-jar-with-dependencies.jar target/fast-ticket-1.0-jar-with-dependencies-run.jar
	java -server -Xms256M -Xmx400M -XX:+UnlockExperimentalVMOptions -XX:+UseG1GC -XX:G1NewSizePercent=20 -XX:G1ReservePercent=20 -XX:MaxGCPauseMillis=30 -XX:G1HeapRegionSize=16M -XX:ParallelGCThreads=4 -XX:+DisableExplicitGC -XX:+AlwaysPreTouch -XX:TargetSurvivorRatio=90 -XX:InitiatingHeapOccupancyPercent=15 -XX:G1MixedGCLiveThresholdPercent=50 -XX:+UseNUMA -XX:MaxTenuringThreshold=15 -XX:+UseCompressedOops -XX:+OptimizeStringConcat -XX:ReservedCodeCacheSize=2048m -XX:+CMSParallelRemarkEnabled -XX:+UseStringDeduplication -noverify -jar target/fast-ticket-1.0-jar-with-dependencies-run.jar
done