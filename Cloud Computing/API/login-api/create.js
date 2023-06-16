const express = require('express');
const router = express.Router();
const bodyParser = require('body-parser');
const jwt = require('jsonwebtoken');
require('dotenv').config();
const profile = require('./profile');

const secretKey = process.env.SECRET_KEY || 'rahasia';


router.use(bodyParser.json());

router.post('/', profile.verifyToken, (req, res) => {
  const { username, email } = req.body;

  // Process creating a new user...
  // Validate the request to check the availability of the email and username
  const userRef = db.collection('users').where('username', '==', username).limit(1);

  userRef
    .get()
    .then((snapshot) => {
      if (!snapshot.empty) {
        // Username already exists
        return res.status(409).json({ error: 'Username already taken' });
      }

      // Check if the email exists
      const emailRef = db.collection('users').where('email', '==', email).limit(1);

      emailRef
        .get()
        .then((snapshot) => {
          if (!snapshot.empty) {
            // Email already exists
            return res.status(409).json({ error: 'Email already taken' });
          }

          // Email and username are available, insert the new user into the database
          const newUser = { username, email };

          db.collection('users')
            .add(newUser)
            .then(() => {
              res.json({ message: 'User created successfully' });
            })
            .catch((error) => {
              console.log(error);
              res.status(500).json({ error: 'Server error' });
            });
        })
        .catch((error) => {
          console.log(error);
          res.status(500).json({ error: 'Server error' });
        });
    })
    .catch((error) => {
      console.log(error);
      res.status(500).json({ error: 'Server error' });
    });
});

module.exports = router;
