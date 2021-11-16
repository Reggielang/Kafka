#!/bin/bash
if [ $# -lt 1 ]
then
 echo '参数不能为空！'
 exit
fi

for host in hadoop102 hadoop103 hadoop104
do 
 case $1 in
 "start")
 echo "$1******$host*****ZK**********"
 ssh $host /opt/module/zookeeper-3.4.10/bin/zkServer.sh $1
 ;;
 "stop")
 echo "$1******$host*****ZK**********"
 ssh $host /opt/module/zookeeper-3.4.10/bin/zkServer.sh $1
 ;;
 "status")
 echo "$1******$host*****ZK**********"
 ssh $host /opt/module/zookeeper-3.4.10/bin/zkServer.sh $1
 ;;
 *)
 echo '参数有误！'
 exit
 ;; 
 esac
done
