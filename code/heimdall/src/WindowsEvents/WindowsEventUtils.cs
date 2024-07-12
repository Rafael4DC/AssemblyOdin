using System.Diagnostics.Eventing.Reader;
using System.Xml;

namespace AssemblyHeimdall.WindowsEvents;

public static class WindowsEventUtils
{
        internal static bool IsAzureAdAccount(string accountName)
        {
            return accountName.StartsWith("AzureAD\\") && accountName.EndsWith("assembly.pt");
        }
        
        internal static string EventToType(string eventId)
        {
            return eventId switch
            {
                "4624" => "Login",
                "4647" => "Logout",
                "4800" => "Lock",
                "4801" => "Unlock",
                _ => $"Unknown, {eventId}"
            };
        }

        internal static Dictionary<string, string> FormatLogEntry(EventRecord record,XmlElement entry)
        {
            var nsmgr = CreateNamespaceManager(entry.OwnerDocument);
            
            string user = entry.SelectSingleNode("//SLog:EventData/SLog:Data[@Name='TargetUserName']", nsmgr)?.InnerText ??
                          entry.SelectSingleNode("//SLog:EventData/SLog:Data[@Name='SubjectUserName']", nsmgr)?.InnerText ?? 
                          "Failed To Get";

            string eventId = entry.SelectSingleNode("//SLog:System/SLog:EventID", nsmgr)?.InnerText ?? "Failed To Get";
            
            return new Dictionary<string, string>
            {
                { "\\", user },
                { "type", EventToType(eventId) },
                { "timestamp", entry.SelectSingleNode("//SLog:System/SLog:TimeCreated/@SystemTime", nsmgr)?.Value ?? "Failed To Get" },
                { "machineName", record.MachineName ?? "Failed To Get" },
                { "LogonType", entry.SelectSingleNode("//SLog:EventData/SLog:Data[@Name='LogonType']", nsmgr)?.InnerText ?? "Failed To Get"},
                { "LogonProcessName", entry.SelectSingleNode("//SLog:EventData/SLog:Data[@Name='LogonProcessName']", nsmgr)?.InnerText.Trim() ?? "Failed To Get"}
            };
        }
        
        internal static Dictionary<string, string>CleanInformation(this Dictionary<string, string> entry)
        {
            entry.Remove("LogonType");
            entry.Remove("LogonProcessName");
            return entry;
        }
        
        

        internal static XmlNamespaceManager CreateNamespaceManager(XmlDocument xmlDoc)
        {
            XmlNamespaceManager nsmgr = new XmlNamespaceManager(xmlDoc.NameTable);
            nsmgr.AddNamespace("SLog", "http://schemas.microsoft.com/win/2004/08/events/event");
            return nsmgr;
        }

        internal static XmlDocument LoadXmlDocument(string xml)
        {
            XmlDocument xmlDoc = new XmlDocument();
            xmlDoc.LoadXml(xml);
            return xmlDoc;
        }

        internal static void LogError(string message, Exception ex)
        {
            Console.WriteLine($"{message}: {ex.Message}");
        }
        
        /*internal static void cleanForStorage(Dictionary<string,string> message, List<string> keysToKeep)
        {
            foreach (var key in keysToRemove)
            {
                dict.Remove(key);
            }
        }*/
}