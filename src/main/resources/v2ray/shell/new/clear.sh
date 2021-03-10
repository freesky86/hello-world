#!/usr/bin/env bash

cd /usr/local/etc/v2ray/log
pwd
rm -rf *
echo "---Delete all log files.---"

cd /usr/local/etc/v2ray/stats
pwd
rm -rf *
echo "---Delete stats file.---"

cd /usr/local/etc/v2ray/daily
pwd
#rm -rf *
#echo "---Delete daily file.---"
echo $(date +%Y-%m-%d)
mv sum.txt sum_$(date +%Y-%m-%d).txt
echo "---rename is done.---"
