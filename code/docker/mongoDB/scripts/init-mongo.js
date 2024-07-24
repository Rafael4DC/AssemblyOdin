db = db.getSiblingDB('LogRepo');

db.createCollection('Unprocessed');
db.createCollection('Processed');
db.createCollection('Bin');

db.createUser({
    user: "admin",
    pwd: "adminPassword",
    roles: [
        { role: "userAdminAnyDatabase", db: "admin" },
        { role: "readWriteAnyDatabase", db: "admin" }
    ]
});

db.Unprocessed.insertMany([
    { identifier: 'ExampleIdentifier1', machineName: 'Machine1', timestamp: new Date(), type: 'Type1' },
    { identifier: 'ExampleIdentifier2', machineName: 'Machine2', timestamp: new Date(), type: 'Type2' },
]);
