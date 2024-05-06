using System.Diagnostics;
using System.Text.RegularExpressions;
using System.Text.Json;


namespace AssemblyHeimdall
{
    public sealed partial class LogService
    {
        private readonly long[] _logonLogoffEventIds = [4624, 4634]; //4647 = Logoff para azure
        private readonly int[] _desiredLogonTypes = [2, 3]; // Example logon types; update with actual types you're interested in

        public string GetAllEventLogs(int maxEvents = 10)
        {
            List<Dictionary<string, string>> logsList = new List<Dictionary<string, string>>();

            using (EventLog securityLog = new EventLog("Security"))
            {
                var entries = securityLog.Entries.Cast<EventLogEntry>()
                    //.Where(entry => _logonLogoffEventIds.Contains(entry.InstanceId))
                    //.Where(entry => _desiredLogonTypes.Contains(int.Parse(ExtractDetails(entry.Message).logonType)))
                    .OrderByDescending(entry => entry.TimeGenerated)
                    .Take(maxEvents);

                logsList.AddRange(from entry in entries
                    let details = ExtractDetails(entry.Message)
                    //where _desiredLogonTypes.Contains(int.Parse(details.logonType))
                    select new Dictionary<string, string>
                    {
                        { "Time", entry.TimeGenerated.ToString("o") }, // "o" for ISO 8601 format
                        { "Event ID", entry.InstanceId.ToString() },
                        { "User", entry.UserName },
                        { "Account Name", details.accountName },
                        { "Account Domain", details.domain },
                        { "Logon Type", details.logonType }
                    });
            }
            return JsonSerializer.Serialize(logsList);
        }

        private static (string accountName, string domain, string logonType) ExtractDetails(string message)
        {
            var accountName = MyRegex().Match(message).Groups[1].Value.Trim();
            var domain = MyRegex1().Match(message).Groups[1].Value.Trim();
            var logonType = MyRegex2().Match(message).Groups[1].Value.Trim();

            return (accountName, domain, logonType);
        }

        [GeneratedRegex(@"Account Name:\s+([^\r\n]+)")]
        private static partial Regex MyRegex();
        
        [GeneratedRegex(@"Account Domain:\s+([^\r\n]+)")]
        private static partial Regex MyRegex1();
        
        [GeneratedRegex(@"Logon Type:\s+([^\r\n]+)")]
        private static partial Regex MyRegex2();
    }
}
