/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wiley.ngguessthenumber.dao;

import com.wiley.ngguessthenumber.dao.GameDao;
import com.wiley.ngguessthenumber.TestApplicationConfiguration;
import com.wiley.ngguessthenumber.dto.Game;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author nastarangh
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)

public class GameDaoDBTest {

    @Autowired
    GameDao gameDao;

    public GameDaoDBTest() {
    }


    @Test
    public void testAddGetGame() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setDone(false);
        game = gameDao.addGame(game);
        Game retrievedGame = gameDao.getGameById(game.getGameId());

        assertEquals(game, retrievedGame);
    }

    @Test
    public void testUpdateGame() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setDone(false);
        game = gameDao.addGame(game);
        Game retrievedGame = gameDao.getGameById(game.getGameId());
        assertEquals(game, retrievedGame);
        game.setDone(true);
        gameDao.updateGame(game);
        
        assertNotEquals(game, retrievedGame);
        retrievedGame = gameDao.getGameById(game.getGameId());
        assertEquals(game, retrievedGame);
    }
}
