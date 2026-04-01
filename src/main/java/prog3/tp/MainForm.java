package prog3.tp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class MainForm implements View {
    private Presenter _presenter;
    private JFrame _frame;
    private JPanel _marginPanel;
    private JPanel _mainPanel;
    private JPanel _infoPanel;
    private JPanel _guessPanel;
    private JPanel _inputPanel;
    private JLabel _guesses[];
    private JLabel _attempts;
    private static final int POS_X = 100;
    private static final int POS_Y = 100;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    /** Launch the application. */
    public static void main(String[] args) {
        EventQueue.invokeLater(
                new Runnable() {
                    public void run() {
                        try {
                            MainForm window = new MainForm();
                            window._frame.setVisible(true);
                            Game game = new Game();
                            new Presenter(game, window);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /** Create the application. */
    public MainForm() {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme");
        } catch (Exception e) {
            System.out.println("Error setting native look: " + e);
        }
        initialize();
    }

    /** Initialize the contents of the frame. */
    private void initialize() {
        setUpFrame();
        setUpMarginPanel();
        setUpMainPanel();
        setUpInfoPanel();
        setUpGuessPanel();
        setUpInputPanel();

        _frame.add(_marginPanel, BorderLayout.CENTER);
        _marginPanel.add(_infoPanel, BorderLayout.NORTH);
        _marginPanel.add(_mainPanel, BorderLayout.CENTER);
        _mainPanel.add(_guessPanel);
        _marginPanel.add(_inputPanel, BorderLayout.SOUTH);
    }

    private void setUpFrame() {
        _frame = new JFrame();
        _frame.setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.setLayout(new BorderLayout());
    }

    private void setUpMarginPanel() {
        _marginPanel = new JPanel();
        _marginPanel.setLayout(new BorderLayout());
        _marginPanel.setBorder(new EmptyBorder(50, 50, 50, 50));
    }

    private void setUpMainPanel() {
        _mainPanel = new JPanel();
        _mainPanel.setLayout(new BoxLayout(_mainPanel, BoxLayout.Y_AXIS));
    }

    private void setUpInfoPanel() {
        _infoPanel = new JPanel();
        _infoPanel.setLayout(new FlowLayout());
        _attempts = new JLabel("Attempts: 0/6");
        JLabel time = new JLabel("Time: 00");
        _infoPanel.add(_attempts);
        _infoPanel.add(time);
    }

    private void setUpGuessPanel() {
        _guessPanel = new JPanel();
        _guessPanel.setLayout(new BoxLayout(_guessPanel, BoxLayout.Y_AXIS));

        _guesses = new JLabel[6];
        for (int i = 0; i < _guesses.length; i++) {
            _guesses[i] = new JLabel(" ");
            _guesses[i].setAlignmentY(Component.CENTER_ALIGNMENT);
            _guesses[i].setFont(new Font("Sans-Serif", Font.PLAIN, 20));
            _guesses[i].setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
            _guessPanel.add(_guesses[i]);
        }
    }

    private void setUpInputPanel() {
        _inputPanel = new JPanel();
        _inputPanel.setLayout(new FlowLayout());
        JTextField inputTextField = new JTextField("", 10);
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        _presenter.newGuess(inputTextField.getText());
                        inputTextField.setText("");
                    }
                });
        _inputPanel.add(inputTextField);
        _inputPanel.add(submitButton);
    }

    @Override
    public void updateView(List<String> lines, Integer attempts) {
        for (int i = 0; i < lines.size(); i++) _guesses[i].setText(lines.get(i));
        this._attempts.setText("Attempts: " + attempts.toString() + "/6");
    }

    @Override
    public void setPresenter(Presenter presenter) {
        _presenter = presenter;
    }
}
