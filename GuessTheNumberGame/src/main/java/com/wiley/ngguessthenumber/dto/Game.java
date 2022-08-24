/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wiley.ngguessthenumber.dto;


import java.util.Objects;

/**
 *
 * @author nastarangh
 */
public class Game {
    
    private int gameId;
    private String answer;
    private boolean done;

    public Game() {
    }

    public Game(String answer, boolean done) {
        this.answer = answer;
        this.done = done;
    }
        
    public Game(int gameId, String answer, boolean done) {
        this.gameId = gameId;
        this.answer = answer;
        this.done = done;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.gameId;
        hash = 47 * hash + Objects.hashCode(this.answer);
        hash = 47 * hash + (this.done ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Game other = (Game) obj;
        if (this.gameId != other.gameId) {
            return false;
        }
        if (this.done != other.done) {
            return false;
        }
        if (!Objects.equals(this.answer, other.answer)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Game{" + "gameId=" + gameId + ", answer=" + answer + ", finished=" + done + '}';
    }
   
}
