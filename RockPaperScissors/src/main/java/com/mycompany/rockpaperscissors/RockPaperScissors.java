/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rockpaperscissors;
import java.util.Scanner;
/**
 *
 * @author nastarangh
 */
public class RockPaperScissors {
    
    public static void main(String[] args){
        //initialize variables
        int currRound;
        int numRounds;
        int userWins, ties, compWins;
        
        //starting main game loop
        while(true){
            numRounds=0;
            currRound=1;
            Scanner input = new Scanner(System.in);
            System.out.println("How many rounds do you wish to play? (Maximum number of rounds = 10, minimum number of rounds = 1) ");
            		
           //check for correct input
            if(input.hasNextInt()){
                numRounds = input.nextInt();
                if(numRounds<1 || numRounds>10){
                    System.out.println("Invalid input. Closing the game...");
                    input.close();
                    System.exit(0);
                }
            }
            else {
                System.out.println("Invalid input. Closing the game...");
                input.close();
                System.exit(0);
            }
            
            //starting the current game loop
            userWins = 0; ties =0 ; compWins = 0;
            while (currRound <= numRounds){
                System.out.println("Round "+currRound+"- What is your move? (1=Rock, 2=Paper, 3=Scissors) ");
                if(input.hasNextInt()){
                    int userMove = input.nextInt();
                    if (userMove==1 || userMove==2 ||userMove==3){
                        int compMove = (int)((Math.random()*3)+1);
                        System.out.println("Computer chose "+compMove+".");
                        if(compMove==userMove){
                            System.out.println("It's a tie!");
                            ties++;
                            currRound++;
                        }
                        else if((compMove==1 && userMove==2)||(compMove==2 && userMove==3) || (compMove==3 && userMove==1)){
                            System.out.println("You win!");
                            userWins++;
                            currRound++;
                        }
                        else if((compMove==2 && userMove==1)||(compMove==3 && userMove==2) || (compMove==1 && userMove==3)){
                            System.out.println("Computer wins!");
                            compWins++;
                            currRound++;
                        }
                    }
                    else {
                        System.out.println("Invalid input.");
                    }
                }
                else {
                    System.out.println("Invalid input.");
                }
            }
            //displaying the final scores
            System.out.println("You have won "+userWins+" times, the Computer has won "+compWins+" times, and you and the Computer have "+ties+" ties.");
            if (userWins==compWins){
                System.out.println("You and the Computer have tied! The game is done now!");
            }
            else if (userWins>compWins){
                System.out.println("You win! The game is done now!");
            }
            else {
                System.out.println("Computer wins! The game is done now!");
            }
            
            //check if user wants to play another game
            String cont="";
            input.nextLine();
            while(true){
                System.out.println("Would you like to play again? Yes/No: ");
                cont=input.nextLine().toLowerCase();
                if(cont.equals("yes") || cont.equals("no")){
                    break;
                }
                else{
                    System.out.println("Invalid answer.");
                }
            }
            //end program if answer is no, and if it's yes then go to the start of the main game loop
            if (cont.equals("no")){
                System.out.println("Thanks for playing!");
                input.close();
                System.exit(0);
            }
        }
    }
}
