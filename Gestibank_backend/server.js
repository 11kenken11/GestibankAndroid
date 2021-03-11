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

var nodemailer = require("nodemailer");

var transporter = nodemailer.createTransport({
  service: "gmail",
  auth: {
    user: "kenji.fujimoto.test@gmail.com",
    pass: "",
  },
});

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
    if (userData.role === "CLIENT") {
      var mailOptions = {
        from: "kenji.fujimoto.test@gmail.com",
        to: "kenji111.fujimoto@gmail.com",
        subject: "Création de votre compte",
        text:
          "Votre demande de création de compte a bien été prise en compte,\n un agent vous sera affecté et votre mot de passe vous sera communiqué à la validation de vos informations",
      };

      transporter.sendMail(mailOptions, function (error, info) {
        if (error) {
          console.log(error);
        } else {
          console.log("Email sent: " + info.response);
        }
      });
    }
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
    if (req.body.password && req.body.status === "VALIDE") {
      var mailOptions = {
        from: "kenji.fujimoto.test@gmail.com",
        to: "kenji111.fujimoto@gmail.com",
        subject: "Account created",
        text: "Your password: " + req.body.password,
      };

      transporter.sendMail(mailOptions, function (error, info) {
        if (error) {
          console.log(error);
        } else {
          console.log("Email sent: " + info.response);
        }
      });
    }
    res.status(200).json(user);
  } catch (err) {
    console.log(err);
    throw err;
  }
});

//generate new pwd
app.put("/forgot-password/:email", async (req, res) => {
  try {
    const email = req.params.email;
    const user = await db.collection("user").findOne({ email });
    const password = (Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15)).slice(0, 8);
    user.password = password;
    const userUpdated = await db.collection("user").replaceOne({ email }, user);

    var mailOptions = {
      from: "kenji.fujimoto.test@gmail.com",
      to: "kenji111.fujimoto@gmail.com",
      subject: "New password",
      text: "Your new password: " + password,
    };

    transporter.sendMail(mailOptions, function (error, info) {
      if (error) {
        console.log(error);
      } else {
        console.log("Email sent: " + info.response);
      }
    });

    res.status(200).json(user);
  } catch (err) {
    console.log(err);
    throw err;
  }
});

// check request
app.put("/check-request/:email", async (req, res) => {
  try {
    const email = req.params.email;
    const user = await db.collection("user").findOne({ email });
    user.chequier = "DEMANDE";
    const userUpdated = await db.collection("user").replaceOne({ email }, user);

    var mailOptions = {
      from: "kenji.fujimoto.test@gmail.com",
      to: "kenji111.fujimoto@gmail.com",
      subject: "Demande de chèquier",
      text: "Votre demande de chèquier a bien été prise en compte ",
    };

    transporter.sendMail(mailOptions, function (error, info) {
      if (error) {
        console.log(error);
      } else {
        console.log("Email sent: " + info.response);
      }
    });

    res.status(200).json(user);
  } catch (err) {
    console.log(err);
    throw err;
  }
});

// valid check request
app.put("/valid-check/:email", async (req, res) => {
  try {
    const email = req.params.email;
    const user = await db.collection("user").findOne({ email });
    user.chequier = "VALIDE";
    const userUpdated = await db.collection("user").replaceOne({ email }, user);

    var mailOptions = {
      from: "kenji.fujimoto.test@gmail.com",
      to: "kenji111.fujimoto@gmail.com",
      subject: "Chèquier",
      text: "Votre chèquier est prêt ",
    };

    transporter.sendMail(mailOptions, function (error, info) {
      if (error) {
        console.log(error);
      } else {
        console.log("Email sent: " + info.response);
      }
    });

    res.status(200).json(user);
  } catch (err) {
    console.log(err);
    throw err;
  }
});

// reject check request
app.put("/reject-request/:email", async (req, res) => {
  try {
    const email = req.params.email;
    const user = await db.collection("user").findOne({ email });
    user.chequier = "REFUSE";
    const userUpdated = await db.collection("user").replaceOne({ email }, user);

    var mailOptions = {
      from: "kenji.fujimoto.test@gmail.com",
      to: "kenji111.fujimoto@gmail.com",
      subject: "Chèquier",
      text: "Votre demande de chèquier a été refusée ",
    };

    transporter.sendMail(mailOptions, function (error, info) {
      if (error) {
        console.log(error);
      } else {
        console.log("Email sent: " + info.response);
      }
    });

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
    const user = await db.collection("user").findOne({ email });
    if (!user) {
      return res.status(400).json({
        message: "User doesn't Exist",
      });
    } else if (password === user.password) {
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
    .find({ role: "AGENT" })
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
    .find({ role: "CLIENT", status: "EN_ATTENTE", agentMatricule: { $exists: false } })
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
    .find({ role: "CLIENT", status: "EN_ATTENTE", agentMatricule: agentMatricule })
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
    .find({ role: "CLIENT", status: "VALIDE" })
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

// get check requested clients
app.get("/list-check/:agentMatricule", (req, res) => {
  const agentMatricule = req.params.agentMatricule;
  db.collection("user")
    .find({ role: "CLIENT", chequier: "DEMANDE", agentMatricule: agentMatricule })
    .toArray(function (err, docs) {
      if (err) {
        console.log(err);
        throw err;
      }
      res.status(200).json(docs);
    });
});
