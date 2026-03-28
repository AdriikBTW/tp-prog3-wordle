package prog3.tp;

import java.util.ArrayList;
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
        StringBuilder sb;
        List<Guess> history = _model.getHistory();
        List<String> lines = new ArrayList<>();

        for (Guess guess : history) {
            String word = guess.getString().toUpperCase();
            LetterStatus status[] = guess.getStatus();

            sb = new StringBuilder("<html>");
            for (int j = 0; j < word.length(); j++) {
                char c = word.charAt(j);
                String color = statusToString(status[j]);

                sb.append("<font color=").append(color).append(">").append(c).append("</font>");
            }
            sb.append("</html>");
            lines.add(sb.toString());
        }

        _view.updateView(lines);
    }

    private String statusToString(LetterStatus status) {
        switch (status) {
            case GREY:
                return "gray";
            case YELLOW:
                return "yellow";
            case GREEN:
                return "green";
            default:
                return "";
        }
    }
}
