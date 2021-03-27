#!/bin/bash
# 生成流量日志 /etc/v2ray/log/
/usr/local/etc/v2ray/shell/all.sh
# 读取并分析日志，流量超出10G, 写入/etc/v2ray/stats/result.txt文件
/usr/local/etc/v2ray/shell/read2.sh
# 如果有result.txt文件, 则发送邮件
/usr/local/etc/v2ray/shell/mail.sh

