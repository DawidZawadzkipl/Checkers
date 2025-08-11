# 🏁 Checkers Game - Java Implementation
## 📝 Project Description
A classic checkers game implemented in Java using Swing library. This project features complete game logic with an intuitive graphical interface, time tracking for each player, and all standard checkers rules.
## ✨ Features
Core Game Mechanics:
- 🔄 Two-player game (white vs black)
- 🎯 Complete checkers logic - pawn moves, mandatory captures. chain captures
- 👑 Pawn promotion to queen - when reaching the opposite end
- ⏱️ Time tracking for each player
- 🎮 Intuitive mouse controls - drag and drop

UI:

- 🎨 Aesthetic board with traditional colors
- 💡 Piece highlighting for selected pieces
- 📈 Information panel displaying:
  - Current player turn
  - Game time for each player
  - Number of remaining pawns and queens
  - Forced capture status

## 🛠️ Technologies

- Java 8+ - main programming language
- Java Swing - graphical user interface
- Java AWT - 2D graphics and event handling
- JUnit5 - unit testing framework
- AssertJ - fluent assertion library

## 🚀 Installation and Setup
Requirements:

- Java 8 or higher
- Maven 3.6+

Steps:

Clone the repository:
```
git clone https://github.com/DawidZawadzkipl/Checkers
cd checkers-java
```
Build the project using Maven:
```
mvn clean compile
```
Run the application
```
java -cp target/classes main.Main
```
## 🧪 Testing
The project includes unit tests for game logic:
```
# Run all tests with detailed output
mvn test

# Run tests and generate reports
mvn clean test surefire-report:report

# View test reports in: target/surefire-reports/
```
## 📜 License
This project is licensed under the MIT License.
