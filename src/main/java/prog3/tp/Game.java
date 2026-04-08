package prog3.tp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Game implements Model {
    private List<Guess> _history;
    private List<Observer> _observers;
    private String _secretWord;
    private int _attempts;
    private List<String> _fileLines;
    private final int _MAX_ATTEMPTS = 6;
    private final int _WORD_LEN = 5;
    private int _time;

    public Game() {
        _history = new ArrayList<>();
        _observers = new ArrayList<>();
        _attempts = 0;
        

        try {
            _fileLines = Files.readAllLines(Paths.get("src/main/resources/spanish_words.txt"));
        } catch (IOException e) {
            e.printStackTrace();	
        }
        
        _secretWord = getRandomWord().toLowerCase();
    }



	public String getRandomWord() {
        Random rng = new Random();
        return _fileLines.get(rng.nextInt(_fileLines.size()));
    }

    public void addObserver(Observer observer) {
        _observers.add(observer);
    }

    private void notifyObservers() {
        for (Observer observer : _observers) observer.update();
    }

    public boolean isThereAttemptsLeft() {
        return _attempts < _MAX_ATTEMPTS;
    }

    public void newGuess(String guess) {
        if (guess.length() != _WORD_LEN) return;
        if (!isThereAttemptsLeft()) return;

        if (!_fileLines.contains(guess.toLowerCase())) return;
        
        guess = guess.toLowerCase();
        LetterStatus status[] = new LetterStatus[_WORD_LEN];
        boolean isLetterUsed[] = new boolean[_WORD_LEN];

        // default all grey
        for (int i = 0; i < _WORD_LEN; i++) status[i] = LetterStatus.GRAY;

        // green letters
        for (int i = 0; i < _WORD_LEN; i++)
            if (_secretWord.charAt(i) == guess.charAt(i)) {
                status[i] = LetterStatus.GREEN;
                isLetterUsed[i] = true;
            }

        // yellow letters
        for (int i = 0; i < _WORD_LEN; i++) {
            if (status[i] == LetterStatus.GREEN) continue;
            for (int j = 0; j < _WORD_LEN; j++) {
                if (!isLetterUsed[j] && _secretWord.charAt(j) == guess.charAt(i)) {
                    status[i] = LetterStatus.YELLOW;
                    isLetterUsed[j] = true;
                    break;
                }
            }
        }

        _attempts++;
        _history.add(new Guess(guess, status));
        notifyObservers();
    }

    public List<Guess> getHistory() {
        List<Guess> cloneHistory = new ArrayList<>();

        for (Guess guess : _history) cloneHistory.add(new Guess(guess));

        return cloneHistory;
    }

    public Integer getAttempts() {
        return _attempts;
    }
    
    public int checkGameStatus(String guess) {
    	if(guess.equals(this._secretWord)) {
    		return 	0;
    	}
    	if(!isThereAttemptsLeft()) {
    		return 1;
    	}
    	else return 2;
    }
    
    public void restart() {
   	 _attempts = 0;
     _history = new ArrayList<>();
     _time = 0;

        try {
            _fileLines = Files.readAllLines(Paths.get("src/main/resources/spanish_words.txt"));
        } catch (IOException e) {
            e.printStackTrace();	
        }

        _secretWord = getRandomWord().toLowerCase();
        notifyObservers();
       
   }
    
    public void setTime(int time) {
    	this._time = time;
    	notifyObservers();
    }
    
    public int getTime() {
    	return _time;
    }

    @Override
    public String toString() {
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
