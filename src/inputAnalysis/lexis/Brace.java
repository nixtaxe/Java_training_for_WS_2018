package inputAnalysis.lexis;

public class Brace implements Token {
    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public void set(String s) throws TokenError {
        //...
    }
}
