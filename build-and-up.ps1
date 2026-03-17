<#
  build-and-up.ps1
  - Ensure you have JDK installed and accessible via javac
  - Builds the project with the Maven wrapper and then runs docker compose

  Usage: Open a NEW PowerShell window after installing JDK and setting JAVA_HOME,
  then run (from repo root):
    .\build-and-up.ps1

#>

function Throw-Error {
    param($msg)
    Write-Host "ERROR: $msg" -ForegroundColor Red
    exit 1
}

Write-Host "Checking for javac..."
$javac = (& where.exe javac) 2>$null
if (-not $javac) {
    Throw-Error 'javac not found in PATH. Install JDK 21 and set JAVA_HOME and PATH, then reopen PowerShell.'
}

Write-Host "javac found: $javac"

Write-Host "Building project with Maven wrapper (skipping tests)..."
if (-not (Test-Path '.\mvnw.cmd')) {
    Throw-Error "Maven wrapper (mvnw.cmd) not found in project root. Run mvn package locally or add mvnw to repo."
}

$build = Start-Process -FilePath '.\mvnw.cmd' -ArgumentList '-DskipTests','clean','package' -NoNewWindow -Wait -PassThru
if ($build.ExitCode -ne 0) {
    Throw-Error "Maven build failed with exit code $($build.ExitCode). Check output above."
}

Write-Host "Maven build completed. Looking for jar in target/"
$jars = Get-ChildItem -Path .\target -Filter *.jar -File -ErrorAction SilentlyContinue
if (-not $jars) {
    Throw-Error "No jar found in target/. Build may have failed."
}

Write-Host "Found jars:" -ForegroundColor Green
$jars | ForEach-Object { Write-Host $_.FullName }

Write-Host "Building and starting containers with Docker Compose..."
docker compose -f .\compose.yaml -p duriu-open up --build -d
$exitCode = $LASTEXITCODE
if ($exitCode -ne 0) {
    Throw-Error "docker compose up failed with exit code $exitCode"
}

Write-Host "Started containers. Tailing app logs (Press Ctrl+C to stop)..."
docker compose -f .\compose.yaml -p duriu-open logs -f app

