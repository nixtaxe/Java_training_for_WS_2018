package inputAnalysis.lexis;

public interface Token extends Cloneable {
    public boolean isValid();
    public void set(String s) throws TokenError;
    public String toString() throws TokenError;
}
