Write-Host "=== Building Plugin (PowerShell) ==="

# Gradle Wrapper ausführbar machen
if (Test-Path "./gradlew") {
    chmod +x ./gradlew
}

./gradlew build

Write-Host "`n=== Build finished ==="
