
## first rename the jar files

BUILD_JAR_HOME="$HOME/general store/general_store_backend/buildJars"

## remove .prev jar file
cd "$BUILD_JAR_HOME"
rm product-service.jar.prev

echo "Removed the prev jar file"

## rename existing to prev
mv product-service.jar product-service.jar.prev
echo "Renamed existing jar to prev jar file"


## remove the build folders from common and product-service

GENERAL_STORE="$HOME/general store/general_store_backend"
PRODUCT_SERVICE="$GENERAL_STORE/product-service"
COMMON="$GENERAL_STORE/common"

rm -r "$COMMON"/build
echo "Deleted the build for common"
rm -r "$PRODUCT_SERVICE"/build
echo "Deleted the build for product-service"

## build the jar files
cd "$PRODUCT_SERVICE"

gradle build -x test

## copy the jar file to buildJars

cd "$BUILD_JAR_HOME"
cp "$PRODUCT_SERVICE"/build/libs/product-service-0.0.1-SNAPSHOT.jar product-service.jar

ls -l

echo "copied the jar file to buildJars"

scp product-service.jar prod-kasera-0:.