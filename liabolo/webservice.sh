#!/usr/local/bin/bash
# creation and deployment of web services, which were developed by liabolo (www.liabolo.org)
# created by easy(Stefan Willer)

echo PLEASE START TOMCAT BEFORE CREATING WEBSERVICES!!

#if [ "$JAVA_HOME" = "" ] : then
#  echo "ERROR: JAVA_HOME not found in your environment."
#  echo
#  echo "Please, set the JAVA_HOME variable in your environment to match the"
#  echo "location of the Java Virtual Machine you want to use."
#  exit 1
#fi

echo Create all clases for web service
$JAVA_HOME/bin/java -classpath ./lib/axis-1.1.jar:./lib/axis-jaxrpc-1.1.jar:./lib/axis.jar:./lib/commons-logging-1.0.3.jar:./lib/commons-discovery-0.2.jar:./lib/wsdl4j.jar:./lib/saaj-api.jar org.apache.axis.wsdl.WSDL2Java -S yes -d session -s SignatureService.wsdl -o src -v

echo web services
cd src
$JAVA_HOME/bin/javac -d ../classes -classpath ../classes:../lib/axis-1.1.jar:../lib/axis-jaxrpc-1.1.jar:../lib/axis.jar:../lib/commons-logging-1.0.3.jar:../lib/commons-discovery-0.2.jar:../lib/wsdl4j.jar:../lib/saaj-api.jar org/liabolo/common/ServerTime.java org/liabolo/common/SignatureService.java org/liabolo/common/SignatureServiceLocator.java org/liabolo/common/SignatureSoapBindingImpl.java org/liabolo/common/SignatureSoapBindingSkeleton.java org/liabolo/common/SignatureSoapBindingStub.java
cd ..

echo copy to axis-webapps-dir
cp src/org/liabolo/common/deploy.wsdd misc/axis/WEB-INF/
cp src/org/liabolo/common/undeploy.wsdd misc/axis/WEB-INF/
cp classes/org/liabolo/common/Signature* misc/axis/WEB-INF/classes/org/liabolo/common

# only if stuff should hosted by tomcat
# copy -R misc/axis $TOMCAT_HOME/webapps/axis

echo deploy webservices
$JAVA_HOME/bin/java -classpath ./lib/axis-1.1.jar:./lib/axis-jaxrpc-1.1.jar:./lib/axis.jar:./lib/commons-logging-1.0.3.jar:./lib/commons-discovery-0.2.jar:./lib/wsdl4j.jar:./lib/saaj-api.jar org/apache/axis/client/AdminClient src/org/liabolo/common/deploy.wsdd
