package prog3.tp;

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
        _view.updateView(_model.getHistory(), _model.getAttempts());
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
}
