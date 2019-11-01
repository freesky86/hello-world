#!/usr/bin/env bash
#=================================================
#	Description: downlink stats
#	Version: 0.0.1
#	Author: Max Zhang
#=================================================
/usr/bin/v2ray/v2ctl api --server=127.0.0.1:54979 StatsService.GetStats 'name: "user>>>love65523@v2ray.com>>>traffic>>>downlink" reset: false' | grep value >> /etc/v2ray/log/love65523-down.txt
/usr/bin/v2ray/v2ctl api --server=127.0.0.1:54979 StatsService.GetStats 'name: "user>>>love65524@v2ray.com>>>traffic>>>downlink" reset: false' | grep value >> /etc/v2ray/log/love65524-down.txt
/usr/bin/v2ray/v2ctl api --server=127.0.0.1:54979 StatsService.GetStats 'name: "user>>>love65525@v2ray.com>>>traffic>>>downlink" reset: false' | grep value >> /etc/v2ray/log/love65525-down.txt
/usr/bin/v2ray/v2ctl api --server=127.0.0.1:54979 StatsService.GetStats 'name: "user>>>love65526@v2ray.com>>>traffic>>>downlink" reset: false' | grep value >> /etc/v2ray/log/love65526-down.txt

/usr/bin/v2ray/v2ctl api --server=127.0.0.1:54979 StatsService.GetStats 'name: "user>>>love1234@v2ray.com>>>traffic>>>downlink" reset: false' | grep value >> /etc/v2ray/log/love1234-down.txt
/usr/bin/v2ray/v2ctl api --server=127.0.0.1:54979 StatsService.GetStats 'name: "user>>>love8888@v2ray.com>>>traffic>>>downlink" reset: false' | grep value >> /etc/v2ray/log/love8888-down.txt
/usr/bin/v2ray/v2ctl api --server=127.0.0.1:54979 StatsService.GetStats 'name: "user>>>love6666@v2ray.com>>>traffic>>>downlink" reset: false' | grep value >> /etc/v2ray/log/love6666-down.txt

/usr/bin/v2ray/v2ctl api --server=127.0.0.1:54979 StatsService.GetStats 'name: "user>>>g20-0904@v2ray.com>>>traffic>>>downlink" reset: false' | grep value >> /etc/v2ray/log/g20-0904-down.txt

/usr/bin/v2ray/v2ctl api --server=127.0.0.1:54979 StatsService.GetStats 'name: "user>>>love6103@v2ray.com>>>traffic>>>downlink" reset: false' | grep value >> /etc/v2ray/log/love6103-down.txt

/usr/bin/v2ray/v2ctl api --server=127.0.0.1:54979 StatsService.GetStats 'name: "user>>>love6401@v2ray.com>>>traffic>>>downlink" reset: false' | grep value >> /etc/v2ray/log/love6401-down.txt

/usr/bin/v2ray/v2ctl api --server=127.0.0.1:54979 StatsService.GetStats 'name: "user>>>love6500@v2ray.com>>>traffic>>>downlink" reset: false' | grep value >> /etc/v2ray/log/love6500-down.txt
/usr/bin/v2ray/v2ctl api --server=127.0.0.1:54979 StatsService.GetStats 'name: "user>>>g20-6501@v2ray.com>>>traffic>>>downlink" reset: false' | grep value >> /etc/v2ray/log/g20-6501-down.txt
/usr/bin/v2ray/v2ctl api --server=127.0.0.1:54979 StatsService.GetStats 'name: "user>>>love6502@v2ray.com>>>traffic>>>downlink" reset: false' | grep value >> /etc/v2ray/log/love6502-down.txt
/usr/bin/v2ray/v2ctl api --server=127.0.0.1:54979 StatsService.GetStats 'name: "user>>>love6501@v2ray.com>>>traffic>>>downlink" reset: false' | grep value >> /etc/v2ray/log/love6501-down.txt

