package platform.exception;


public class CodeNotFoundException extends Exception {
    public CodeNotFoundException(Integer id) {
        super(String.format("Code %d not found", id));
    }
}
