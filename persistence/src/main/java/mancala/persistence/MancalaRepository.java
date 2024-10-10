package mancala.persistence;

import java.util.HashMap;

import mancala.domain.IMancala;

public class MancalaRepository implements IMancalaRepository {
    private HashMap<String, IMancala> saves;

    public MancalaRepository() {
        saves = new HashMap<>();
    }

    public void save(String saveName, IMancala saveGame){
        saves.put(saveName, saveGame);
    }

    public IMancala get(String saveName) {
        return saves.get(saveName);
    }
}