****************
* P2: Nondeterministic Finite Automata
* CS361, Theory of comp
* 04/30/2026
*  Maria Gomez Baeza, Amy Lee, section 002
**************** 


## OVERVIEW

This program simulates a  determistic Turing Machine. It reads rules and input from a file, executes the machine on a bi-infinite tape(
infinite in both directions), and outputs the contents of all tape cells visited during execution.


## INCLUDED FILES
 * TMSimulator.java – the main class
 * TM.java – implements the Turing Machine
 * TMState.java – Defines constants for state labels 
 * Tape.java – Implements the bi-infinite tape 
 * README – This file

## COMPILING AND RUNNING 

To compile and run this project, please run the following commands

        1. Compile all java files in the current directory. Makes sure it is in the root directory first: 
             -  javac tm/*.java
        2. Then to test out the code  in your terminal on your top directly use this command to compile the provided test txt files. It should print out all the visited tapes/
             - java tm.TMSimulator  <input-file>

            An example of this is:
                    $ java tm.TMSimulator file0.txt
                    111111
          


## PROGRAM DESIGN AND IMPORTANT CONCEPTS


### Main concepts and organization
 The program is organized into four main classes: TMSimulator, TM, Tape, an TMState where:
 * TMSimulator- the main class and controls the program execution. It handles command-line input and runs the Turing machine simulation
 * TM - implements  the Turing machine. It is responsible for reading and parsing the file, storing all states and transitions, and running the simulation
 * Tape- represents the bi-infinite tape. It behaves like an infinite strip of cells extending in both directions via Hashmap. 
 * TMState-  A simple utility class that provides constants for state labels.

### Key concepts 

#### Turing Machine execution
 The main concept is to execute an efficient Turing Machine. It operates by preforming steps based on the current state and symbols read from the tape.In each step it:
  * reads the current symbol from the tape
  * finds the next transition
  * write a new symbol
  * moves head left or right
  * update state

  it then cycles throught the state into it reaches the halt state, where the execution ends.

#### Transition table
Another key concept is to define a transtion function to move symbols across a stripe of tape. Instead of using a 2d array to represent the transtion table, it uses 3 1D arrays.
* nextState - which state to go next
* writeSymbol - what symbol to write on the tape
* moveRight - which direction to move. Move right if true, left otherwise.

Using the 3 1D array allows the the program to visit each symbol faster as it is more simple then a 2D array as it is located in the same index. The arrays are ordered like this (nextState, writeSymbol, moveRight)


 #### State representation
The TMState is a helper class where the states are represented using integers instead of objects. The start state is always 0 and the halt state is always numStates - 1. This simplifies the design and makes it easier to compute transition.

##### Bi-infinite tape
The TUrning machine uses bi-infinite tape, where it can go either direction(left or right) infinitely. It is done using HashMap to store only the postion in the tape that are used.The tape  keeps track of the smallest (minVisted) and largest(maxVisted) positions the head visits. By doing this, the program only prints the part of the tape that was actually used. Which reduces run time and memory used.


## DISCUSSION
     Turning machine was a bit harder to understand then FA and PDAs,especilly on how we were going to impelement the bi-infitintite tape. Breaking the system into separate classes helped make the design more manageable and easier to understand.

     One challenge was to get to lower the runtime as one of the txt files had a large stimuation. To help with it, we used BufferedReader, that buffers characters to make it more efficent to read characters, arrays and lines. This helped lower the runtime needed to stimulate a turing machine.

     One issue I ran into was that  I had the incorrect transtion indexing which caused it to write the wrong symbol on the tape. At first I wrote return tapeSymbol * gammaSize + state; This flips the table which caused the transition to be stored incorrectly. Other issues was being off by transitionCount where it read an extra line where it was "< transitionCount", but was an easy fix by making it  "<= transitionCount". Other then that there were some compiling issues.
 
 
## TESTING

To test the program corrrectly, we used the provided txt files to stimulate the turing machines. It shows what txt input to read and desired output. Then we ran the stimulation to see what kind of output we are getting vs expected. We first used file0 as it was the smallest one. We then added breakpoints to see where the code broke and what bugs we ran into. Then we fixed a portion abd ran the stimulation. added more breakpoints and repeat into all the files were stimuated correctly.

## SOURCES

https://www.geeksforgeeks.org/theory-of-computation/turing-machine-in-toc/  - to help better understand turing machine

https://docs.oracle.com/javase/8/docs/api/java/io/BufferedReader.html - review on how bufferedReader works 








