#!/bin/bash
cd /etc/v2ray/stats
pwd
files=`ls -l | grep "^-" | wc -l`
echo "files num: $files"
if [ $files -gt 0 ]
then
   echo "there is $files files"
   mail -s 'Warning from iFreesky' zhwyv@163.com < /etc/v2ray/stats/result.txt
fi
echo "---mail sent successfully."

