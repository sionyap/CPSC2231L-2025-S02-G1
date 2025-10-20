# CPSC2231L: Text Analysis Project
Last edit: 20 Oct 2025\
Text Analysis Project – Milestone 1

Course: CPSC 2231L – Programming Workshop Lab\
Semester: Fall 2025\
Institution: Fairfield University, School of Engineering and Computing

# Table of Contents
1. [Overview](https://github.com/sionyap/CPSC2231L-2025-S02-G1#overview)
2. [Project Structure](https://github.com/sionyap/CPSC2231L-2025-S02-G1#project-structure)
3. [How to Run](https://github.com/sionyap/CPSC2231L-2025-S02-G1#how-to-run)
4. [Example Output](https://github.com/sionyap/CPSC2231L-2025-S02-G1#example-output)
5. [Group Members](https://github.com/sionyap/CPSC2231L-2025-S02-G1#group-members)

## 📘 Overview

This project is part of the semester-long software development assignment for CPSC 2231L.
The goal of Milestone 1 is to build the foundation for a text analysis tool that preprocesses and analyzes multiple text articles about the same topic.

The program currently performs the following:

- Loads multiple text files (articles) from a folder.
- Removes stop words (common words like “and”, “the”, “but”, etc.).
- Calculates basic text statistics (total words, unique words, word frequency).
- Ranks words by their frequency of occurrence.

All preprocessing is done for one topic (folder) at a time.

## 🧱 Project Structure

The project uses an object-oriented architecture with four classes:

| File	| Description |
|-------|-------------|
| Main.java	| Entry point of the program. Handles user input and coordinates analysis of articles using a while loop to display a user interface menu. |
| FileLoader.java	| Loads all text files from a specified directory (topic folder), adding all within one topic to an ArrayList of Strings for `ArticleStats.java` to iterate through and analyze individually. |
| StopWords.java	| Stores and filters out common English stop words using the `removeStopWords(ArrayList<String> articleWords)` method from the article being iterated through in `ArticleStats.java`. |
| ArticleStats.java |	Through the main method, processes given text to calculate article statistics and, using `sortByRank(ArrayList<String> words, ArrayList<Integer> wordCounts)`, generate ranked word frequencies by aligning words to their frequencies across ArrayLists `words` and `wordCounts`. |

## ⚙️ How to Run

Place your article .txt files in a folder (one topic per folder).

Ensure the folder path is correctly referenced in Main.java.

Compile all Java files (as applicable):

```javac Main.java FileLoader.java StopWords.java ArticleStats.java```

Run the program:

```java Main.java```

The program will:
- Read all text files from the specified directory.
- Remove stop words.
- Display word counts, unique word counts, and top frequent words.

## 🧮 Example Output
![Sample output of "CPU" topic.](/assets/Milestone1_sample.png)

## 👥 Group Members

Nolan: Implemented FileLoader.java

Kevin: Implemented StopWords.java

Sion: Implemented ArticleStats.java and Main.java integration
