package prog3.tp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class MainForm implements View
{
    private Presenter presenter;
    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable() {
            public void run()
            {
                try {
                    MainForm window = new MainForm();
                    window.frame.setVisible(true);
                    Game game = new Game();
                    new Presenter(game, window);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public MainForm()
    {
        try {
            UIManager.setLookAndFeel(
                    "com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme");
        } catch (Exception e) {
            System.out.println("Error setting native look: " + e);
        }
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize()
    {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel marginPanel = new JPanel();
        marginPanel.setLayout(new BorderLayout());
        marginPanel.setBorder(new EmptyBorder(50, 50, 50, 50));
        frame.add(marginPanel, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        marginPanel.add(mainPanel, BorderLayout.CENTER);

        JPanel guessPanel = new JPanel();
        guessPanel.setLayout(new BoxLayout(guessPanel, BoxLayout.Y_AXIS));

        JLabel guesses[] = new JLabel[5];
        for (int i = 0; i < 5; i++) {
            guesses[i] = new JLabel("label " + i);
            guesses[i].setAlignmentY(Component.CENTER_ALIGNMENT);
            guessPanel.add(guesses[i]);
        }

        mainPanel.add(guessPanel);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        JTextField inputTextField = new JTextField("", 10);
        JButton submitButton = new JButton("Submit");
        inputPanel.add(inputTextField);
        inputPanel.add(submitButton);

        mainPanel.add(inputPanel);

        frame.setVisible(true);

    }

    @Override
    public void setPresenter(Presenter presenter)
    {
        this.presenter = presenter;
    }
}
