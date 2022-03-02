#!/usr/bin

# 获取插件相关目录
pluginHome=$1
rootHome=$(pwd)
if [ -z $pluginHome ]; then
  pluginHome=$(pwd)/plugins
  mkdir -p pluginHome
fi

cp $rootHome/dist/apache-apisix-java-plugin-runner-0.1.0-bin.tar.gz $pluginHome/apache-apisix-java-plugin-runner-0.1.0-bin.tar.gz

sh scripts/update_plugin.sh $pluginHome




