package prog3.tp;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainForm implements View
{
    private Presenter presenter;
    private JFrame frame;
    private JTextField inputUsuario;
    private JLabel mensajeFinal;

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
            UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme");
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
        frame.getContentPane().setLayout(null);

        JLabel label1 = new JLabel("Introduzca nombre: ");
        label1.setBounds(153, 82, 147, 52);
        frame.getContentPane().add(label1);

        inputUsuario = new JTextField();
        inputUsuario.setBounds(153, 132, 124, 21);
        frame.getContentPane().add(inputUsuario);
        inputUsuario.setColumns(10);

        JButton button1 = new JButton("Aceptar");
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                mensajeFinal.setText("Usuario " + inputUsuario.getText()
                        + " se la come.");
            }
        });
        button1.setBounds(153, 165, 124, 27);
        frame.getContentPane().add(button1);

        mensajeFinal = new JLabel("");
        mensajeFinal.setBounds(153, 53, 285, 17);
        frame.getContentPane().add(mensajeFinal);
    }

    @Override
    public void setPresenter(Presenter presenter)
    {
        this.presenter = presenter;
    }
}
