package mancala.domain;

import java.util.ArrayList;

public class Player {
    private Player opponent;
    private boolean turn;
    enum STATE {
        WON,
        LOST,
        DRAW,
        PLAYING
    }
    private STATE state;
    private String name;


    public Player(int numPlayers, ArrayList<String> names) {
        turn = true;
        state = STATE.PLAYING;
        this.name = names.getFirst();
        names.removeFirst();
        makeNextPlayer(numPlayers, names, this);
    }

    private Player(int numPlayers, ArrayList<String> names, Player firstPlayer) {
        turn = false;
        state = STATE.PLAYING;
        this.name = names.getFirst();
        names.removeFirst();
        if (numPlayers > 1)
            makeNextPlayer(numPlayers, names, firstPlayer);
        else {
            this.opponent = firstPlayer;
        }
    }

    public String getName() {return this.name;}

    private void makeNextPlayer(int numPlayers, ArrayList<String> names, Player firstPlayer) {
        this.opponent = new Player(numPlayers - 1, names, firstPlayer);
    }

    Player getPlayerAt(int i) {
        if (i == 1) return this;
        else return opponent.getPlayerAt(i - 1);
    }

    public Player getOpponent() {
        return this.opponent;
    }

    public boolean getTurn() {
        return this.turn;
    }

    public void changeTurn() {
        if (this.turn) {
            this.turn = false;
            this.opponent.changeTurn();
        } else
            this.turn = true;
    }

    STATE getState() {return state;}

    void setState(STATE state) {
        this.state = state;
        if (opponent.getState() == STATE.PLAYING) {
            switch(state) {
                case STATE.WON: {
                    opponent.setState(STATE.LOST);
                    break;
                }
                case STATE.LOST: {
                    opponent.setState(STATE.WON);
                    break;
                }
                case STATE.DRAW: {
                    opponent.setState(STATE.DRAW);
                    break;
                }
            }
        }
    }

}
