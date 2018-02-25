package inputAnalysis.lexis;

public class Number implements Token {
    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public void set(String s) throws TokenError {
        //...
    }
}
