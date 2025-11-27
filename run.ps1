<#
.SYNOPSIS
    Compiles and runs the Train Management System.

.DESCRIPTION
    This script cleans the output directory, compiles all Java source files,
    and launches the main application. It automatically includes the MySQL
    connector from the 'lib' directory.

.EXAMPLE
    ./run.ps1
#>

$ErrorActionPreference = "Stop"

# Ensure we are in the script's directory
Set-Location $PSScriptRoot

Write-Host "Starting Train Management System Build..." -ForegroundColor Cyan

# Check for library
if (-not (Test-Path "lib")) 
{
    Write-Error "Library directory isnt working! Please ensure 'mysql-connector-j.jar' is in a 'lib' folder."
    exit 1
}

# Clean and recreate output directory
Write-Host "Cleaning the output directory..." -ForegroundColor Yellow
if (Test-Path "out") 
{
    Remove-Item -Path "out" -Recurse -Force
}
New-Item -ItemType Directory -Path "out" | Out-Null

# Find all Java files
$javaFiles = Get-ChildItem -Path "src\main\java" -Recurse -Filter *.java
if ($javaFiles.Count -eq 0)
 {
    Write-Error "No Java files found in src\main\java"
    exit 1
}

# Compile
Write-Host "Compiling the source files..." -ForegroundColor Yellow

# Use relative paths and forward slashes for classpath
$classpath = "lib/*"
$javacArgs = @("-cp", $classpath, "-d", "out", "-encoding", "UTF-8") + $javaFiles.FullName

try 
{
    & javac $javacArgs
    Write-Host "Compilation successful!" -ForegroundColor Green
}
catch 
{
    Write-Error "Compilation failed!"
    exit 1
}

# Run
Write-Host "Running the application..." -ForegroundColor Cyan

$runClasspath = "out;lib/*"

try 
{
    & java -cp $runClasspath com.example.train.Main
}
catch 
{
    Write-Error "Application crashed or failed to start. Sorry"
    exit 1
}
