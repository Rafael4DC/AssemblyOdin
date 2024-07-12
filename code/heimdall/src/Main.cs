using System.Diagnostics;
using System.Diagnostics.Eventing.Reader;
using AssemblyHeimdall.Repo;
using AssemblyHeimdall.WindowsEvents;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using MongoDB.Bson;
using MongoDB.Driver;

namespace AssemblyHeimdall;

public sealed class Main : BackgroundService
{
    private readonly WindowsEventService _windowsEventService;
    private readonly ILogger<Main> _logger;
    private readonly IStoreRepo _storeRepo;
    private readonly EventLog _eventLog;
    private const string EventLogSource = "Assembly Heimdall";
    private const string EventLogName = "Application";

    public Main(WindowsEventService windowsEventService, ILogger<Main> logger, IStoreRepo storeRepo)
    {
        _windowsEventService = windowsEventService;
        _logger = logger;
        _storeRepo = storeRepo;
        EnsureEventLogSourceExists(EventLogSource, EventLogName);

        _eventLog = new EventLog
        {
            Source = EventLogSource,
            Log = EventLogName
        };
    }
    public static void EnsureEventLogSourceExists(string source, string logName)
    {
        if (!EventLog.SourceExists(source))
        {
            EventLog.CreateEventSource(source, logName);
        }
    }
    
    
    protected override async Task ExecuteAsync(CancellationToken stoppingToken)
    {
        try
        {
            
            string initialLog = _windowsEventService.GetLogonEventsOnStartup();
            _eventLog.WriteEntry($"Initial log done {initialLog}", EventLogEntryType.Information);
            _storeRepo.Store(initialLog);

            
            while (!stoppingToken.IsCancellationRequested)
            {
                _eventLog.WriteEntry("Waiting for the next interval...", EventLogEntryType.Information);
                await Task.Delay(TimeSpan.FromMinutes(3), stoppingToken);
                _eventLog.WriteEntry("Stopped for the next interval...", EventLogEntryType.Information);
                string periodicLog = _windowsEventService.GetRecentEvents(_eventLog);
                if (periodicLog.Length == 0) periodicLog = "Failed for some reason";
                _storeRepo.Store(periodicLog);
                _eventLog.WriteEntry($"Ended {periodicLog}", EventLogEntryType.Information);
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
}
