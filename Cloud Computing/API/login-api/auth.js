const express = require('express');
const jwt = require('jsonwebtoken');
const secretKey = 'rahasia'; // Same secret key used for token generation

const router = express.Router();

// Example function for token verification
function verifyToken(token) {
  try {
    const decoded = jwt.verify(token, secretKey);
    return decoded;
  } catch (err) {
    // Invalid or expired token
    return null;
  }
}

// Example usage of token verification function
const token =
  'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InVzZXIxIiwiaWF0IjoxNjg1MjgzNTM5fQ.EJ-lJ-IhDSnZrwNe6zDL12iqRac-Bw36yvtrX13p-V8';
const decodedToken = verifyToken(token);

if (decodedToken) {
  // Valid token
  console.log('Valid token');
  console.log('Username:', decodedToken.username);
} else {
  // Invalid token
  console.log('Invalid token');
}

module.exports = router;