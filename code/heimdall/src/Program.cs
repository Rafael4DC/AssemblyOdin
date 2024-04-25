using System.Diagnostics;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging.EventLog;
using AssemblyHeimdall;
using Microsoft.Extensions.Configuration;
using System.IO;

var builder = Host.CreateDefaultBuilder(args);

builder.ConfigureAppConfiguration((hostContext, config) =>
    {
        var basePath = Path.GetFullPath(@"..\..\..\..\", Directory.GetCurrentDirectory());
        var appSettingsPath = Path.Combine(basePath, "appsettings.json");

        config.AddJsonFile(appSettingsPath, optional: true, reloadOnChange: true);

    })
    .ConfigureServices((hostContext, services) =>
    {
        var configuration = hostContext.Configuration;
        var projectSettings = configuration.GetSection("ProjectSettings").Get<ProjectSettings>();

        // Ensure the log directory exists
        string logDirectory = projectSettings?.LogFolder ?? throw new InvalidOperationException("Log folder path must be set in project settings.");
        if (!Directory.Exists(logDirectory))
        {
            Directory.CreateDirectory(logDirectory);
        }

        services.AddWindowsService(options =>
        {
            options.ServiceName = "Assembly Heimdall";
        });
        services.AddSingleton(new LogService()); // Assuming LogService takes a logDirectory parameter
        services.AddSingleton(logDirectory); 
        services.AddHostedService<HeimdallWindowsService>();
        
        // Configure logging to the event log
        services.Configure<EventLogSettings>(config =>
        {
            config.LogName = "Application";
            config.SourceName = "Assembly Heimdall";
        });
    })
    .UseWindowsService();

var host = builder.Build();
host.Run();

public class ProjectSettings
{
    public string? ProjectLocation { get; set; }
    public string? InstallLocation { get; set; }
    public string? LogFolder { get; set; }
}