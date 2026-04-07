package prog3.tp;

interface View {
    public void setPresenter(Presenter p);

    public void updateView(String[] words, LetterStatus[][] statusList, Integer attempts, Integer time);

	public void showWinMessage();

	public void showLoseMessage();

	public void cleanScreen();
}
