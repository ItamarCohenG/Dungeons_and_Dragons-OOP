# **Dungeons and Dragons - OOP**

GitHub project link: [https://github.com/ItamarCohenG/Dungeons_and_Dragons-OOP](https://github.com/users/ItamarCohenG/projects/1/settings#)

This project involves developing a 2D turn-based game that draws inspiration from the classic Dungeons & Dragons.
The player navigates a map, engaging with enemies and exploring the environment.
The game features four player types, each with different abilities and resources, offering varied strategic options.
Levels are loaded dynamically from a directory, and players can easily add more levels themselves by creating simple text files. 
The game is implemented using Java, focusing on backend logic with a text-based user interface.

### **Key Principles:**

- Inheritance
- Polymorphism
- Encapsulation
- Abstraction
- Composition
- Coupling
- Cohesion

### **Design Patterns Used:**

- Factory Pattern: The Factory pattern is utilized to create instances of enemies and player characters. This pattern allows the game to generate different types of units dynamically, ensuring that the creation logic is centralized and easily manageable. By using a factory, the game can easily extend the types of characters and enemies without modifying existing code, thus adhering to the Open/Closed Principle.

- Visitor Pattern: The Visitor pattern is employed to handle interactions between units, such as combat or other interactions. This pattern allows you to define new operations without changing the classes of the elements on which it operates.

## **How to Run the Game**

To get started, download the project and execute the JAR file via the command line. Here’s how you can do it:

#### **1. 	Open Command Line/Terminal:**

- On Windows, navigate to the directory where the JAR file is saved. You can either use the cd command, or simply right-click on the folder containing the JAR and select “Open in Terminal” from the menu. This sets your terminal’s directory to where the JAR file is located.
- On Mac, open Terminal and use the cd command to navigate to the folder where your JAR file resides.
- On Linux, open Terminal and similarly navigate to the folder using cd.

Examples:

- Windows: `cd C:\Users\user\Downloads\`
- Mac: `cd /Users/user/Downloads/`
- Linux: `cd /home/user/Downloads/`

#### **2. Run the JAR File:**

Execute the JAR with the necessary arguments, such as the directory containing your level files,
 using the following command: `java -jar hw3.jar "[NECESSARY ARGUMENTS]`
