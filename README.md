# 🌱 POOB vs Zombies
# 🕹️ About the Project

POOB vs Zombies is a Java-based game inspired by Plants vs Zombies, developed as a final project for the Object-Oriented Programming course at the Escuela Colombiana de Ingeniería Julio Garavito (5th semester).
The project simulates strategic gameplay where players use plants to defend against waves of zombies, implementing OOP principles such as inheritance, encapsulation, polymorphism, and abstraction.

# 🎯 Project Objectives

Apply object-oriented design patterns to a real-world inspired game.

Implement domain, presentation, and logic layers following clean architecture principles.

Use JUnit for testing and ensure high code coverage (87.6%).

Analyze code quality using PMD static analysis.

Develop a maintainable and scalable codebase using Java and Swing for the GUI.

# 🧩 Project Structure
src/
 ┣ 📂 domain/        → Core game logic (plants, zombies, player, level, board, etc.)
 ┣ 📂 presentation/  → Graphical interface built with Java Swing
 ┣ 📂 music/         → Sound and music management
 ┣ 📂 utils/         → Utility classes and helpers
 ┗ 📜 Main.java      → Entry point of the game
 
# 🔍 Code Analysis

Static Analysis (PMD):
Identified and corrected issues related to code style, documentation, and performance.
Improved maintainability and adherence to Java best practices.
(See Informe de analisis estatico Final.docx)

Dynamic Analysis (JUnit Coverage):
Achieved 87.6% code coverage through comprehensive testing of classes such as BrainManager, Tablero, ZombieCubeta, Jugador, and others.
(See Informe de analisis dinamico POOBVsZombies.docx)

# 🧠 Key Features

Plant and zombie classes with unique abilities.
Turn-based attack and defense logic.
Evolutionary plants that increase power over time.
Timer-based mechanics for resource generation.
Defensive and offensive strategies for both players.
Functional GUI that allows player interaction.

# 🧪 Testing

The project includes multiple unit tests (JUnit) to ensure game stability:
Entity behavior validation (plants, zombies, players).
Board interaction and collision logic.
Time-based mechanics and evolution cycles.
Edge cases for invalid moves and resource handling.

# 🧰 Technologies Used

Language: Java
IDE: IntelliJ IDEA / Eclipse
Testing Framework: JUnit
Static Analysis Tool: PMD
UML Modeling: Astah UML
Version Control: Git & GitHub

# 👨‍💻 Developed by
- Camilo Andrés Fernández Díaz
- Andrés Jacobo Sepúlveda Sánchez
Under the guidance of professors Juan Sebastián Frasica Galeano and María Irma Díaz Rozo.
