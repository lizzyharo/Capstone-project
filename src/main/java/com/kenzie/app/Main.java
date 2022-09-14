package com.kenzie.app;

// import necessary libraries


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.app.CluesGame.CluesDTO;

import java.io.StringReader;
import java.util.Objects;

import java.util.Scanner;

public class Main {


    public static class RandomGenerator { // method to generate a random number
        int randomRangeLimit(int min, int max) {

            int random = (max - min) + 1;
            return (int) (Math.random() * random) + min;
        }
    }


    public static void main(String[] args) throws JsonProcessingException {

        final String QUESTIONS_URL = "https://jservice.kenzie.academy/api/clues";
        String responseStr;

        RandomGenerator rand = new RandomGenerator();

        int counter = 0;  // User score
        int questionCounter = 1; // Counter for questions
        boolean gamePlay = true;


        responseStr = CustomHttpClient.sendGET(QUESTIONS_URL);
        ObjectMapper objectMapper = new ObjectMapper();

        CluesDTO cluesDTO = objectMapper.readValue(responseStr, CluesDTO.class);




        //The final gameplay loop
        while (gamePlay){

            int randomNum = rand.randomRangeLimit(0, 100); // Using the first 100 questions/categories
            int setRandomNum = randomNum;
            
            System.out.println();
            System.out.println("Number: " + questionCounter + "/10");
            System.out.println("Category: " + cluesDTO.getClues().get(setRandomNum).getCategory().getTitle());
            System.out.println("Question: " + cluesDTO.getClues().get(setRandomNum).getQuestion());
            System.out.println("What is your answer? " + cluesDTO.getClues().get(setRandomNum).getAnswer()); // Printing out answer just for testing purposes
            Scanner scanner = new Scanner(System.in);   //creating scanner for userInput
            String userAnswer = scanner.nextLine();   //reading userInput
            questionCounter++;


            if (cluesDTO.getClues().get(setRandomNum).getAnswer().equalsIgnoreCase(userAnswer)) { // For correct answers ignoring upper & lower cases
                counter++;
                System.out.println("Correct! Your current score is: " + counter );


            } else { // Anything different is automatically wrong
                System.out.println("Wrong answer! Correct answer was: " + cluesDTO.getClues().get(setRandomNum).getAnswer());
                System.out.println("Current score is: " + counter);

            }
            if (questionCounter> 10){ // End of game after 10 rounds
                System.out.println();
                System.out.println("Congratulations! You finished with " + counter + " point(s)!");
                break;
            }


        }

    }
}



