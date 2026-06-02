# Java Chat Application

A progressively built Java-based chat application developed to strengthen Object-Oriented Programming, file handling, networking, multithreading, and software design skills.

## Current Progress (Step 16 Complete)

The application has evolved from a simple console chat system into a networked multi-client chat application capable of storing messages, handling multiple users, and broadcasting messages in real time.

## Features Implemented

### Core Chat Features

* User management using a dedicated `User` class
* Message handling using a dedicated `Message` class
* Centralized chat logic through `ChatManager`
* Menu-driven console interface
* Send messages
* View chat history
* Search messages by keyword
* Delete messages
* Timestamped messages using `LocalDateTime`

### Persistence Features

* Save messages to `messages.txt`
* Load chat history automatically on startup
* Rewrite stored messages after deletion
* Persistent storage across application sessions

### Networking Features

* Client-server architecture using `Socket` and `ServerSocket`
* Dedicated `ChatServer`
* Dedicated `ChatClient`
* Multi-threaded server using Java Threads
* Multiple client support
* Broadcast messaging to connected clients
* Username identification
* Logout and disconnect handling

## Key Concepts Learned

### Object-Oriented Programming (OOP)

* Classes and Objects
* Encapsulation
* Constructors
* Separation of Concerns
* Reusable Components

### Java Collections

* ArrayList
* Iteration and Searching
* Collection Manipulation

### File Handling

* FileReader
* BufferedReader
* FileWriter
* BufferedWriter

### Exception Handling

* Try-Catch Blocks
* IOException Handling

### Date and Time API

* LocalDateTime
* DateTimeFormatter

### Networking

* Socket
* ServerSocket
* Client-Server Architecture
* Input Streams
* Output Streams

### Concurrency

* Threads
* Runnable Interface
* Multi-client Handling

## Project Structure

ChatApplication/
│
├── Main.java
├── User.java
├── Message.java
├── ChatManager.java
├── ChatServer.java
├── ChatClient.java
├── ClientHandler.java
└── messages.txt

## Current Capabilities

* Console-based chat system
* Persistent message storage
* Search functionality
* Message deletion
* Chat history loading
* Multi-client networking
* Real-time message broadcasting
* Username support
* Logout functionality
* Multi-threaded server architecture

## Upcoming Enhancements

### Security Features

* Message encryption
* Secure communication
* Authentication system

### User Experience

* Graphical User Interface (JavaFX)
* Improved message formatting
* Private messaging

### Advanced Development

* Database integration
* Spring Boot backend version
* REST API development
* React frontend integration
* Cloud deployment

## Project Goal

To progressively evolve this application from a console-based chat system into a full-featured communication platform while developing strong skills in:

* Java Programming
* Software Design
* Networking
* Multithreading
* Backend Development
* Security Concepts
* Full-Stack Development

