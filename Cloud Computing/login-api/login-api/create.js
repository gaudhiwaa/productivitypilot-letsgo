const express = require("express");
const bodyParser = require("body-parser");
const jwt = require("jsonwebtoken");

const app = express();
const port = 3000;
const secretKey = "rahasia"; // Kunci rahasia yang sama dengan yang digunakan saat menghasilkan token

app.use(bodyParser.json());

// Middleware untuk memverifikasi token
function verifyToken(req, res, next) {
  const authHeader = req.headers.authorization;

  if (authHeader) {
    const token = authHeader.split(" ")[1];

    jwt.verify(token, secretKey, (err, decoded) => {
      if (err) {
        // Token tidak valid
        res.sendStatus(403);
      } else {
        // Token valid, simpan informasi pengguna ke request
        req.user = decoded;
        next();
      }
    });
  } else {
    // Tidak ada token dalam header Authorization
    res.sendStatus(401);
  }
}

// API endpoint untuk membuat pengguna
app.post("/users", verifyToken, (req, res) => {
  // Hanya pengguna yang terotentikasi (dengan token) yang dapat membuat pengguna baru
  const { username, email } = req.body;

  // Proses pembuatan pengguna baru...

  res.json({ message: "User created successfully" });
});

app.listen(port, () => {
  console.log(`Server berjalan di http://localhost:${port}`);
});
