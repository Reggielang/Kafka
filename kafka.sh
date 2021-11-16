#!/bin/bash
if [ $# -lt 1 ]
then 
 echo "缺少参数"
 exit
fi

case $1 in
start)
 for i in hadoop102 hadoop103 hadoop104
 do
 echo "=========>START $i KAFKA<=========== "
 ssh $i /opt/module/kafka_2.11-2.4.1/bin/kafka-server-start.sh -daemon /opt/module/kafka_2.11-2.4.1/config/server.properties 
 done
;;

stop)
for i in hadoop102 hadoop103 hadoop104
 do
 echo "=========>STOP $i KAFKA<=========== " 
 ssh $i /opt/module/kafka_2.11-2.4.1/bin/kafka-server-stop.sh
 done
;;

*)
 echo "USAGE:kafka.sh {start|stop}"
 exit
;;
esac
