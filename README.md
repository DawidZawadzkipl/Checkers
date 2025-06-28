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

## 🚀 Installation and Setup
Requirements:

- Java 8 or higher
- IDE environment (IntelliJ IDEA, Eclipse) or javac compiler

Steps:

Clone the repository:
```
git clone https://github.com/DawidZawadzkipl/Checkers
cd checkers-java
```
Compile the project:
```
javac -d bin src/main/*.java src/piece/*.java
```

Run the game:
```
java -cp bin main.Main
```

Running in IDE:

- Import project into your IDE
- Make sure res/ folder is in classpath
- Run the main.Main class
## 📜 License
This project is licensed under the MIT License.
