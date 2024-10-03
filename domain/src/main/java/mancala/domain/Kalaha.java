package mancala.domain;

import java.util.ArrayList;
import java.util.List;

public class Kalaha {
    protected int stones;
    protected Kalaha nextBowl;
    protected Player player;

    Kalaha() {}

    Kalaha(int numBowls, Pit rootBowl, int bowlsPerPlayer,
           Player player, List<Integer> startingStones, ArrayList<String> names) {
        if (!startingStones.isEmpty()) {
            this.stones = startingStones.getFirst();
            startingStones.removeFirst();
        }
        if (numBowls > 1) {
            makeNextBowl(numBowls, rootBowl, bowlsPerPlayer,
                    player, startingStones, names);
        }
        else this.nextBowl = rootBowl;
        this.player = player;
    }

    void makeNextBowl(int numBowls, Pit rootBowl,
                      int bowlsPerPlayer, Player player,
                      List<Integer> startingStones, ArrayList<String> names) {
        this.nextBowl = new Pit(numBowls-1, rootBowl,
                bowlsPerPlayer, player.getOpponent(), startingStones, names);
    }

    boolean isLegalMove(int n){return false;}

    Kalaha getBowlAt(int bowlNumber) {
        if (bowlNumber==1) return this;
        else return this.nextBowl.getBowlAt(bowlNumber-1);
    }

    int getStones() {
        return this.stones;
    }

    Kalaha getNextBowl() {return this.nextBowl;}

    Player getPlayer() {return this.player;}

    void passStones(int numStones, Player playingPlayer) {
        if (numStones > 0) {
            if (this.player.getTurn()){
                addStones(1);
                this.nextBowl.passStones(numStones - 1, playingPlayer);
            }
            else this.nextBowl.passStones(numStones, playingPlayer);
        }
    }

    Kalaha getKalaha() {
        return this;
    }

    void playPit(int n) {
        nextBowl.playPit(n);
    }

    void empty() {}

    void addStones(int numStones) {
        this.stones += numStones;
    }

    Kalaha getOppositeBowl() {
        return this;
    }

    boolean isGameStillRunning() {return false;}

    void finishGame(int totalScore) {
        this.stones += totalScore;
        if (nextBowl.hasStonesLeft()) {
            nextBowl.finishGame(0);
        }
        else {
            setPlayerState();
        }
    }

    boolean hasStonesLeft() {
        return false;
    }

    private void setPlayerState() {
        if (this.stones > nextBowl.getKalaha().getStones())
            player.setState(Player.STATE.WON);
        else if (this.stones < nextBowl.getKalaha().getStones())
            player.setState(Player.STATE.LOST);
        else if (this.stones == nextBowl.getKalaha().getStones())
            player.setState(Player.STATE.DRAW);
    }
}