#!/bin/bash

pluginHome=$1

# 删除当前旧版本插件文件
rm -rf $pluginHome/apisix-runner-bin/

# 解压当前新的插件包
tar -zxvf $pluginHome/apache-apisix-java-plugin-runner-0.1.0-bin.tar.gz

# 重启apisix容器
docker restart docker-apisix-apisix-1
