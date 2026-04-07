package prog3.tp;

import java.util.List;

interface Model {
    public void addObserver(Observer observer);

    public boolean isThereAttemptsLeft();

    public void newGuess(String guess);

    public List<Guess> getHistory();

    public Integer getAttempts();

	public int checkGameStatus(String guess);

	public void restart();

	public int getTime();

	public void setTime(int i);
}
