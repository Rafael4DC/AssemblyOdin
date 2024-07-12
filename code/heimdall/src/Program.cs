using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging.EventLog;
using AssemblyHeimdall;
using AssemblyHeimdall.Repo;
using AssemblyHeimdall.WindowsEvents;

var builder = Host.CreateDefaultBuilder(args);

builder.ConfigureServices((hostContext, services) =>
    {
        
        IStoreRepo repo;
        if (false)
        {
            string logDirectory = Directory.GetCurrentDirectory()+"\\AHeimdall\\Logs";
            if (!Directory.Exists(logDirectory))
            {
                Directory.CreateDirectory(logDirectory);
            }
                    
            repo = new FileRepo(logDirectory);
        }
        else
        {
            string mongoUri = "mongodb+srv://DragoTeste:DragoTeste@testecluster.51fod3y.mongodb.net/?retryWrites=true&w=majority&appName=TesteCluster";
            repo = new MongoRepo(mongoUri,"logsDatabase","unprocessed");
        }
        
        
        services.AddWindowsService(options =>
        {
            options.ServiceName = "Assembly Heimdall";
        });
        services.AddSingleton(new WindowsEventService());
        services.AddSingleton(repo); 
        services.AddHostedService<Main>();
        
        
        services.Configure<EventLogSettings>(config =>
        {
            config.LogName = "Application";
            config.SourceName = "Assembly Heimdall";
        });
    })
    .UseWindowsService();

var host = builder.Build();
host.Run();