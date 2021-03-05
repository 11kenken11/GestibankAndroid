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

// équipe

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

app.get("/user/:login", async (req, res) => {
  const login = req.params.login;
  try {
    const user = await db.collection("user").findOne({ login });
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

app.put("/users/:login", async (req, res) => {
  try {
    const login = req.params.login;
    const remplacementUser = req.body;
    const user = await db.collection("user").replaceOne({ login }, remplacementUser);
    res.status(200).json(user);
  } catch (err) {
    console.log(err);
    throw err;
  }
});

app.delete("/equipes/:login", async (req, res) => {
  try {
    const login = req.params.login;
    const user = await db.collection("user").deleteOne({ login });
    res.status(200).json(user);
  } catch (err) {
    console.log(err);
    throw err;
  }
});

