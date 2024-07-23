namespace AssemblyHeimdall.Repo;

public class FileRepo : IStoreRepo
{
    private string LogPath { get; set; }
    
    public FileRepo(string logPath)
    {
        LogPath = logPath;
    }
    public void Store(string logContent)
    {
        string timestamp = DateTime.Now.ToString("yyyyMMddHHmmss");
        string fileName = Path.Combine(LogPath, $"log_{timestamp}.txt");

        File.WriteAllTextAsync(fileName, logContent);
    }
}