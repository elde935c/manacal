package mancala.domain;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    static Player player;
    int numBowlsPerPlayer = 7;
    int numPlayers = 2;
    int numBowls = numBowlsPerPlayer*numPlayers;
    static List<Integer> startingStones;
    static int pitStartingStones = 4;
    static ArrayList<String> names;


    @BeforeEach
    public void init() {
        startingStones = new ArrayList<>();
        for (int i=1; i<=numBowls; i++) {
            startingStones.add((i%numBowlsPerPlayer==0) ? 0 : pitStartingStones);
        }
        names = new ArrayList<>();
        names.add("Mario");
        names.add("Luigi");
        player = new Player(numPlayers, names);
    }

    @Test
    public void doesFirstPlayerHaveOpponent() {
        Player opponent = player.getOpponent();
        assertNotEquals(null, opponent);
    }

    @Test
    public void opponentOfOpponentIsPlayer() {
        Player opponent = player.getOpponent().getOpponent();
        assertEquals(player, opponent);
    }

    @Test
    public void firstPlayerIsTheSameAsThirdPlayer() {
        assertEquals(player.getPlayerAt(1), player.getPlayerAt(numPlayers+1));
    }

    @Test
    public void firstPlayerHasTurn() {
        assertTrue(player.getTurn());
    }

    @Test
    public void opponentDoesNotHaveTurn() {
        assertFalse(player.getOpponent().getTurn());
    }

    @Test
    public void firstPlayerShouldLooseTurn() {
        player.changeTurn();
        assertFalse(player.getTurn());
    }

    @Test
    public void opponentShouldGetTurn() {
        player.changeTurn();
        assertTrue(player.getOpponent().getTurn());
    }

    @Test
    public void playersPlaying() {
        assertEquals(Player.STATE.PLAYING, player.getState());
    }

    @Test
    public void opponentPlaying() {
        assertEquals(Player.STATE.PLAYING, player.getOpponent().getState());
    }

    @Test
    public void playerWonSoOtherPlayerLost() {
        player.setState(Player.STATE.WON);
        assertEquals(Player.STATE.LOST, player.getOpponent().getState());
    }

    @Test
    public void playerWon() {
        player.setState(Player.STATE.WON);
        assertEquals(Player.STATE.WON, player.getState());
    }

    @Test
    public void playerDrawSoOtherPlayerDraw() {
        player.setState(Player.STATE.DRAW);
        assertEquals(Player.STATE.DRAW, player.getOpponent().getState());
    }

    @Test
    public void playerDraw() {
        player.setState(Player.STATE.DRAW);
        assertEquals(Player.STATE.DRAW, player.getState());
    }

    @AfterEach
    public void teardown() {
        player = null;
        names = null;
    }
}