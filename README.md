# CPSC2231L: Text Analysis Project

Last edit: 10 November 2025 9:24 PM\
Milestone 1-2 – Text Analysis Tool

Course: CPSC 2231L – Programming Workshop Lab\
Semester: Fall 2025\
Institution: Fairfield University, School of Engineering and Computing

# Table of Contents
1. [Overview](https://github.com/sionyap/CPSC2231L-2025-S02-G1#overview)
2. [Project Structure](https://github.com/sionyap/CPSC2231L-2025-S02-G1#project-structure)
3. [How to Run](https://github.com/sionyap/CPSC2231L-2025-S02-G1#how-to-run)
4. [Example Output](https://github.com/sionyap/CPSC2231L-2025-S02-G1#example-output)
5. [UML Diagram](https://github.com/sionyap/CPSC2231L-2025-S02-G1#uml-diagram)
6. [Group Members](https://github.com/sionyap/CPSC2231L-2025-S02-G1#group-members)

## 📘 Overview

This project is part of the semester-long software development assignment for CPSC 2231L.\
The goal of Milestone 1 is to build the foundation for a text analysis tool that preprocesses and analyzes multiple text articles about the same topic.\
The goal of Milestone 2 is to create a sentiment analysis; with these articles, the program is able to indicate generally whether they are positive, negative, or neutral.\
Additionally, an API call was implemented to gather articles from the Internet rather than prompting users to choose a topic or upload a particular file.

The program currently performs the following:

- Loads multiple text files (articles) from an API call.
- Removes stop words (common words like “and”, “the”, “but”, etc.).
- Calculates basic text statistics (total words, unique words, word frequency).
- Ranks words by their frequency of occurrence.
- Analyzes sentiment of the article (positive, negative, neutral) based on scoring of words' neutrality.

## 🧱 Project Structure

The project uses an object-oriented architecture with seven classes:

| File	| Description |
|-------|-------------|
| Main.java	| Entry point of the program; calls the method (from `AppController.java`) that runs sentiment analysis. |
| LexiconLoader.java	| NO LONGER USED, LARGELY REPLACED BY THE ResourceManager class |
| TextProcessor.java	| . |
| SentimentAnalysis.java |	. |
| ApiFetcher.java	| . |
| ResourceManager.java	| . |
| AppController.java	| - Manage the program flow from start to finish. 
|| - Uses the ApiFetcher to pull the articles from the API.
|| - Uses the TextProcessor to remove stop words from the article text.
|| - Load lexicon scores and words associated via ResourceManager.
|| - Calls SentimentAnalysis.java methods to compute:
|| -- Simple sentiment score
|| -- Lexicon-weighted score |

## ⚙️ How to Run

Run the program within an IDE:

```java Main.java```

The program will:
- Choose articles through the API call.
- Remove stop words.
- Display word counts, unique word counts, top frequent words, and whether the article was positive, negative, or neutral (with its scoring breakdown).

## 🧮 Example Output
![Sample output of refactored code for Milestone 2, involving an API call.](/assets/sample_M2.png)

## UML Diagram
![UML Diagram, Milestone 2.](/assets/UML_M2.png)

## 👥 Group Members

Nolan: Implemented ApiFetcher.java, AppController.java, ResourceManager.java

Kevin: Implemented LexiconLoader.java, SentimentAnalysis.java, UML diagram, README

Sion: Implemented TextProcessor.java, Main.java, UML diagram (remainder), README (remainder)
