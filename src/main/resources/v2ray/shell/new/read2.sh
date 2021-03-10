#!/usr/bin/env bash
echo "---------Begin-------------"
all=0
#10G/month
limit10=$((10*1024*1024*1024))
#20G/month
limit20=$((20*1024*1024*1024))
#M
mvalue=$((1024*1024))
echo "limit10 is $limit10 (10G/month)"
echo "limit20 is $limit20 (20G/month)"
limit=$limit10

value=0
current=`date`
beijing=`date -d "+12 hours" +"%Y-%m-%d %H:%M:%S %A %a"`
title="Updated at: $current ($beijing) EST"

echo "$title " >> /usr/local/etc/v2ray/daily/sum.txt
rm /usr/local/etc/v2ray/daily/latest.txt
echo "$title " >> /usr/local/etc/v2ray/daily/latest.txt

cd /usr/local/etc/v2ray/log
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
		limit=$limit20
		echo "It's 20G/month user."
		hint="20G/month"
	fi
	if [ ${file:2:4} = "love" ]; then
		limit=$limit10
		echo "It's 10G/month user."
		hint="10G/month"
	fi
 
    all=`awk '{sum += $2}END{print sum}' $file`
	echo "---all=$all"
	statistics=`expr $all / $mvalue`
	statistics="($statistics M)"
    if [ $all -gt $limit ]; then
	    warning="Warning: $file($all) is more than limit($limit [$hint])."
	    echo "$warning"
	    echo "$warning" >> /usr/local/etc/v2ray/stats/result.txt
		statistics="$statistics !!!"
	fi
	
	# append to sum.txt
	actual="Info: $file consumes $all $statistics"
	limitv="                          limit is $limit ($hint)"
    echo "$actual" >> /usr/local/etc/v2ray/daily/sum.txt
    echo "$limitv" >> /usr/local/etc/v2ray/daily/sum.txt
	echo " " >> /usr/local/etc/v2ray/daily/sum.txt
	# append latest.txt
	echo "$actual" >> /usr/local/etc/v2ray/daily/latest.txt
    echo "$limitv" >> /usr/local/etc/v2ray/daily/latest.txt
	echo " " >> /usr/local/etc/v2ray/daily/latest.txt
	
 echo "-------End-------------"
done

