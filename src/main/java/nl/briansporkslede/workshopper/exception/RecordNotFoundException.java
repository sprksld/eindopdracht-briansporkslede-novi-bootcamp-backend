package nl.briansporkslede.workshopper.exception;

public class RecordNotFoundException extends RuntimeException {
    //    private static final long serialVersionUID = 1L; todo
    public RecordNotFoundException() {
        super();
    }

    public RecordNotFoundException(String message) {
        super(message);
    }

}