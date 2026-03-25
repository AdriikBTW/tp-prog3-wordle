package prog3.tp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game implements Model
{
    private List<Guess> _history;
    private List<Observer> _observers;
    private String _secretWord;
    private int _attempts;
    private static final int _MAX_ATTEMPTS = 6;
    private static final int _WORD_LEN = 5;

    public Game()
    {
        _history = new ArrayList<>();
        _observers = new ArrayList<>();
        _secretWord = "pasto";
        _attempts = 0;
    }

    public void addObserver(Observer observer)
    {
        _observers.add(observer);
    }

    private void notifyObservers()
    {
        for (Observer observer : _observers)
            observer.notify();
    }

    public boolean isThereAttemptsLeft()
    {
        return _attempts < _MAX_ATTEMPTS;
    }

    public void newGuess(String guess)
    {
        if (guess.length() != _WORD_LEN)
            throw new IllegalArgumentException(
                    "The string must contain 5 letters");

        guess.toLowerCase();
        LetterStatus status[] = new LetterStatus[_WORD_LEN];

        // default all grey
        for (int i = 0; i < _WORD_LEN; i++)
            status[i] = LetterStatus.GREY;

        // green letters
        for (int i = 0; i < _WORD_LEN; i++)
            if (_secretWord.charAt(i) == guess.charAt(i))
                status[i] = LetterStatus.GREEN;

        // yellow letters
        for (int i = 0; i < _WORD_LEN; i++) {
            if (status[i] == LetterStatus.GREEN)
                continue;
            char letter = guess.charAt(i);
            for (int j = 0; j < _WORD_LEN; j++) {
                if (i == j)
                    continue;
                if (_secretWord.charAt(j) == letter) {
                    status[i] = LetterStatus.YELLOW;
                    break;
                }
            }
        }


        _history.add(new Guess(guess, status));
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        Iterator<Guess> it = _history.iterator();

        sb.append("[DEBUG] The secret word is: ");
        sb.append(_secretWord);
        sb.append("\n");

        while (it.hasNext()) {
            sb.append(it.next());
            sb.append("\n");
        }

        return sb.toString();
    }
}
