# Miniterm-Chatbot

## Introduction

This project is a chatterbot built by Abhimanyu Deora and Caleb Scott (NCSSM '15)
during the 2015 NCSSM Miniterm. Built in Java, the chatbot uses Markov modeling
to generate responses. 

Two 5th-order Markov models are used - one modeling the text forward, and the other 
modeling the text backwards. The Markov models are initially generated from a given
set of training data, and periodically re-generated from the user's inputs.

When a user's input is receieved, the following steps are taken to generate a response:

1. Extraneous words (the, an, a, etc.) stripped from response
2. Certain words transitioned to alternate forms (you -> I, your -> my, why -> because, etc.)
3. Keywords identified based on known list of verbs and nouns
4. Keywords used to seed Markov generation of set of candidate responses
5. Candidate responses evaluated based on number of keywords from original input present in response
6. Optimal response returned to user

## Included Files

* `ElizaImp3.java` - the core of the chatbot: processes user input, gets response from  Markov3.java, gets keywords from KeyFinder.java
* * `Markov3.java` - the Markov modeling engine: trains itself on given source data, generates responses based on given keywords, selects most appropriate response
* * `KeyFinder.java` - keyword identifier: identifies keys based on a given set of verbs and nouns
* * `SlangBanger.java` - slang identifier: an in-progress tool to generate associations between words and phrases in order to identify slang terms
* * `conversation-data.txt` - training data: a formatted list of possible responses - not used by the chatterbot
* * `conversation-data-single-line.txt` - training data: `conversation-data.txt` formatted onto a single line (`\n` characters replaced with spaces) - used by the chatterbot to train itself
* * `nouns.txt` - noun list: a list of nouns used by `KeyFinder.java` to identify keywords
* * `verbs.txt` - verb list: a list of verbs used by `KeyFinder.java` to identify keywords 
*
