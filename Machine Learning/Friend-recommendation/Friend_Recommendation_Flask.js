const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

const df = require('./Data generated.json');
const inputFeatures = ['domisili', 'pekerjaan', 'instansi', 'hobi'];
const outputFeature = 'userid';

exports.recommendations = functions.https.onRequest((req, res) => {
  const { user_id } = req.body;

  if (!user_id) {
    return res.status(400).json({ error: 'Invalid request. Missing user_id parameter.' });
  }

  const recommendedUsers = getRecommendations(user_id);

  return res.json({ recommendedUsers });
});

function getRecommendations(user_id, top_n = 10) {
  const userIndex = df.findIndex((row) => row[outputFeature] === user_id);

  if (userIndex === -1) {
    return [];
  }

  const userText = inputFeatures.map((feature) => df[userIndex][feature]);
  const cosineSimilarities = [];

  for (let i = 0; i < df.length; i++) {
    const text = inputFeatures.map((feature) => df[i][feature]);
    const similarity = calculateCosineSimilarity(userText, text);
    cosineSimilarities.push({ index: i, similarity });
  }

  cosineSimilarities.sort((a, b) => b.similarity - a.similarity);

  const recommendedUsers = cosineSimilarities
    .slice(0, top_n)
    .map((similarity) => df[similarity.index][outputFeature]);

  return recommendedUsers;
}

function calculateCosineSimilarity(text1, text2) {
  const dotProduct = dot(text1, text2);
  const magnitude1 = magnitude(text1);
  const magnitude2 = magnitude(text2);

  if (magnitude1 === 0 || magnitude2 === 0) {
    return 0;
  }

  return dotProduct / (magnitude1 * magnitude2);
}

function dot(vec1, vec2) {
  let product = 0;
  for (let i = 0; i < vec1.length; i++) {
    product += vec1[i] * vec2[i];
  }
  return product;
}

function magnitude(vec) {
  let sum = 0;
  for (let i = 0; i < vec.length; i++) {
    sum += vec[i] * vec[i];
  }
  return Math.sqrt(sum);
}
