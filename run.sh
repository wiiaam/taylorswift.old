#!/bin/sh
cd libs
find -iname "*.jar" > ../libs.txt
cd ..
libs=" -classpath ."
while read val ; do
        libs="$libs:libs/$val"
done < libs.txt
if [ "$libs" = " -classpath ." ]; then
	libs=""
fi
java$libs PersonalBot
