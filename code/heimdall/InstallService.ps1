# PowerShell script to install a Windows service and create a log folder

# PowerShell script to install a Windows service and create a log folder

param(
    [string]$baseDirectory = "E:\Code Snippets\AssemblyOdin\code", # Pass the base directory of your service and logs
    [string]$exeRelativePath = "heimdall\bin\Release\net8.0\win-x64\publish\AssemblyHeimdall.exe", # Relative path from the base directory to the executable
    [string]$logRelativePath = "ConsoleApp1\bin\Release\net8.0\win-x64\publish\Logs" # Relative path from the base directory to the log folder
)

# Resolve the full paths
$exePath = Join-Path $baseDirectory $exeRelativePath
$logFolderPath = Join-Path $baseDirectory $logRelativePath

# Define the service name
$serviceName = "Assembly Heimdall"

# Check if the service is already installed
if (Get-Service -Name $serviceName -ErrorAction SilentlyContinue) {
    Write-Host "Service $serviceName is already installed."
} else {
    # Create the log folder if it doesn't exist
    if (-not (Test-Path -Path $logFolderPath)) {
        New-Item -ItemType Directory -Path $logFolderPath
        Write-Host "Created log folder at $logFolderPath"
    } else {
        Write-Host "Log folder already exists at $logFolderPath"
    }

    # Install the service using sc.exe
    $service = sc.exe create $serviceName binPath= $exePath start= auto
    if ($service -match "SUCCESS") {
        Write-Host "Service $serviceName installed successfully."
    } else {
        Write-Host "Failed to install service $serviceName. Error: $service"
        exit
    }

    # Start the service
    Start-Service -Name $serviceName
    if ((Get-Service -Name $serviceName).Status -eq "Running") {
        Write-Host "Service $serviceName started successfully."
    } else {
        Write-Host "Failed to start service $serviceName."
    }
}
