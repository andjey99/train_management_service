# Quick run
cd $PSScriptRoot

# Clean
if (Test-Path out) { rm out -Recurse -Force }
mkdir out | Out-Null

# Compile
Write-Host "Compiling..." -f Gray
$files = (dir src\main\java -Recurse *.java).FullName
javac -cp "lib/*" -d out -encoding UTF-8 $files

# Run if compile ok
if ($LASTEXITCODE -eq 0) {
    $msg = "LAUNCHING"
    $colors = @("Red", "Yellow", "Green", "Cyan", "Blue", "Magenta")
    $i = 0
    foreach ($char in $msg.ToCharArray()) {
        Write-Host "$char" -NoNewline -f $colors[$i % $colors.Count]
        $i++
        Start-Sleep -Milliseconds 100
    }
    Write-Host ""
    
    java -cp "out;lib/*" com.example.train.Main
}
