using MongoDB.Bson;
using MongoDB.Driver;

namespace AssemblyHeimdall.Repo;

public class MongoRepo(string connectionUri,string database, string collection) : IStoreRepo
{
    private string ConnectionUri { get; set; } = connectionUri;
    private string Database { get; set; } = database;
    private string Collection { get; set; } = collection;

    public void Store(string json)
    {
        MongoClientSettings settings = MongoClientSettings.FromConnectionString(ConnectionUri);

        settings.ServerApi = new ServerApi(ServerApiVersion.V1);

        MongoClient client = new MongoClient(settings);

        IMongoDatabase database = client.GetDatabase(Database);
        IMongoCollection<BsonDocument> unprocessedCollection = database.GetCollection<BsonDocument>(Collection);

        BsonArray logs = MongoDB.Bson.Serialization.BsonSerializer.Deserialize<BsonArray>(json);
        foreach (BsonValue log in logs)
        {
            unprocessedCollection.InsertOne(log.AsBsonDocument);
        }
    }

}