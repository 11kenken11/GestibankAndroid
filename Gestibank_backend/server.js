const express = require("express");
const app = express();
app.use(express.json());

app.listen(85, () => {
  console.log("Serveur Express a l ecoute sur le port 85");
});

const cors = require("cors");

var corsOptions = {
  origin: ["http://localhost:4200/", "http://localhost", "http://localhost:8100"],
};

app.use(cors(corsOptions));

const MongoClient = require("mongodb").MongoClient;
const url = "mongodb://localhost:27017";
const dbName = "gestibank";
let db;
MongoClient.connect(url, function (err, client) {
  console.log("Connexion réussi avec Mongo");
  db = client.db(dbName);
});



app.get("/users", (req, res) => {
  db.collection("user")
    .find({})
    .toArray(function (err, docs) {
      if (err) {
        console.log(err);
        throw err;
      }
      res.status(200).json(docs);
    });
});

app.get("/user/:email", async (req, res) => {
  const email = req.params.email;
  try {
    const user = await db.collection("user").findOne({ email });
    res.status(200).json(user);
  } catch (err) {
    console.log(err);
    throw err;
  }
});

app.post("/users", async (req, res) => {
  try {
    const userData = req.body;
    const user = await db.collection("user").insertOne(userData);
    res.status(200).json(user);
  } catch (err) {
    console.log(err);
    throw err;
  }
});

app.put("/users/:email", async (req, res) => {
  try {
    const email = req.params.email;
    const remplacementUser = req.body;
    const user = await db.collection("user").replaceOne({ email }, remplacementUser);
    res.status(200).json(user);
  } catch (err) {
    console.log(err);
    throw err;
  }
});

app.delete("/users/:email", async (req, res) => {
  try {
    const email = req.params.email;
    const user = await db.collection("user").deleteOne({ email });
    res.status(200).json(user);
  } catch (err) {
    console.log(err);
    throw err;
  }
});

app.post("/authenticate", async (req, res) => {
    try {
      const email = req.body.email;
      const password = req.body.password;
      const user = await db.collection("user").findOne({email});
      if(!user) {
        return res.status(400).json({
            message: "User doesn't Exist"
          });
      } else if(password === user.password) {
        res.status(200).json(user);
      }
      
    } catch (err) {
      console.log(err);
      throw err;
    }
  });


  // ADMIN

  // get agents
  app.get("/agents", (req, res) => {
    db.collection("user")
      .find({"role": "AGENT"})
      .toArray(function (err, docs) {
        if (err) {
          console.log(err);
          throw err;
        }
        res.status(200).json(docs);
      });
  });

  // get one agent by matricule
  app.get("/agents/:matricule", async (req, res) => {
    const matricule = req.params.matricule;
    try {
      const user = await db.collection("user").findOne({ matricule });
      res.status(200).json(user);
    } catch (err) {
      console.log(err);
      throw err;
    }
  });

  // get pending clients 
  app.get("/users/pending", (req, res) => {
    db.collection("user")
      .find({"role": "CLIENT", "status": "EN_ATTENTE", "agentMatricule": {$exists: false}})
      .toArray(function (err, docs) {
        if (err) {
          console.log(err);
          throw err;
        }
        res.status(200).json(docs);
      });
  });

  // get pending clients 
  app.get("/users/pending/:agentMatricule", (req, res) => {
    const agentMatricule = req.params.agentMatricule;
    db.collection("user")
      .find({"role": "CLIENT", "status": "EN_ATTENTE", "agentMatricule": agentMatricule})
      .toArray(function (err, docs) {
        if (err) {
          console.log(err);
          throw err;
        }
        res.status(200).json(docs);
      });
  });

   // get validated clients 
   app.get("/users/validated", (req, res) => {
    db.collection("user")
      .find({"role": "CLIENT", "status": "VALIDE"})
      .toArray(function (err, docs) {
        if (err) {
          console.log(err);
          throw err;
        }
        res.status(200).json(docs);
      });
  });

  // update agent
  app.put("/agents/:matricule", async (req, res) => {
    try {
      const matricule = req.params.matricule;
      const remplacementAgent = req.body;
      const user = await db.collection("user").replaceOne({ matricule }, remplacementAgent);
      res.status(200).json(user);
    } catch (err) {
      console.log(err);
      throw err;
    }
  });
