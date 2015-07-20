#!/bin/sh
echo "Finding .java files"
find -iname "*.java" > javafiles.txt
cd libs
echo "Checking libs/ for libraries"
find -iname "*.jar" > ../libs.txt
cd ..
libs=" -classpath ."
while read val ; do
        libs="$libs:libs/$val"
done < libs.txt
if [ "$libs" = " -classpath ." ]; then
	libs=""
fi
echo "Compiling java files"
javac$libs @javafiles.txt
echo "Finishing up"
rm javafiles.txt
rm libs.txt
echo "Finished. All files have been compiled. Check log for any errors"
