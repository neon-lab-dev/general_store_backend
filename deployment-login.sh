
## first rename the jar files

BUILD_JAR_HOME="$HOME/general store/general_store_backend/buildJars"

## remove .prev jar file
cd "$BUILD_JAR_HOME"
rm login-service.jar.prev

echo "Removed the prev jar file"

## rename existing to prev
mv login-service.jar login-service.jar.prev
echo "Renamed existing jar to prev jar file"


## remove the build folders from common and login-service

GENERAL_STORE="$HOME/general store/general_store_backend"
LOGIN_SERVICE="$GENERAL_STORE/login-service"
COMMON="$GENERAL_STORE/common"

rm -r "$COMMON"/build
echo "Deleted the build for common"
rm -r "$LOGIN_SERVICE"/build
echo "Deleted the build for login-service"

## build the jar files
cd "$LOGIN_SERVICE"

gradle build -x test

## copy the jar file to buildJars

cd "$BUILD_JAR_HOME"
cp "$LOGIN_SERVICE"/build/libs/login-service-0.0.1-SNAPSHOT.jar login-service.jar

ls -l

echo "copied the jar file to buildJars"

scp login-service.jar dev:.
