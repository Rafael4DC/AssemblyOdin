using System.Diagnostics;
using System.Diagnostics.Eventing.Reader;
using System.Text.Json;
using System.Xml;

namespace AssemblyHeimdall.WindowsEvents
{
    public sealed class WindowsEventService
    {
        public string GetLogonEventsOnStartup()
        {
            List<Dictionary<string, string>> results = new List<Dictionary<string, string>>();

            EventLogQuery logQuery = new EventLogQuery("Security", PathType.LogName,
                $"*[System/EventID=4647 or System/EventID=4624]") { ReverseDirection = true };

            using (EventLogReader reader = new EventLogReader(logQuery))
            {
                EventRecord record;
                Dictionary<string, string>? latestLogoff = null;
                Dictionary<string, string>? latestLogon = null;

                while ((record = reader.ReadEvent()) != null)
                {
                    try
                    {
                        XmlElement entryXml = WindowsEventUtils.LoadXmlDocument(record.ToXml()).DocumentElement;
                        Dictionary<string, string> logEntry = WindowsEventUtils.FormatLogEntry(record,entryXml);
                            
                        if (record.Id == 4624 && logEntry["LogonProcessName"] == "User32")
                        {
                            latestLogon = logEntry;
                        }

                        if (record.Id == 4647)
                        {
                            latestLogoff = logEntry;
                            break;
                        }
                    }
                    catch (Exception ex)
                    {
                        WindowsEventUtils.LogError("Failed to process Event ID", ex);
                    }
                }

                if (latestLogoff != null)
                {
                    latestLogoff.CleanInformation();
                    results.Add(latestLogoff);
                }

                if (latestLogon != null)
                {
                    latestLogon.CleanInformation();
                    results.Add(latestLogon);
                }
            }

            return JsonSerializer.Serialize(results);
        }

        
        public string GetRecentEvents(EventLog eventLog)
        {
            List<Dictionary<string, string>> results = new List<Dictionary<string, string>>();
            DateTime tenMinutesAgo = DateTime.Now.AddMinutes(-3);
            EventLogQuery logQuery = new EventLogQuery("Security", PathType.LogName,
                $"*[System/EventID=4624 or System/EventID=4647 or System/EventID=4800]") { ReverseDirection = true };
            
            
            using (EventLogReader reader = new EventLogReader(logQuery))
            {
                EventRecord record;
                while ((record = reader.ReadEvent()) != null)
                {
                    try
                    {
                        XmlElement entryXml = WindowsEventUtils.LoadXmlDocument(record.ToXml()).DocumentElement ?? throw new Exception();
                        Dictionary<string, string> logEntry = WindowsEventUtils.FormatLogEntry(record,entryXml);
                        if (!(logEntry["LogonProcessName"] == "User32" || logEntry["LogonProcessName"] == "Failed To Get")) continue;
                        
                        if (DateTime.TryParse(logEntry["timestamp"], out DateTime eventTime) && eventTime >= tenMinutesAgo)
                        {
                            eventLog.WriteEntry("ValidLog!", EventLogEntryType.Information);
                            logEntry.CleanInformation();
                            results.Add(logEntry);
                        }
                        else
                        {
                            break;
                        }
                        
                    }
                    catch (Exception ex)
                    {
                        eventLog.WriteEntry("Failed", EventLogEntryType.Information);
                        WindowsEventUtils.LogError("Failed to process recent events", ex);
                    }
                }
            }

            results.Reverse();
            return JsonSerializer.Serialize(results);
        }
    }
}
