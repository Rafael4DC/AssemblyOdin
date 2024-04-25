using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;

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
        // Provide a fallback default value for _logBasePath if the configuration does not contain it
        _logBasePath = logPath;
    }

    protected override async Task ExecuteAsync(CancellationToken stoppingToken)
    {
        try
        {
            while (!stoppingToken.IsCancellationRequested)
            {
                string logg = _logService.GetAllEventLogs();

                // Generate a file name with a timestamp.
                string timestamp = DateTime.Now.ToString("yyyyMMddHHmmss");
                string fileName = $@"{_logBasePath}\log_{timestamp}.txt";

                // Write the log to the file.
                await File.WriteAllTextAsync(fileName, logg);

                // Optionally log that the operation has completed.
                _logger.LogWarning("Log written to {FileName}", fileName);

                await Task.Delay(TimeSpan.FromMinutes(10), stoppingToken);
            }
        }
        catch (OperationCanceledException)
        {
            // When the stopping token is canceled, for example, a call made from services.msc,
            // we shouldn't exit with a non-zero exit code. In other words, this is expected...
        }
        catch (Exception ex)
        {
            _logger.LogError(ex, "{Message}", ex.Message);

            // Terminates this process and returns an exit code to the operating system.
            // This is required to avoid the 'BackgroundServiceExceptionBehavior', which
            // performs one of two scenarios:
            // 1. When set to "Ignore": will do nothing at all, errors cause zombie services.
            // 2. When set to "StopHost": will cleanly stop the host, and log errors.
            //
            // In order for the Windows Service Management system to leverage configured
            // recovery options, we need to terminate the process with a non-zero exit code.
            Environment.Exit(1);
        }
    }
}