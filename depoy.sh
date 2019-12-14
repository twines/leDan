#!/bin/bash

a=`uname  -a`

b="Darwin"
c="centos"
d="ubuntu"

if [[ $a =~ $b ]];then
    echo "mac"
    lsof -i:8080


else
netstat -lnpt |grep 8080
fi

read -t 60 -p "请输入需要结束的端口:" port

pid=$(netstat -nlp | grep :${port} | awk '{print $7}' | awk -F"/" '{ print $1 }');

if [[  -n  "$pid"  ]];  then
    kill -9 ${pid};
fi
if [[ $a =~ $b ]];then
echo "不用打包"
else
rm -rf ~/*.war
cd ~/leDan
echo "pull update code"
git pull
#项目web下打包
mvn clean package -P pro
read -t 60 -p "请输入需要结束的端口:" version
#移动到~
mv target/lee-${version}-SNAPSHOT.war ~/lee-${version}-SNAPSHOT.war
#    运行
nohup java -jar ~/lee-${version}-SNAPSHOT.war &
fi


