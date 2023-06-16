const express = require('express');
const app = express();
const port = process.env.PORT || 3000;
// const login = require('./login');
// const create = require('./create');
// const profile = require('./profile');
// const camerausage = require('./camerausage');
// const leaderboard = require('./leaderboard');
require('dotenv').config();


// Middleware
app.use(express.json());

// Routes
// app.use('/', login);
// app.use('/create', create);
// app.use('/profile', require('./profile'));
// app.use('/camerausage', camerausage);
app.use('/leaderboard', require('./leaderboard'));

// Start the server
app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});
