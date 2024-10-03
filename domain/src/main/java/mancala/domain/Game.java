package mancala.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Pit firstPit;
    private boolean gameStillRunning;
    private static final Logger logger = LogManager.getLogger(Game.class);

    public Game(int numPlayers, int numBowlsPerPlayer, List<Integer> startingStones){
        gameStillRunning = true;

        firstPit = new Pit(numPlayers*numBowlsPerPlayer,
                numBowlsPerPlayer, startingStones, names);
    }

    public void processMove(int pitNumber) {
        gameStillRunning = firstPit.isGameStillRunning();
        if (gameStillRunning) {
            if (firstPit.isLegalMove(pitNumber) && firstPit.canMakeMove(pitNumber))
                firstPit.playPit(pitNumber);
        }
        else
            calculateScores();
    }

    public Player getPlayerWithTurn() {
        return firstPit.getPlayerWithTurn();
    }

    Pit getFirstPit() {return firstPit;}

    Player getFirstPlayer() {return firstPit.getPlayer();}

    public boolean getIsGameStillRunning() {return gameStillRunning;}

    Kalaha getPitAt(int n) {
        return firstPit.getBowlAt(n);
    }

    public List<Player> getWinner() {
        if (gameStillRunning){
            logger.error("game is not finished yet");
            return null;
        }
        else {
            calculateScores();
            return findWinner();
        }
    }

    private List<Player> findWinner() {
        List<Player> winners = new ArrayList<>();
        if (firstPit.getPlayer().getState() == Player.STATE.WON)
            winners.add(firstPit.getPlayer());
        else if (firstPit.getPlayer().getState() == Player.STATE.LOST)
            winners.add(firstPit.getPlayer().getOpponent());
        else if (firstPit.getPlayer().getState() == Player.STATE.DRAW) {
            winners.add(firstPit.getPlayer());
            winners.add(firstPit.getPlayer().getOpponent());
        }
        return winners;
    }

    private void calculateScores() {
        firstPit.finishGame(0); // iteratively finishes game for all players
    }

}