/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wiley.ngguessthenumber.dao;

import com.wiley.ngguessthenumber.dto.Round;
import java.util.List;

/**
 *
 * @author nastarangh
 */

public interface RoundDao {
    
    List<Round> getAllRoundsByGameId(int gameId);
    
    Round getRoundById(int roundId);
    
    Round addRound(Round round);
    
}
