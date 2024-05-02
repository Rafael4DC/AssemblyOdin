using System.Diagnostics;
using System.Text.RegularExpressions;
using System.Text.Json;


namespace AssemblyHeimdall
{
    public sealed class LogService
    {
        private readonly long[] _logonLogoffEventIds = { 4624, 4634 };
        private readonly int[] _desiredLogonTypes = { 2, 3 }; // Example logon types; update with actual types you're interested in

        public string GetAllEventLogs(int maxEvents = 10)
        {
            List<Dictionary<string, string>> logsList = new List<Dictionary<string, string>>();

            using (EventLog securityLog = new EventLog("Security"))
            {
                var entries = securityLog.Entries.Cast<EventLogEntry>()
                    .Where(entry => _logonLogoffEventIds.Contains(entry.InstanceId))
                    .Where(entry => _desiredLogonTypes.Contains(int.Parse(ExtractDetails(entry.Message).logonType)))
                    .OrderByDescending(entry => entry.TimeGenerated)
                    .Take(maxEvents);

                foreach (EventLogEntry entry in entries)
                {
                    var details = ExtractDetails(entry.Message);
                    if (_desiredLogonTypes.Contains(int.Parse(details.logonType)))
                    {
                        logsList.Add(new Dictionary<string, string>
                        {
                            { "Time", entry.TimeGenerated.ToString("o") }, // "o" for ISO 8601 format
                            { "Event ID", entry.InstanceId.ToString() },
                            { "User", entry.UserName },
                            { "Account Name", details.accountName },
                            { "Account Domain", details.domain },
                            { "Logon Type", details.logonType }
                        });
                    }
                }
            }
            return JsonSerializer.Serialize(logsList);
        }

        private (string accountName, string domain, string logonType) ExtractDetails(string message)
        {
            var accountName = Regex.Match(message, @"Account Name:\s+([^\r\n]+)").Groups[1].Value.Trim();
            var domain = Regex.Match(message, @"Account Domain:\s+([^\r\n]+)").Groups[1].Value.Trim();
            var logonType = Regex.Match(message, @"Logon Type:\s+([^\r\n]+)").Groups[1].Value.Trim();

            return (accountName, domain, logonType);
        }
    }
}
