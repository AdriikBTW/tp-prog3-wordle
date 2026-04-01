package prog3.tp;

import java.util.List;

interface View {
    public void setPresenter(Presenter p);

    public void updateView(List<String> lines, Integer attempts);
}
