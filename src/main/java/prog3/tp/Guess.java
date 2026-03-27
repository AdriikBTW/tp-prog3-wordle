package prog3.tp;

public class Guess
{
    private String _guess;
    private LetterStatus[] _status;

    public Guess(String guess, LetterStatus[] status)
    {
        _guess = guess;
        _status = status;
    }

    public Guess(Guess guess)
    {
        _guess = guess._guess;
        _status = guess._status;
    }

    public String getString()
    {
        return new String(_guess);
    }

    public LetterStatus[] getStatus()
    {
        return _status.clone();
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Word: ");
        sb.append(_guess);
        sb.append("\n");

        for (LetterStatus status : _status) {
            sb.append(status.toString());
            sb.append(" ");
        }

        return sb.toString();
    }
}
