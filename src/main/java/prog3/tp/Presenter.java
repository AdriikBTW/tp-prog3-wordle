package prog3.tp;

import java.util.List;

class Presenter implements Observer {
    private final Model _model;
    private final View _view;

    public Presenter(Model model, View view) {
        _model = model;
        _view = view;

        _view.setPresenter(this);
        _model.addObserver(this);
    }

    public void newGuess(String guess) {
        _model.newGuess(guess);
    }

    @Override
    public void update() {
        // NOTE: really is all this better than just passing Guess to view?
        List<Guess> history = _model.getHistory();
        String[] words = new String[history.size()];
        LetterStatus[][] statusList = new LetterStatus[history.size()][];

        for (int i = 0; i < words.length; i++) {
            words[i] = history.get(i).getString();
            statusList[i] = history.get(i).getStatus();
        }

        _view.updateView(words, statusList, _model.getAttempts());
    }

	public void checkGameStatus(String guess) {
		int status = _model.checkGameStatus(guess);
		
		switch(status) {
			case 0:
				_view.showWinMessage();
				break;
			
			case 1: 
				_view.showLoseMessage();
				break;
				
		}
	}
	
	public void restartGame() {
		_model.restart();
	}
}
