#!/bin/bash
sum=0
for i in `seq 1 100`
do
     sum=$[$i+$sum]
done
echo $sum

limit=$((10*1024*1024*1024))
echo "limit is $limit"
value=4616293
value=$(($limit+$value))
echo "value is $value"

files=`ls -l | grep "^-" | wc -l`
echo "files num: $files"
if [ $files -gt 0 ]
then
   echo "there is $files files"
fi
