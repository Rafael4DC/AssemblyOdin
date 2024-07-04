using System.Diagnostics;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging.EventLog;
using AssemblyHeimdall;
using Microsoft.Extensions.Configuration;
using System.IO;

var builder = Host.CreateDefaultBuilder(args);

builder.ConfigureServices((hostContext, services) =>
    {
        // Ensure the log directory exists
        string logDirectory = Directory.GetCurrentDirectory()+"\\AHeimdall\\Logs"; //this is kinda sussy
        if (!Directory.Exists(logDirectory))
        {
            Directory.CreateDirectory(logDirectory);
        }

        services.AddWindowsService(options =>
        {
            options.ServiceName = "Assembly Heimdall";
        });
        services.AddSingleton(new LogService());
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