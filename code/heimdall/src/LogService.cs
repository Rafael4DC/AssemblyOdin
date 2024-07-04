using System;
using System.Collections.Generic;
using System.Diagnostics.Eventing.Reader;
using System.Text.Json;
using System.Xml;

namespace AssemblyHeimdall
{
    public sealed class LogService
    {
        private readonly long[] _logonLogoffEventIds = { 4647, 4672 };
        private readonly long[] _screenEventIds = { 4800, 4801 };

        // Method to get the latest 4647 event and the first 4672 event after that with Azure AD account
        public string GetAzureEvents()
        {
            List<Dictionary<string, string>> results = new List<Dictionary<string, string>>();
            
            EventLogQuery logQuery = new EventLogQuery("Security", PathType.LogName,
                $"*[System/EventID=4647 or System/EventID=4672 or System/EventID=4648]") { ReverseDirection = true };

            using (EventLogReader reader = new EventLogReader(logQuery))
            {
                EventRecord record;
                Dictionary<string, string>? latest4667 = null;
                Dictionary<string, string>? latest4672 = null;
                EventRecord? latest4672Record = null;
                
                
                while ((record = reader.ReadEvent()) != null)
                {
                    try
                    {
                        switch (record.Id)
                        {
                            case 4672:
                                latest4672Record = record;
                                break;
                            
                            case 4648:
                                XmlElement Xml4672 = LoadXmlDocument(latest4672Record.ToXml()).DocumentElement;
                                Dictionary<string, string> logEntry4672 = FormatLogEntry(Xml4672);
                                if (IsAzureAdAccount(logEntry4672["User"].Trim()))
                                    latest4672 = logEntry4672;
                                break;
                            
                            case 4647:
                                XmlElement Xml4667 = LoadXmlDocument(latest4672Record.ToXml()).DocumentElement;
                                Dictionary<string, string> logEntry4667 = FormatLogEntry(Xml4667);
                                latest4667 = logEntry4667;
                                break;
                        }
                    }
                    catch (Exception ex)
                    {
                        LogError("Failed to process Event ID 4647", ex);
                    }

                    if (latest4667 != null)
                        break;
                }

                results.Add(latest4667);
                results.Add(latest4672);
            }
            return JsonSerializer.Serialize(results);
        }

        // Method to retrieve recent events every ten minutes
        public string GetRecentEvents()
        {
            List<Dictionary<string, string>> logsList = new List<Dictionary<string, string>>();
            DateTime tenMinutesAgo = DateTime.Now.AddMinutes(-10);
            string queryStr = $"*[System/TimeCreated[@SystemTime >= '{tenMinutesAgo:O}'] and (System/EventID=4800 or System/EventID=4801 or System/EventID=4647 or System/EventID=4672)]";
            EventLogQuery logQuery = new EventLogQuery("Security", PathType.LogName, queryStr);

            using (EventLogReader reader = new EventLogReader(logQuery))
            {
                EventRecord record;
                while ((record = reader.ReadEvent()) != null)
                {
                    try
                    {
                        XmlElement entryXml = LoadXmlDocument(record.ToXml()).DocumentElement;
                        Dictionary<string, string> logEntry = FormatLogEntry(entryXml);
                        if (record.Id == 4672 && !IsAzureAdAccount(logEntry["User"].Trim()))
                            continue;
                        logsList.Add(logEntry);
                    }
                    catch (Exception ex)
                    {
                        LogError("Failed to process recent events", ex);
                    }
                }
            }
            return JsonSerializer.Serialize(logsList);
        }

        private static bool IsAzureAdAccount(string accountName)
        {
            return accountName.StartsWith("AzureAD\\") && accountName.EndsWith("assembly.pt");
        }

        private static Dictionary<string, string> FormatLogEntry(XmlElement entry)
        {
            
            var nsmgr = CreateNamespaceManager(entry.OwnerDocument);
            // Correct XPath to select either 'SubjectUserName' or 'TargetUserName'
            string user = entry.SelectSingleNode("//SLog:EventData/SLog:Data[@Name='SubjectUserName']", nsmgr)?.InnerText ??
                          entry.SelectSingleNode("//SLog:EventData/SLog:Data[@Name='TargetUserName']", nsmgr)?.InnerText ?? "Failed To Get";

            // Correct XPath to select the 'DomainName' value
            string domain = entry.SelectSingleNode("//SLog:EventData/SLog:Data[@Name='SubjectDomainName']", nsmgr)?.InnerText ?? 
                            entry.SelectSingleNode("//SLog:EventData/SLog:Data[@Name='TargetDomainName']", nsmgr)?.InnerText ?? "Failed To Get";

            return new Dictionary<string, string>
            {
                { "Time", entry.SelectSingleNode("//SLog:TimeCreated/@SystemTime", nsmgr)?.Value ?? "Failed To Get" },
                { "Event ID", entry.SelectSingleNode("//SLog:EventID", nsmgr)?.InnerText ?? "Failed To Get" },
                { "User", user },
                { "Account Domain", domain }
            };
        }

        private static XmlNamespaceManager CreateNamespaceManager(XmlDocument xmlDoc)
        {
            XmlNamespaceManager nsmgr = new XmlNamespaceManager(xmlDoc.NameTable);
            nsmgr.AddNamespace("SLog", "http://schemas.microsoft.com/win/2004/08/events/event");
            return nsmgr;
        }

        private static XmlDocument LoadXmlDocument(string xml)
        {
            XmlDocument xmlDoc = new XmlDocument();
            xmlDoc.LoadXml(xml);
            return xmlDoc;
        }

        private void LogError(string message, Exception ex)
        {
            // Assuming there's a logger implemented; use it to log errors
            Console.WriteLine($"{message}: {ex.Message}");
            // Additional logging details can be implemented as needed
        }
    }
}
