const express = require('express');
const router = express.Router();
const jwt = require('jsonwebtoken');
const bcrypt = require('bcrypt');
const cors = require('cors');
const app = require('./firebase');
const auth = app.auth()
const secretKey = process.env.SECRET_KEY;

router.use(cors());

router.post('/login', (req, res) => {
  const { username, password } = req.body;

  // Add logic to check username and password in the database
  // Example: Assuming you have a 'users' collection in Firebase

  // Retrieve the user with the given username from the database
  const userRef = db.collection('users').where('username', '==', username).limit(1);

  userRef
    .get()
    .then((snapshot) => {
      if (snapshot.empty) {
        // User not found
        return res.status(401).json({ error: 'Invalid credentials' });
      }

      const user = snapshot.docs[0].data();

      // Compare the provided password with the stored password hash
      bcrypt.compare(password, user.password, (err, result) => {
        if (err) {
          return res.status(500).json({ error: 'Server error' });
        }

        if (!result) {
          // Passwords don't match
          return res.status(401).json({ error: 'Invalid credentials' });
        }

        // Authentication successful
        const token = jwt.sign({ username, email: user.email }, secretKey);
        res.json({ token, email: user.email, username });
      });
    })
    .catch((error) => {
      console.log(error);
      res.status(500).json({ error: 'Server error' });
    });
});

module.exports = router;