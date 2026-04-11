package prog3.tp;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
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
    private CardLayout _cardLayout;
    private JPanel _marginPanel;
    private JPanel _gamePanel;
    private JPanel _menuPanel;
    private JPanel _mainPanel;
    private JPanel _infoPanel;
    private JPanel _guessPanel;
    private JPanel _inputPanel;
    private JLabel[][] _guess_grid;
    private JLabel _attempts;
    private JLabel _timerLabel;
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
        setUpMenuPanel();
        setUpMainPanel();
        setUpInfoPanel();
        setUpGuessPanel();
        setUpInputPanel();
        setUpAlphabetPanel();
        setUpGamePanel();

        _frame.add(_marginPanel, BorderLayout.CENTER);
        _marginPanel.add(_menuPanel, "menu");
        _marginPanel.add(_gamePanel, "game");
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
        _cardLayout = new CardLayout();
        _marginPanel.setLayout(_cardLayout);
        _marginPanel.setBorder(new EmptyBorder(50, 50, 50, 50));
    }

    private void setUpMenuPanel() {
        _menuPanel = new JPanel();
        _menuPanel.setLayout(new GridBagLayout());

        JButton startButton = new JButton("Jugar");
        _menuPanel.add(startButton);

        startButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        _cardLayout.show(_marginPanel, "game");
                    }
                });
    }

    private void setUpGamePanel() {
        _gamePanel = new JPanel(new BorderLayout());

        _gamePanel.add(_infoPanel, BorderLayout.NORTH);
        _gamePanel.add(_mainPanel, BorderLayout.CENTER);
        _gamePanel.add(_inputPanel, BorderLayout.SOUTH);
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
        _timerLabel = new JLabel("Time: 00:00");
        _infoPanel.add(_attempts);
        _infoPanel.add(_timerLabel);
    }

    private void setUpGuessPanel() {

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
		Object[] opciones = {"Reintentar", "Cerrar"};
		
		int selecction = JOptionPane.showOptionDialog(
		        null,
		        "Has ganado, ¿Qué querés hacer?",
		        "Fin del juego",
		        JOptionPane.YES_NO_OPTION,
		        JOptionPane.QUESTION_MESSAGE,
		        null,
		        opciones,
		        opciones[0]
		);

		if(selecction == 0) {
			_presenter.restartGame();
			cleanScreen();
		} else {
			System.exit(0);
		}
	}
	
	public void showLoseMessage(String secretWord) {
	    Object[] opciones = {"Reintentar", "Cerrar"};

	    int selecction = JOptionPane.showOptionDialog(
	        null,
	        "Perdiste. La palabra era: " + secretWord + "\n¿Qué querés hacer?",
	        "Fin del juego",
	        JOptionPane.YES_NO_OPTION,
	        JOptionPane.QUESTION_MESSAGE,
	        null,
	        opciones,
	        opciones[0]
	    );

	    if(selecction == 0) {
	        _presenter.restartGame();
	        cleanScreen();
	    } else {
	        System.exit(0);
	    }
	}


    @Override
    public void updateView(String[] words, LetterStatus[][] statusList, Integer attempts, Integer time) {
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
        
        int seconds = time % 60;
        int minutes = time / 60;

        String formatted = String.format("%02d:%02d", minutes, seconds);
        _timerLabel.setText("Time: " + formatted);
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
    
    public void cleanScreen() {
    	    for (int i = 0; i < _guess_grid.length; i++) {
    	        for (int j = 0; j < _guess_grid[i].length; j++) {
    	            _guess_grid[i][j].setText("");
    	            _guess_grid[i][j].setBackground(Color.DARK_GRAY);
    	        }
    	    }

    	    for (int i = 0; i < _alphabet_grid.length; i++) {
    	        _alphabet_grid[i].setBackground(Color.LIGHT_GRAY);
    	    }

    	    _attempts.setText("Attempts: 0/6");
    	    _timerLabel.setText("Time: 00:00");

    }

    @Override
    public void setPresenter(Presenter presenter) {
        _presenter = presenter;
    }
    
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(
            _frame,
            message,
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
