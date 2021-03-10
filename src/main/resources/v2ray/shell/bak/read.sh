#!/usr/bin/env bash
cd /etc/v2ray/log

echo "---------Begin-------------"
all=0
limit=$((10*1024*1024*1024))
echo "limit is $limit (10G/month)"

value=0

filelist=`find ./ -size +0c`
for file in $filelist
do 
   if [ -d $file ]; then
      echo "this is a directory!!!"
      continue
   fi
   echo "---File:$file"
 
 cat $file | while read line
 do
    echo "${line}"
	value=${line:7}
	echo "value= $value"
	all=$(($all+$value))
	echo "---all=$all"
	if [ $all -gt $limit ]
	then
		echo "Warning: $file($all) is more than limit($limit)."
	    echo "Warning: $file($all) is more than limit($limit)." >> /etc/v2ray/stats/result.txt
	fi
 done
 echo "-------End-------------"
done

