package inputAnalysis.lexis;

public class Ident implements Token {
    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public void set(String s) throws TokenError {
        //...
    }
}
