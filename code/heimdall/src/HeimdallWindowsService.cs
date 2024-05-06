using System.Diagnostics.Eventing.Reader;
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
        _logBasePath = logPath;
    }

    protected override async Task ExecuteAsync(CancellationToken stoppingToken)
    {
        try
        {
            while (!stoppingToken.IsCancellationRequested)
            {
                string logg = _logService.GetAllEventLogs();
                
                if(logg.Length != 0){

                    string timestamp = DateTime.Now.ToString("yyyyMMddHHmmss");
                    string fileName = $@"{_logBasePath}\log_{timestamp}.txt";

                    await File.WriteAllTextAsync(fileName, logg);

                    _logger.LogWarning("Log written to {FileName} at {LogBasePath}", fileName,_logBasePath);

                    await Task.Delay(TimeSpan.FromMinutes(10), stoppingToken);
                }
            }
        }
        catch (OperationCanceledException)
        {
            
        }
        catch (Exception ex)
        {
            _logger.LogError(ex, "{Message}", ex.Message);
            Environment.Exit(1);
        }
    }
}