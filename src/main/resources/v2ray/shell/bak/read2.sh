#!/usr/bin/env bash
echo "---------Begin-------------"
all=0
limit1=$((10*1024*1024*1024))
limit2=$((10*1024*1024*1024*2))
mvalue=$((1024*1024))
echo "limit1 is $limit1 (10G/month)"
echo "limit2 is $limit2 (20G/month)"
limit=$limit1

value=0
current=`date`
beijing=`date -d "+12 hours" +"%Y-%m-%d %H:%M:%S %A %a"`
title="Updated at: $current ($beijing) EST"

echo "$title " >> /etc/v2ray/daily/sum.txt
rm /etc/v2ray/daily/latest.txt
echo "$title " >> /etc/v2ray/daily/latest.txt

cd /etc/v2ray/log
filelist=`find ./ -size +0c`
for file in $filelist
do 
    if [ -d $file ]; then
      echo "this is a directory!!!"
      continue
    fi
    echo "---File:$file---"
	#---File:./love65525-down.txt---
	#---File:./g20-0904-down.txt---
	if [ ${file:2:3} = "g20" ]; then
		limit=$limit2
		echo "It's 20G/month user."
		hint="20G/month"
	fi
	if [ ${file:2:4} = "love" ]; then
		limit=$limit1
		echo "It's 10G/month user."
		hint="10G/month"
	fi
 
    all=`awk '{sum += $2}END{print sum}' $file`
	echo "---all=$all"
	statistics=`expr $all / $mvalue`
	# append to sum.txt
	actual="Info: $file consumes $all ($statistics M)"
	limitv="                          limit is $limit ($hint)"
    echo "$actual" >> /etc/v2ray/daily/sum.txt
    echo "$limitv" >> /etc/v2ray/daily/sum.txt
	echo " " >> /etc/v2ray/daily/sum.txt
	# append latest.txt
	echo "$actual" >> /etc/v2ray/daily/latest.txt
    echo "$limitv" >> /etc/v2ray/daily/latest.txt
	echo " " >> /etc/v2ray/daily/latest.txt
	
    if [ $all -gt $limit ]; then
	    warning="Warning: $file($all) is more than limit($limit [$hint])."
	    echo "$warning"
	    echo "$warning" >> /etc/v2ray/stats/result.txt
	fi
 echo "-------End-------------"
done

