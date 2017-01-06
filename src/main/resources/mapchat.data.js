//mongoDB stores position as longitude,latitude. Opposite to Google Maps
db.createCollection("messages")
db.messages.save({ _id: 1, message: "Any body there?", position: [37.7648235,-121.905671]})
db.messages.save({ _id: 2, message: "LOL that's funny", position: [37.764086,-121.90389]})
db.messages.save({ _id: 3, message: "Walgreens has very cheap drinks xD", position: [37.7634744,-121.9046315]})
db.messages.ensureIndex({position: "2d"})


db.createCollection("mapmessages")
db.mapmessages.save({ message:"Any body there?", loc: {type:"Point", coordinates: [ -121.905671, 37.7648235]}})
db.mapmessages.insert({ message:"LOL, that's funny", loc: {type:"Point", coordinates: [ -121.90389, 37.764086]}})
db.mapmessages.save({ message:"Walgreens has very cheap stuff", loc: {type:"Point", coordinates: [ -121.9046315, 37.7634744]}})

db.mapmessages.save({ message:"Berlin", loc: {type:"Point", coordinates: [13.405838,52.531261]}})
db.mapmessages.save({ message:"Cologne", loc: {type:"Point", coordinates: [6.921272,50.960157]}})
db.mapmessages.save({ message:"Dusseldorf", loc: {type:"Point", coordinates: [6.810036,51.224088]}})

db.mapmessages.ensureIndex({loc: "2dsphere"})



db.runCommand( { geoNear: "mapmessages", near: {type: "Point", coordinates: [ -121.90389, 37.764086 ] }, spherical: true, maxDistance: 40000 } )
