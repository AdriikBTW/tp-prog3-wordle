package prog3.tp;

interface View {
    public void setPresenter(Presenter p);

    public void updateView(String[] words, LetterStatus[][] statusList, Integer attempts);

	public void showWinMessage();

	public void showLoseMessage();

	public void cleanScreen();
}
