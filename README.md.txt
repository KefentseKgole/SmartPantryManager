# Smart Pantry Manager

## Description
Smart Pantry Manager is a Java-based Android application designed to reduce food waste. It allows users to track the ingredients they currently have at home. The app features a strict-matching algorithm that only suggests recipes if the user has *every single required ingredient* in their pantry.

## Database Option
**SQLite** (via `SQLiteOpenHelper`).
*Why?* I chose SQLite because it is a lightweight, local, and serverless database that comes built-in with Android. It ensures the app works completely offline and is perfect for storing structured data like pantry items and recipes without the need for a complex backend.

## Setup and Run Instructions
1. Download and install Android Studio.
2. Open Android Studio and select **Open an Existing Project**.
3. Navigate to the `SmartPantryManager` folder and open it.
4. Allow Gradle to sync and download the necessary dependencies.
5. Create an Android Virtual Device (AVD) or connect a physical Android phone.
6. Click the green **Run (▶)** button at the top to build and launch the app.
(Docs: Add comprehensive README)
