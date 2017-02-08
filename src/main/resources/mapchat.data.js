//mongoDB stores position as longitude,latitude. Opposite to Google Maps

db.createCollection("mapmessages")
db.mapmessages.save({ _id: 1, message:"Any body there?", "duration" : 4, location: {type:"Point", coordinates: [ -121.905671, 37.7648235]}})
db.mapmessages.insert({ _id: 2, message:"LOL, that's funny", "duration" : 4, location: {type:"Point", coordinates: [ -121.90389, 37.764086]}})
db.mapmessages.save({ _id: 3, message:"Walgreens has very cheap stuff", "duration" : 4, location: {type:"Point", coordinates: [ -121.9046315, 37.7634744]}})

db.mapmessages.save({ _id: 4, message:"Berlin", "duration" : 4, location: {type:"Point", coordinates: [13.405838,52.531261]}})
db.mapmessages.save({ _id: 5, message:"Cologne", "duration" : 4, location: {type:"Point", coordinates: [6.921272,50.960157]}})
db.mapmessages.save({ _id: 6, message:"Dusseldorf", "duration" : 4, location: {type:"Point", coordinates: [6.810036,51.224088]}})

db.mapmessages.ensureIndex({location: "2dsphere"})



db.runCommand( { geoNear: "mapmessages", near: {type: "Point", coordinates: [ -121.90389, 37.764086 ] }, spherical: true, maxDistance: 40 } )
