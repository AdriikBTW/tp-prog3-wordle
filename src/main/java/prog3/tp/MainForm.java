package prog3.tp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
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
    private JLabel[][] _guess_grid;
    private JLabel _attempts;
    private static final int POS_X = 100;
    private static final int POS_Y = 100;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private JPanel _alphabetPanel;
    private JLabel[] _alphabet_grid;

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
        setUpAlphabetPanel();

        _frame.add(_marginPanel, BorderLayout.CENTER);
        _marginPanel.setLayout(new BorderLayout());
        _marginPanel.add(_infoPanel, BorderLayout.NORTH);
        _marginPanel.add(_mainPanel, BorderLayout.CENTER);
        _marginPanel.add(_inputPanel, BorderLayout.SOUTH);
        _mainPanel.add(_guessPanel);
        _mainPanel.add(_alphabetPanel);
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
        _infoPanel.setBorder(new EmptyBorder(0,0,50,0));
        _attempts = new JLabel("Attempts: 0/6");
        JLabel time = new JLabel("Time: 00");
        _infoPanel.add(_attempts);
        _infoPanel.add(time);
    }

    private void setUpGuessPanel() {
        // TODO: make the grid layout to not scale with the window so it doesnt look so weird
        int rows = 6;
        int cols = 5;
        int hgap = 2;
        int vgap = 2;

        _guessPanel = new JPanel();
        _guessPanel.setLayout(new GridLayout(rows, cols, hgap, vgap));
        _guessPanel.setBorder(new EmptyBorder(0,0,0,0));
        _guessPanel.setPreferredSize(new Dimension(400, 400));
        _guessPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));

        _guess_grid = new JLabel[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JLabel label = new JLabel("", (int) Component.TOP_ALIGNMENT);
                label.setFont(new Font("Sans-Serif", Font.BOLD, 20));
                label.setOpaque(true);
                label.setBackground(Color.DARK_GRAY);
                label.setForeground(Color.WHITE);
                
                _guess_grid[i][j] = label;
                _guessPanel.add(label);
            }
        }
    }
    
    private void setUpAlphabetPanel() {
        int rows = 3;
        int cols = 3;
        int hgap = 1;
        int vgap = 5;	
        
        String alphabet = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
        
        _alphabetPanel = new JPanel();
        _alphabetPanel.setLayout(new GridLayout(rows, cols, hgap, vgap));
        _alphabetPanel.setBorder(new EmptyBorder(100,70,50,70));

        _alphabet_grid = new JLabel[27];

        for (int i = 0; i < alphabet.length(); i++) {
        	char letter = alphabet.charAt(i);
        

            JLabel label = new JLabel(String.valueOf(letter), SwingConstants.CENTER);
            label.setOpaque(true);
            label.setBackground(Color.LIGHT_GRAY);
            label.setForeground(Color.BLACK);
            label.setFont(new Font("Sans-Serif", Font.BOLD, 18));

            _alphabet_grid[i] = label;
            _alphabetPanel.add(label);
        }
    }

    private void setUpInputPanel() {
        _inputPanel = new JPanel();
        _inputPanel.setLayout(new FlowLayout());
        _inputPanel.setBorder(new EmptyBorder(0,0,0,0));
        JTextField inputTextField = new JTextField("", 10);
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        _presenter.newGuess(inputTextField.getText());
                        _presenter.checkGameStatus(inputTextField.getText());
                        inputTextField.setText("");
                    }
                });
        _inputPanel.add(inputTextField);
        _inputPanel.add(submitButton);
    }
    
	public void showWinMessage() {
		JOptionPane.showMessageDialog(null, "Ganaste, Eres un crack");
	}
	
	public void showLoseMessage() {
	    JOptionPane.showMessageDialog(null, "Perdiste, vuelve a intentarlo");
	}


    @Override
    public void updateView(String[] words, LetterStatus[][] statusList, Integer attempts) {
        for (int i = 0; i < words.length; i++) {
            String word = words[i].toUpperCase();
            LetterStatus[] status = statusList[i];

            for (int j = 0; j < word.length(); j++) {
                _guess_grid[i][j].setText(String.valueOf(word.charAt(j)));
                _guess_grid[i][j].setBackground(statusToString(status[j]));
            
               for (int x = 0; x < _alphabet_grid.length; x++) {
                    if (_alphabet_grid[x].getText().equals(String.valueOf(word.charAt(j)))) {
                        _alphabet_grid[x].setBackground(statusToString(status[j]));
                       break;
                   }
               } 
            }   
        }

        _attempts.setText("Attempts: " + attempts.toString() + "/6");
    }

    private Color statusToString(LetterStatus status) {
        switch (status) {
            case GRAY:
                return new Color(60, 60, 60);
            case YELLOW:
                return new Color(180, 160, 60);
            case GREEN:
                return new Color(80, 140, 78);
            default:
                return Color.BLACK;
        }
    }
    

    @Override
    public void setPresenter(Presenter presenter) {
        _presenter = presenter;
    }
}
