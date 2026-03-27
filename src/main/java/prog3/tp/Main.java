package prog3.tp;

public class Main {
    public static void main(String[] args) {
        Game wordle = new Game();

        wordle.newGuess("perro");
        wordle.newGuess("pasto");
        wordle.newGuess("posta");

        System.out.println(wordle.toString());
    }
}
