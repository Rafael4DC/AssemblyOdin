namespace AssemblyHeimdall;

using MongoDB.Driver;
using MongoDB.Bson;

public class MongoRepo
{

    const string connectionUri = "mongodb+srv://DragoTeste:DragoTeste@testecluster.51fod3y.mongodb.net/?retryWrites=true&w=majority&appName=TesteCluster";

    public static void doThings()
    {
        MongoClientSettings settings = MongoClientSettings.FromConnectionString(connectionUri);
        
        settings.ServerApi = new ServerApi(ServerApiVersion.V1);
        
        var client = new MongoClient(settings);

        
        try {
            var result = client.GetDatabase("admin").RunCommand<BsonDocument>(new BsonDocument("ping", 1));
            Console.WriteLine("Pinged your deployment. You successfully connected to MongoDB!");
        } catch (Exception ex) {
            Console.WriteLine(ex);
        }
    }

}