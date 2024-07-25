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
    { _id: ObjectId("666c5a4d1852b08d2bdbfd12"), identifier: 'rafael.costa@assembly.pt', machineName: 'PT-AL-GAM001', timestamp:'2024-06-14T10:56:10.8397683Z', type: 'Login' },
    { _id: ObjectId("666c59991852b08d2bdbfd11"), identifier: 'andre.mota@students.assembly.pt', machineName: 'PT-AL-GAM001', timestamp: '2024-06-14T14:54:09.0364949Z', type: 'Login' },
    { _id: ObjectId("666c5a4d1852b08d2bdbfd14"), identifier: 'andre.mota@students.assembly.pt', machineName: 'PT-AL-GAM001', timestamp: '2024-06-14T14:55:45.4054426Z', type: 'Login' },
    { _id: ObjectId("666c5b96355e465d0b7c1fce"), identifier: 'pedro.rodrigues@students.assembly.pt', machineName: 'PT-AL-GAM002', timestamp: '2024-06-14T15:00:56.4884660Z', type: 'Login' },
    { _id: ObjectId("666c5e67355e465d0b7c1fd0"), identifier: 'miguel.menezes@students.assembly.pt', machineName: 'PT-AL-GAM001', timestamp: '2024-06-14T15:14:44.9133021Z', type: 'Login' },
    { _id: ObjectId("666c5a4d1852b08d2bdbfd10"), identifier: 'RafaelCosta', machineName: 'PT-AL-GAM001', timestamp: '2024-06-14T12:56:03.6620948Z', type: 'Logout' },
    { _id: ObjectId("666c5a4d1852b08d2bdbfd15"), identifier: 'AndréMota', machineName: 'PT-AL-GAM001', timestamp: '2024-06-14T14:55:37.7362122Z', type: 'Logout' },
    { _id: ObjectId("666c5a4d1852b08d2bdbfd13"), identifier: 'AndréMota', machineName: 'PT-AL-GAM001', timestamp: '2024-06-14T14:56:03.6620948Z', type: 'Logout' },
    { _id: ObjectId("666c66441852b08d2bdbfd16"), identifier: 'MiguelMenezes', machineName: 'PT-AL-GAM001', timestamp: '2024-06-14T15:48:08.2669620Z', type: 'Logout' },
    { _id: ObjectId("666d64abdb3b62aad6cc26e1"), identifier: 'PedroRodrigues', machineName: 'PT-AL-GAM002', timestamp: '2024-06-14T16:03:21.1324332Z', type: 'Logout' }
]);



