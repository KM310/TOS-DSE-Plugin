#!/bin/bash
#für linux und macOS

echo "=== Building Plugin (Linux/macOS) ==="

# Gradle Wrapper ausführbar machen
chmod +x ./gradlew

# Build starten
./gradlew build

echo
echo "=== Build finished ==="
