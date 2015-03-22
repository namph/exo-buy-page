# Environment Variable Prerequisites
#
# JAVA_OPTS override jvm options for example: Xmx, Xms etc.

# custom JAVA options
[ -z ${EXO_JVM_SIZE_MAX} ] && EXO_JVM_SIZE_MAX=1g
[ -z ${EXO_JVM_SIZE_MIN} ] && EXO_JVM_SIZE_MIN=512m
[ -z ${EXO_JVM_PERMSIZE_MAX} ] && EXO_JVM_PERMSIZE_MAX=256m

# this host external address
# HOST_EXTERNAL_ADDR=`ifconfig eth0 | grep 'inet addr:' | cut -d: -f2 | awk '{ print $1}'`
[ -z ${HOST_EXTERNAL_ADDR} ]  && HOST_EXTERNAL_ADDR="localhost"

# dir for exo buy data
EXO_BUY_DATA_DIR="$CATALINA_HOME/data"


# exo buy logs
EXO_BUY_LOGS_DIR="$CATALINA_HOME/logs/exo-buy"

# needs for showing logs of application servers through apache
APPLICATION_SERVER_LOGS_PORT="8085"

# exo buy config
EXO_BUY_CONF_DIR="$CATALINA_HOME/exo-buy-conf"

# Braintree option
#[ -z ${BRAINTREE_SETTING_MODE} ] && BRAINTREE_SETTING_MODE="prod"
[ -z ${BRAINTREE_SETTING_MODE} ] && BRAINTREE_SETTING_MODE="dev"

# custom JAVA options
EXO_JAVA_OPTS="-Xms${EXO_JVM_SIZE_MIN} -Xmx${EXO_JVM_SIZE_MAX} -XX:MaxPermSize=${EXO_JVM_PERMSIZE_MAX}"

# exo buy variables
EXO_BUY_OPTS="-Dexo.buy.data.dir=$EXO_BUY_DATA_DIR"
EXO_BUY_OPTS="$EXO_BUY_OPTS -Dexo.buy.configuration.dir=$EXO_BUY_CONF_DIR"
EXO_BUY_OPTS="$EXO_BUY_OPTS -Dexo.buy.configuration.file=$EXO_BUY_CONF_DIR/exo-buy-configuration.properties"

# exo buy logs
EXO_BUY_OPTS="$EXO_BUY_OPTS -Dapplication.server.logs.port=$APPLICATION_SERVER_LOGS_PORT"
EXO_BUY_OPTS="$EXO_BUY_OPTS -Dexo.buy.log.dir=$EXO_BUY_LOGS_DIR"
EXO_BUY_OPTS="$EXO_BUY_OPTS -Dlogback.configurationFile=$CATALINA_HOME/conf/logback.xml"

# Braintree option
EXO_BUY_OPTS="$EXO_BUY_OPTS -Dbuypage.braintree.setting.mode=${BRAINTREE_SETTING_MODE}"
EXO_BUY_OPTS="$EXO_BUY_OPTS -Dbraintree.dev.configuration.file=$EXO_BUY_CONF_DIR/dev_braintree.properties"
EXO_BUY_OPTS="$EXO_BUY_OPTS -Dbraintree.prod.configuration.file=$EXO_BUY_CONF_DIR/prod_braintree.properties"

#uncomment if you want to debug app server
#REMOTE_DEBUG="-Xdebug -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=y"
REMOTE_DEBUG=""

export JAVA_OPTS="$JAVA_OPTS $EXO_JAVA_OPTS $EXO_BUY_OPTS $REMOTE_DEBUG"
export CLASSPATH="${CATALINA_HOME}/conf/:${CATALINA_HOME}/lib/jul-to-slf4j-1.7.2.jar:\
${CATALINA_HOME}/lib/slf4j-api-1.7.2.jar:${CATALINA_HOME}/lib/logback-classic-1.0.9.jar:${CATALINA_HOME}/lib/logback-core-1.0.9.jar"

# Catalina pid file
[ -z "$CATALINA_PID" ]  && CATALINA_PID="$CATALINA_HOME/temp/catalina.tmp"
export CATALINA_PID
