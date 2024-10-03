package mancala.domain;

public class MancalaFactory implements IMancalaFactory{


    @Override
    public IMancala createNewGame(String namePlayer1, String namePlayer2) {
        return new Mancala(namePlayer1, namePlayer2);
    }
}
