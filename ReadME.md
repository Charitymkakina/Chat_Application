# Java Console Chat Application

A simple but progressively built Java-based chat application developed as a learning project to strengthen Object-Oriented Programming, file handling, and software design skills.

# Current Progress (Step 6 Complete)

At this stage, the application is fully functional as a console-based chat system with persistent storage.

# What has been accomplished

- Created a `User` class to represent chat users
- Created a `Message` class with sender, content, and timestamp
- Built a `ChatManager` class to handle chat logic
- Implemented a menu-driven console interface using `Scanner`
- Added ability to:
  - Send messages
  - View chat history
- Integrated timestamps using `LocalDateTime`
- Formatted timestamps for readability
- Stored messages in memory using `ArrayList`
- Implemented file persistence:
  - Messages are saved to `messages.txt`
  - Data is appended (not overwritten)
- Used proper exception handling for file operations

# Key Concepts Learned

- Object-Oriented Programming (OOP)
  - Classes and objects
  - Encapsulation
  - Constructors
- Java Collections (`ArrayList`)
- File Handling
  - `FileWriter`
  - `BufferedWriter`
- Exception Handling (`try-catch`)
- Java Date and Time API (`LocalDateTime`)
- Menu-driven console applications
- Separation of concerns (clean class structure)

# Project Structure
ChatApplication/
│
├── Main.java
├── User.java
├── Message.java
├── ChatManager.java
└── messages.txt (generated after running)

# Current Features

- Console-based user interaction
- Message sending system
- Chat history display
- Timestamped messages
- Persistent message storage in a text file

# Upcoming Goals (Step 7 and Beyond)

# Step 7: Load Chat History from File
- Read previous messages from `messages.txt` on startup
- Display old messages automatically
- Ensure chat history persists across sessions

# Step 8: Improve Message System
- Add message IDs
- Improve formatting and structure
- Separate file storage into better format (e.g. JSON-like structure)

# Step 9: Multi-User Simulation
- Allow switching between users
- Simulate real conversations between multiple users

# Step 10: Networking (Real Chat System)
- Introduce `Socket` and `ServerSocket`
- Enable real-time messaging between two programs
- Build client-server architecture

# Step 11: Advanced Features
- Message encryption (cybersecurity focus)
- Login system
- Message search
- GUI version using JavaFX or Swing

## Project Goal

To progressively evolve this application from a simple console program into a real-world chat system that demonstrates:

- Strong Java fundamentals
- Software design principles
- File persistence
- Networking concepts
- Security awareness

## Author
Charity Mkakina