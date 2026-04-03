package prog3.tp;

import java.util.List;

interface View {
    public void setPresenter(Presenter p);

    public void updateView(List<Guess> history, Integer attempts);

	public void showWinMessage();

	public void showLoseMessage();
}
