package mancala.domain;

import java.util.ArrayList;
import java.util.List;

public class Mancala implements IMancala {

    private final Pit firstPit;

    public Mancala(ArrayList<String> names) {
        int numPlayers = 2;
        int numBowlsPerPlayer = 7;
        ArrayList<Integer>  startingStones = new ArrayList<Integer>();
        for (int i=0; i<numPlayers; i++) {
            for (int j = 0; j < numBowlsPerPlayer; j++) {
                startingStones.add(4);
            }
            startingStones.add(0);
        }

        firstPit = new Pit(numPlayers*numBowlsPerPlayer,
                numBowlsPerPlayer, startingStones, names);
    }

    @Override
    public String getNameOfPlayerOne() {
        return firstPit.getPlayer().getName();
    }

    @Override
    public String getNameOfPlayerTwo() {
        return firstPit.getPlayer().getOpponent().getName();
    }

    @Override
    public boolean isPlayersTurn(String name) {
        return false;
    }

    @Override
    public void playPit(int index) {
        firstPit.playPit(index);
    }

    @Override
    public int getStonesForPit(int index) {
        return firstPit.getBowlAt(index).getStones();
    }

    @Override
    public boolean isEndOfGame() {
        return !firstPit.isGameStillRunning();
    }

    @Override
    public Winner getWinner() {
        if (firstPit.getPlayer().getState() == Player.STATE.WON)
            return Winner.PLAYER_1;
        else if (firstPit.getPlayer().getState() == Player.STATE.LOST)
            return Winner.PLAYER_2;
        return Winner.DRAW;
    }
}
