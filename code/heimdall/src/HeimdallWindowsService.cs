using System.Diagnostics.Eventing.Reader;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using System.IO;
using System.Threading.Tasks;
using System.Threading;

namespace AssemblyHeimdall;

public sealed class HeimdallWindowsService : BackgroundService
{
    private readonly LogService _logService;
    private readonly ILogger<HeimdallWindowsService> _logger;
    private readonly string _logBasePath;

    public HeimdallWindowsService(LogService logService, ILogger<HeimdallWindowsService> logger, string logPath)
    {
        _logService = logService;
        _logger = logger;
        _logBasePath = logPath;
    }

    protected override async Task ExecuteAsync(CancellationToken stoppingToken)
    {
        
        try
        {
                // On service start, retrieve the latest 4647 and the first 4672 after it with an Azure AD account
                string initialLog = _logService.GetAzureEvents();
                _logger.LogInformation("initial log done");
                await WriteLogToFile(initialLog, "initial");

                // Periodically retrieve events every 10 minutes
                while (!stoppingToken.IsCancellationRequested)
                {
                    await Task.Delay(TimeSpan.FromMinutes(10), stoppingToken);
                    // Get screen and session events with the specified logic
                    string periodicLog = _logService.GetRecentEvents();
                    if (periodicLog.Length == 0) periodicLog = "Failed for some reason";
                    // Check if there is any log content to write
                    await WriteLogToFile(periodicLog, "periodic");
                    _logger.LogInformation("Periodic log done");
                }
            
        }
        catch (OperationCanceledException)
        {
            _logger.LogInformation("Operation was cancelled");
        }
        catch (Exception ex)
        {
            _logger.LogError(ex, "Error in HeimdallWindowsService: {Message}", ex.Message);
            Environment.Exit(1);
        }
    }

    // Helper method to write logs to a file
    private async Task WriteLogToFile(string logContent, string logType)
    {
        string timestamp = DateTime.Now.ToString("yyyyMMddHHmmss");
        string fileName = Path.Combine(_logBasePath, $"{logType}_log_{timestamp}.txt");

        await File.WriteAllTextAsync(fileName, logContent);
        _logger.LogInformation("Log written to {FileName} at {LogBasePath}", fileName, _logBasePath);
    }
}
