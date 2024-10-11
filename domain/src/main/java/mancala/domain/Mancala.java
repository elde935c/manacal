package mancala.domain;

import java.util.ArrayList;
import java.util.Objects;

public class Mancala implements IMancala {

    private final Pit firstPit;

    public Mancala(String name1, String name2) {
        int numPlayers = 2;
        int numBowlsPerPlayer = 7;
        ArrayList<Integer>  startingStones = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        names.add(name1);
        names.add(name2);

        for (int i=0; i<numPlayers; i++) {
            for (int j = 1; j < numBowlsPerPlayer; j++) {
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
        if (Objects.equals(firstPit.getPlayer().getName(), name))
            return firstPit.getPlayer().getTurn();
        else if (Objects.equals(firstPit.getOppositeBowl().getPlayer().getName(), name))
            return firstPit.getOppositeBowl().getPlayer().getTurn();
        return false;
    }

    @Override
    public void playPit(int index) {
        firstPit.playPit((index % 7) + 1);
    }

    @Override
    public int getStonesForPit(int index) {
        return firstPit.getBowlAt(index+1).getStones();
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
        else if (firstPit.getPlayer().getState() == Player.STATE.DRAW)
            return Winner.DRAW;
        return Winner.NO_ONE;
    }
}
