package mancala.domain;

import java.util.ArrayList;
import java.util.List;

public class Pit extends Kalaha {

    Pit(int numBowls, int bowlsPerPlayer, List<Integer> startingStones, ArrayList<String> names) {
        super();
        if (!startingStones.isEmpty()) {
            this.stones = startingStones.getFirst();
            startingStones.removeFirst();
        }
        this.player = new Player(numBowls/bowlsPerPlayer, names);
        makeNextBowl(numBowls, this, bowlsPerPlayer, player,
                startingStones, names);
    }

    Pit(int numBowls, Pit rootBowl, int bowlsPerPlayer,
        Player player, List<Integer> startingStones, ArrayList<String> names) {
        super(numBowls, rootBowl, bowlsPerPlayer, player, startingStones, names);
    }


    void makeNextBowl(int numBowls, Pit rootBowl,
                      int bowlsPerPlayer, Player player,
                      List<Integer> startingStones, ArrayList<String> names) {
        if ((numBowls-2) % bowlsPerPlayer == 0) {
            this.nextBowl = new Kalaha(numBowls - 1, rootBowl,
                    bowlsPerPlayer, player, startingStones, names);
        }
        else {
            this.nextBowl = new Pit(numBowls - 1, rootBowl,
                    bowlsPerPlayer, player, startingStones, names);
        }
    }

    @Override
    boolean isLegalMove(int n) {
        if (n<1) return false;
        else if (n==1) return true;
        else return nextBowl.isLegalMove(n-1);
    }

    boolean canMakeMove (int pitNumber) {
        return (getBowlAt(pitNumber).getStones() > 0);
    }

    @Override
    void playPit(int n) {
        if (getPlayer().getTurn()) {
            if (n==1) {
                this.play();
            }
            else {
                nextBowl.playPit(n-1);
            }
        }
        else {
            nextBowl.playPit(n);
        }
    }

    private void play() {
        if (this.stones > 0) {
            int numStonesTmp = this.stones;
            this.empty(); // empty must be done before passStones in case the stones are passed all the way around
            this.nextBowl.passStones(numStonesTmp, this.player);
        }
    }

    @Override
    void empty() {
        this.stones = 0;
    }

    @Override
    void passStones(int numStones, Player playingPlayer) {
        if (numStones==1) {
            if (this.stones == 0 && this.player.getTurn())
                stealStones();
            else this.stones ++;
            playingPlayer.changeTurn();
        }
        else if (numStones > 1) {
            this.stones++;
            this.nextBowl.passStones(numStones-1, playingPlayer);
        }
    }

    private void stealStones() {
        getKalaha().addStones(getOppositeBowl().getStones()+1);
        getOppositeBowl().empty();
    }

    @Override
    Kalaha getOppositeBowl() {
        return nextBowl.getOppositeBowl().getNextBowl();
    }

    Kalaha getKalaha() {
        return this.nextBowl.getKalaha();
    }

    @Override
    boolean isGameStillRunning() {
        if (player.getTurn()) {
            return hasStonesLeft();
        }
        return getOppositeBowl().isGameStillRunning();
    }

    @Override
    boolean hasStonesLeft() {
        if (this.stones > 0) return true;
        else return nextBowl.hasStonesLeft();
    }

    @Override
    void finishGame(int totalScore) {
        int numStonesTmp = this.stones;
        this.empty();
        nextBowl.finishGame(totalScore + numStonesTmp);
    }

    int getScore() {
        return getKalaha().getStones();
    }

    Player getPlayerWithTurn() {
        if (player.getTurn()) return player;
        else return player.getOpponent();
    }
}