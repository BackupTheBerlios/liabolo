@echo on

rem creation and deployment of web services, which were developed by liabolo (www.liabolo.org)
rem created by easy(Stefan Willer)

echo PLEASE START TOMCAT BEFORE CREATING WEBSERVICES!!

if not "%JAVA_HOME%" == "" goto gotJavaHome
echo Java environment not found. Please set
echo your JAVA_HOME environment variable to
echo the home of you JDK.
goto :eof

:gotJavaHome
echo Create all clases for web service
%JAVA_HOME%\bin\java -classpath lib\axis-1.1.jar;lib\axis-jaxrpc-1.1.jar;lib\axis.jar;lib\commons-logging-1.0.3.jar;lib\commons-discovery-0.2.jar;lib\wsdl4j.jar;lib\saaj-api.jar org.apache.axis.wsdl.WSDL2Java -S yes -d session -s ServerTimeService.wsdl -o src -v

echo web services
cd src
%JAVA_HOME%\bin\javac -d ..\classes -classpath ..\lib\axis-1.1.jar;..\lib\axis-jaxrpc-1.1.jar;..\lib\axis.jar;..\lib\commons-logging-1.0.3.jar;..\lib\commons-discovery-0.2.jar;..\lib\wsdl4j.jar;..\lib\saaj-api.jar org/liabolo/common/ServerTime.java org/liabolo/common/ServerTimeService.java org/liabolo/common/ServerTimeServiceLocator.java org/liabolo/common/ServerTimeSoapBindingImpl.java org/liabolo/common/ServerTimeSoapBindingSkeleton.java org/liabolo/common/ServerTimeSoapBindingStub.java
cd ..

echo copy to axis-webapps-dir
copy /Y src\org\liabolo\common\deploy.wsdd misc\axis\WEB-INF\
copy /Y src\org\liabolo\common\undeploy.wsdd misc\axis\WEB-INF\
copy /Y classes\org\liabolo\common\ServerTime* misc\axis\WEB-INF\classes\org\liabolo\common

rem only if stuff should hosted by tomcat
rem copy /Y misc\axis %TOMCAT_HOME%\webapps\axis

echo deploy webservices
java -classpath lib\axis-1.1.jar;lib\axis-jaxrpc-1.1.jar;lib\axis.jar;lib\commons-logging-1.0.3.jar;lib\commons-discovery-0.2.jar;lib\wsdl4j.jar;lib\saaj-api.jar org/apache/axis/client/AdminClient src/org/liabolo/common/deploy.wsdd
