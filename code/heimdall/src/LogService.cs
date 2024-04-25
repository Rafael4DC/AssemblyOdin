using System.Diagnostics;
using System.Text;
using System.Linq;
using System.Text.RegularExpressions;

namespace AssemblyHeimdall
{
    public sealed class LogService
    {
        private readonly long[] _logonLogoffEventIds = { 4624, 4634 };
        private readonly int[] _desiredLogonTypes = { 2, 3 }; // Example logon types; update with actual types you're interested in

        public string GetAllEventLogs(int maxEvents = 1000)
        {
            StringBuilder eventLogInfo = new StringBuilder();

            using (EventLog securityLog = new EventLog("Security"))
            {
                var entries = securityLog.Entries.Cast<EventLogEntry>()
                    .Where(entry => _logonLogoffEventIds.Contains(entry.InstanceId))
                    .OrderByDescending(entry => entry.TimeGenerated)
                    .Take(maxEvents);

                foreach (EventLogEntry entry in entries)
                {
                    var details = ExtractDetails(entry.Message);
                    // Check if the logon type is one of the desired types before appending
                    if (_desiredLogonTypes.Contains(int.Parse(details.logonType)))
                    {
                        eventLogInfo.AppendLine($"Time: {entry.TimeGenerated}, Event ID: {entry.InstanceId}, User: {entry.UserName}, Account Name: {details.accountName}, Account Domain: {details.domain}, Logon Type: {details.logonType}");
                    }
                }
            }

            return eventLogInfo.ToString();
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
