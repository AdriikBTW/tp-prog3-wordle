package prog3.tp;

import java.util.List;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class Presenter implements Observer {
    private final Model _model;
    private final View _view;
    private Timer _timer;

    public Presenter(Model model, View view) {
        _model = model;
        _view = view;

        _view.setPresenter(this);
        _model.addObserver(this);
        
        startTimer();
    }

    private void startTimer() {
		_timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e ) {
					int time = _model.getTime();
					_model.setTime(time+1);
					update();
			}
		}); 
		_timer.start();
		
	}
    
    public void stopTimer() {
    	if (_timer != null) {
    		_timer.stop();
    	}
    };

	public void newGuess(String guess) {
        _model.newGuess(guess);
    }

    @Override
    public void update() {
        if (_model instanceof Game) {
            Game game = (Game) _model;
            if (game.getLastErrorMessage() != null) {
                _view.showErrorMessage(game.getLastErrorMessage());
                game.clearLastErrorMessage(); // limpiar después de mostrar
                return;
            }
        }
    	
    	// NOTE: really is all this better than just passing Guess to view?
        List<Guess> history = _model.getHistory();
        String[] words = new String[history.size()];
        LetterStatus[][] statusList = new LetterStatus[history.size()][];

        for (int i = 0; i < words.length; i++) {
            words[i] = history.get(i).getString();
            statusList[i] = history.get(i).getStatus();
        }

        _view.updateView(words, statusList, _model.getAttempts(), _model.getTime());        
    }

	public void checkGameStatus(String guess) {
		int status = _model.checkGameStatus(guess);
		
		switch(status) {
			case 0:
				stopTimer();
				_view.showWinMessage();
				break;
			
			case 1: 
			
			    stopTimer();
			    if (_model instanceof Game) {
			        Game game = (Game) _model;
			        _view.showLoseMessage(game.getSecretWord());
			    }
			    break;
		}
	}
	
	public void restartGame() {
		_model.restart();
		startTimer();
	}
}
