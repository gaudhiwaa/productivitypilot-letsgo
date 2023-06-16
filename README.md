# productivitypilot-letsgo
Bangkit 2023 Project Capstone: ProductivityPilot

## Setup Firebase to Android
To connect firebase to Android you can check the Firebase [documentation](https://firebase.google.com/docs/android/setup#:~:text=Open%20the%20Firebase%20Assistant%3A%20Tools,your%20Android%20project%20with%20Firebase.)

## Firebase Feature

1. Authentication
2. Firestore Database
3. Functions

## Authentication
- Register
  
```env
auth.createUserWithEmailAndPassword(email, password)
    .addOnCompleteListener { task ->
        if (task.isSuccessful) {
            // User registration successful
        } else {
            // User registration failed       
        }
    }
```

- Sign In

```env
   auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign-in successful
                } else {
                    // Sign-in faile
                }
            }
```

## Firestore Database

Firestore Structure

```env
### Collection: users

#### Document: 0DmJ4VzsdHecc9pGtper3NFp1Cq1

- **userId**: [string]
- **name**: [string]
- **monthlyProductiveHour**: [number]
- **weeklyProductiveHour**: [number]
- **dailyProductiveHour**: [number]
- **email**: [string]
- **following**: [array]
- **hobby**: [string]
- **institution**: [string]
- **location**: [string]
- **occupation**: [string]
- **userPoint**: [number]
```

```env
### Collection: occupation

#### Document: 93HMAHKKAawraNm1mgkV

- **id**: [string]
- **name**: [string]
```

```env
### Collection: institution

#### Document: 6HcxckfDXaYdN9AqS4X6

- **id**: [string]
- **name**: [string]
```

```env
### Collection: location

#### Document: 6wuFL801S0TQS7Cz8LxA

- **id**: [string]
- **name**: [string]
```

```env
### Collection: hobby

#### Document: 91hipry1a6XqgOyoPdtE

- **id**: [string]
- **name**: [string]
```

- GET
```env
db.collection("users").document("user1")
    .get()
    .addOnSuccessListener { document ->
        if (document != null) {
            // Process the retrieved data
        } else {
            // Document does not exist
        }
    }
    .addOnFailureListener { exception ->
        // Handle any errors
    }
```

- POST
```env
db.collection(collection)
    .document(documentId)
    .set(data)
    .addOnSuccessListener {
        // Data successfully posted to Firestore
    }
    .addOnFailureListener { exception ->
        // Handle any errors
    }
```

- PUT
```env
db.collection(collection).document(documentId)
    .update(updates)
    .addOnSuccessListener {
        // Update successful
        println("Document successfully updated!")
    }
    .addOnFailureListener { exception ->
        // Handle any errors
        println("Error updating document: $exception")
    }
```

## Functions

* Endpoint:
    * `POST /recommendations`

* Content-Type:
    * `application/json`

* Request Body: 
```json
{
  "user_id": 61377
}
```

* Response:
```json
{
    "recommendedUsers": [
        61377,
        16429,
        27812,
        30786,
        76660,
        10476,
        31025,
        79192,
        50048,
        85501
    ]
}
```
