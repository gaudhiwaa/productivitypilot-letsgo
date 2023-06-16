const express = require('express');
const router = express.Router();
const jwt = require('jsonwebtoken');
const app = require('./firebase');
const db = app.firestore();

const secretKey = process.env.SECRET_KEY;

const authenticateToken = (req, res, next) => {
  const authHeader = req.headers['authorization'];
  const token = authHeader && authHeader.split(' ')[1];

  if (!token) {
    return res.status(401).json({ error: 'Unauthorized' });
  }

  jwt.verify(token, secretKey, (err, decoded) => {
    if (err) {
      return res.status(403).json({ error: 'Invalid token' });
    }
    req.username = decoded.username;
    next();
  });
};

router.post('/', //authenticateToken, async (req, res) => {
  const { username } = req.body;

  // Fetch the user profile data from the database
  const userRef = db.collection('users').where('username', '==', username).limit(1);

  const userSnapshot = await userRef.get();
  const user = userSnapshot.docs[0].data();
  res.json({user})
});

module.exports = router;
