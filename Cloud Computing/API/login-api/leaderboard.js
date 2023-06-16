const express = require('express');
const router = express.Router();
const app = require('./firebase');
const db = app.firestore();
require('dotenv').config();

router.get('/', async (req, res) => {
  // Retrieve camera usage data from the database
  // Fetch all users from the database
  const leaderboard = [];
  const usersRef = db.collection('user');

  const usersnapshot=await usersRef
    .orderBy('monthlyProductiveHour', 'desc')
    .limit(10)
    .get();

  if (!usersnapshot.empty) {
    usersnapshot.forEach((doc)=>{
      const {name,userPoint, monthlyProductiveHour}=doc.data()
      leaderboard.push({
        name,
        userPoint,
        monthlyProductiveHour,
      })
    })
  }

  res.json({
    leaderboard
  })
});

module.exports = router;
