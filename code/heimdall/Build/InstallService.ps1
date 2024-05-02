# PowerShell script to install a Windows service in the System32 directory and copy necessary files


param(
    [string]$baseDirectory = "C:\Windows\System32\AHeimdall", # Service installation directory in System32
    [string]$exeRelativePath = "AssemblyHeimdall.exe" # Executable name
)

# Determine the source directory based on the script's current location
$scriptPath = $MyInvocation.MyCommand.Path
$sourceDirectory = Split-Path -Path $scriptPath

# Resolve the full path to the executable in the destination directory
$exePath = Join-Path $baseDirectory $exeRelativePath

# Define the service name
$serviceName = "Assembly Heimdall"

# Ensure the installation directory exists
if (-not (Test-Path -Path $baseDirectory)) {
    New-Item -ItemType Directory -Path $baseDirectory
    Write-Host "Created service directory at $baseDirectory"
} else {
    Write-Host "Service directory already exists at $baseDirectory"
}

# Copy all contents from the source directory to the base directory, excluding the script itself
Get-ChildItem -Path $sourceDirectory -Recurse -Exclude $scriptPath | Copy-Item -Destination $baseDirectory -Recurse -Force

# Check if the service is already installed
if (Get-Service -Name $serviceName -ErrorAction SilentlyContinue) {
    Write-Host "Service $serviceName is already installed."
} else {
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
