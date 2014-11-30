package example.forum.dao.exceptions;

public class DataAccessException extends RuntimeException {

    private static final long serialVersionUID = 7460079212527391583L;

    public DataAccessException() {
        super();
    }

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(Throwable cause) {
        super(cause);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message,cause);
    }

}
