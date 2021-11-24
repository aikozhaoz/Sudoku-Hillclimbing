# Sudoku-Hillclimbing
-Instruction:
To run the program:
1. cd to the src folder
2. To compile: type command "javac HillClimbing.java"
3. To run : type command "java HillClimbing.java"

-Testing:
Contains 4 test files for testing purpose
To test: type command "test1.txt" or "test2.txt"

-What does this program do?
Read through the input file and create a 2D matrix from it
initial state: I made it so each row contains 1 - 4 without any duplicates
Feed successor function with initial state and loop through it.
sucessor function: swap values until it get to the min cost

Note: 
This program compiles and only finds local min.