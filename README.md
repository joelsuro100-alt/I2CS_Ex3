# ᾠ Smart Pacman AI Algorithm (Ex3)

> An autonomous Pacman agent based on BFS pathfinding, capable of navigating complex cyclic mazes and surviving high-speed chases.

![Java](https://img.shields.io/badge/Language-Java-orange) ![Algorithm](https://img.shields.io/badge/Algorithm-BFS-blue) ![Performance](https://img.shields.io/badge/Level-4-green)

## 📖 About The Project

This project implements an algorithmic logic for the Pacman game (Ex3 Assignment). The goal was to create a smart agent (`Ex3Algo`) that can analyze the game board in real-time, calculate the optimal path to food, avoid obstacles, and survive against ghosts.

The implementation focuses on efficiency and robustness, allowing the Pacman to perform well even at high speeds (**dt=100ms**) and high difficulty levels (**Level 4 with 4 Ghosts**).

## 🚀 Key Features & Performance

* **Breadth-First Search (BFS):** Uses a custom `Map` class to calculate the shortest distance from the Pacman to every pixel on the board instantly.
* **Cyclic World Navigation:** Fully handles "wrap-around" maps where the Pacman can exit one side of the screen and appear on the other.
* **Smart Targeting:** Dynamically prioritizes the closest edible target (Pink or Green food) to maximize score efficiency.
* **Performance:** Successfully clears **Level 4**, managing 4 ghosts at a fast game tick rate of `100ms`.

---

## 🎥 Demo

### Watch the Pacman in Action
[[Click here to watch the gameplay video](https://youtube.com/shorts/OgIEpB-HZTg?feature=share)]([[INSERT_VIDEO_LINK_HERE](https://youtube.com/shorts/OgIEpB-HZTg?feature=share)]

### Success Screenshot
![Level 4 Success DT 100]
<img width="1072" height="1246" alt="Screenshot 2026-01-20 193521" src="https://github.com/user-attachments/assets/8ba7fd61-459f-41bd-b7de-841d2ded59a6" />




---

## 🧠 How It Works

The algorithm is split into two main components:

### 1. `Map.java` (The Core Logic)
This class converts the game board into a navigable grid.
* **`allDistance(start, obstacle)`**: Implements BFS to create a "distance map". It floods the board from the Pacman's position, marking the distance to every reachable cell.
* **`shortestPath(start, end, obstacle)`**: Uses the distance map to backtrack from the target to the start, reconstructing the optimal path.
* **`normalize()` & `toMove()`**: Helper functions that handle the **cyclic coordinates** math, ensuring the algorithm understands that the left edge connects to the right edge.

### 2. `Ex3Algo.java` (The Brain)
The main controller loop:
1.  **Parse Board:** Reads the current game state and creates a `Map` object.
2.  **Scan Targets:** Iterates through the board to find all Pink and Green food.
3.  **Calculate:** Uses `allDistance` to find the strictly closest reachable target.
4.  **Execute:** Calculates the `shortestPath` to that target and sends the next move command.

---

## 🛠️ Installation & Running

You can run the game using the provided JAR file.

### Prerequisites
* Java Runtime Environment (JRE) installed.

### Run Command
Open your terminal/CMD in the project folder and run:

```bash
java -jar Ex3.jar <ID> <Level_Number>
