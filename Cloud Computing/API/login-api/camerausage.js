const express = require('express');
const router = express.Router();
const bodyParser = require('body-parser');

router.use(bodyParser.json());

router.put('/', (req, res) => {
  const { username, usageCount } = req.body;

  // Update camera usage for a specific user with the given username
  // Assuming you have a 'users' collection in Firebase

  // Check if the user exists
  const userRef = db.collection('users').where('username', '==', username).limit(1);

  userRef
    .get()
    .then((snapshot) => {
      if (snapshot.empty) {
        // User not found
        return res.status(404).json({ error: 'User not found' });
      }

      // User found, update the camera usage
      const user = snapshot.docs[0].data();

      // Update the usageCount property of the user
      user.usageCount = usageCount;

      // Update the user in the database
      db.collection('users')
        .doc(snapshot.docs[0].id)
        .update(user)
        .then(() => {
          res.status(200).json({ message: 'Camera usage updated successfully' });
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
