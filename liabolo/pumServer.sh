#!/bin/sh
# -----------------------------------------------------------------------------
# pumServer.sh - Script for Liabolo-Server
#
# $Id: pumServer.sh,v 1.1 2004/09/13 17:45:52 viegelinsch Exp $
# -----------------------------------------------------------------------------

echo "Using LIABOLO_HOME:   $LIABOLO_HOME"
echo "Using JAVA_HOME:   $JAVA_HOME"


if [ ! -f "$LIABOLO_HOME/lib/start.jar" ]; then
	echo "Unable to find start.jar in lib-dir. Please set LIABOLO_HOME to point to your installation directory."
	exit 1
fi

OPTIONS="-Dexist.home=$LIABOLO_HOME/exist"

# save LANG
if [ -n "$LANG" ]; then
	OLD_LANG="$LANG"
fi
# set LANG to UTF-8
LANG=de_DE.UTF-8

# set java options
if [ -z "$JAVA_OPTIONS" ]; then
    export JAVA_OPTIONS="-Xms16000k -Xmx256000k -Dfile.encoding=UTF-8"
fi

JAVA_ENDORSED_DIRS="$LIABOLO_HOME/exist/lib/endorsed"
JAVA_OPTIONS="$JAVA_OPTIONS -Djava.endorsed.dirs=$JAVA_ENDORSED_DIRS"


if [ "$1" = "start" ] ; then
	$JAVA_HOME/bin/java $JAVA_OPTIONS $OPTIONS -jar "$LIABOLO_HOME/lib/start.jar" standalone $* > $LIABOLO_HOME/lib/pumServer.log &  
elif [ "$1" = "stop" ] ; then
	$JAVA_HOME/bin/java $JAVA_OPTIONS $OPTIONS -jar "$LIABOLO_HOME/lib/start.jar" shutdown $*  
else
	echo "Usage: pumServer.sh ( commands ... )"
  	echo "commands:"
  	echo "start             Start the Library"
  	echo "stop              Stop  the library"
  	exit 1
fi
