//mongoDB stores position as longitude,latitude. Opposite to Google Maps

db.createCollection("mapmessages")
db.mapmessages.save({ _id: UUID('e4248835ad054dc29587a150aa17a741'), message:"Any body there?", "duration" : 4, location: {type:"Point", coordinates: [ -121.905671, 37.7648235]}})
db.mapmessages.insert({ _id: UUID('0e375a8a737e4034bae62f838977ec4c'), message:"LOL, that's funny", "duration" : 4, location: {type:"Point", coordinates: [ -121.90389, 37.764086]}})
db.mapmessages.save({ _id: UUID('9bfdd5dee5354f1dbf21ce357d8615ed'), message:"Walgreens has very cheap stuff", "duration" : 4, location: {type:"Point", coordinates: [ -121.9046315, 37.7634744]}})

db.mapmessages.save({ _id: UUID('98d23e0244204ae7ad2fef2164b91faf'), message:"Berlin", "duration" : 4, location: {type:"Point", coordinates: [13.405838,52.531261]}})
db.mapmessages.save({ _id: UUID('6cabf51003084300b8825bc2d8710f88'), message:"Cologne", "duration" : 4, location: {type:"Point", coordinates: [6.921272,50.960157]}})
db.mapmessages.save({ _id: UUID('637a2b952e614628b688f00e9d45504b'), message:"Dusseldorf", "duration" : 4, location: {type:"Point", coordinates: [6.810036,51.224088]}})

db.mapmessages.ensureIndex({location: "2dsphere"})



db.runCommand( { geoNear: "mapmessages", near: {type: "Point", coordinates: [ -121.90389, 37.764086 ] }, spherical: true, maxDistance: 40 } )
