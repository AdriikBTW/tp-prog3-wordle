package prog3.tp;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

// TODO: see how to test the randomness of secret word

public class GameTest {
    private Game _game;

    @Before
    public void setUp() throws Exception {
        _game = new Game();
    }

    @Test
    public void validGuessIncreaseAttemptsTest() {
        _game.newGuess("perro");
        assertEquals(1, _game.getHistory().size());
    }

    @Test
    public void invalidGuessDontIncreaseAttemptsTest() {
        _game.newGuess("astronauta");
        assertEquals(0, _game.getHistory().size());
    }

    @Test
    public void attemptsAvailableTest() {
        /* one attempt */
        _game.newGuess("pasto");
        assertTrue(_game.isThereAttemptsLeft());

        /* limit attempts */
        _game.newGuess("pasto");
        _game.newGuess("pasto");
        _game.newGuess("pasto");
        _game.newGuess("pasto");
        assertTrue(_game.isThereAttemptsLeft());
    }

    @Test
    public void attemptsNotAvailableTest() {
        for (int i = 0; i < 6; i++) _game.newGuess("perro");

        assertFalse(_game.isThereAttemptsLeft());
    }
}
