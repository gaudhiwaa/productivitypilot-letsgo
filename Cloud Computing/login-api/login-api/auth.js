const jwt = require("jsonwebtoken");
const secretKey = "rahasia"; // Kunci rahasia yang sama dengan yang digunakan saat menghasilkan token

// Contoh fungsi untuk memverifikasi token
function verifyToken(token) {
  try {
    const decoded = jwt.verify(token, secretKey);
    return decoded;
  } catch (err) {
    // Token tidak valid atau kadaluarsa
    return null;
  }
}

// Contoh penggunaan fungsi verifikasi token
const token =
  "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InVzZXIxIiwiaWF0IjoxNjg1MjgzNTM5fQ.EJ-lJ-IhDSnZrwNe6zDL12iqRac-Bw36yvtrX13p-V8";
const decodedToken = verifyToken(token);

if (decodedToken) {
  // Token valid
  console.log("Token valid");
  console.log("Username:", decodedToken.username);
} else {
  // Token tidak valid
  console.log("Token tidak valid");
}
