const express = require("express");
const bodyParser = require("body-parser");
const app = express();
const jwt = require("jsonwebtoken");
const bcrypt = require("bcrypt");
const cors = require("cors");
const port = 3000; // Ganti dengan port yang sesuai
const secretKey = "rahasia"; // Ganti dengan kunci rahasia yang kuat

app.use(bodyParser.json());
app.use(cors());

app.post("/login", (req, res) => {
  const { username, password } = req.body;

  // TODO: Implement authentication logic
  // Contoh implementasi sederhana:
  console.log("username", username, "password", password, req.body);
  if (username === "user1" && password === "password1") {
    // Otentikasi berhasil
    const token = jwt.sign({ username }, secretKey);
    res.json({ token });
  } else {
    // Otentikasi gagal
    res.status(401).json({ error: "Invalid credentials" });
  }
});

app.listen(port, () => {
  console.log(`Server berjalan di http://localhost:${port}`);
});
