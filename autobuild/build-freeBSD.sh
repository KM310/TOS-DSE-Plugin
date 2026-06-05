#!/bin/sh

echo "=== Building Plugin (FreeBSD / POSIX sh) ==="

chmod +x ./gradlew
./gradlew build

echo
echo "=== Build finished ==="
