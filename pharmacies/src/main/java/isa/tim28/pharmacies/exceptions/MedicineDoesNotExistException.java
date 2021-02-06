package isa.tim28.pharmacies.exceptions;

public class MedicineDoesNotExistException extends Exception {

	private static final long serialVersionUID = 1L;

	public MedicineDoesNotExistException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MedicineDoesNotExistException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public MedicineDoesNotExistException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public MedicineDoesNotExistException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public MedicineDoesNotExistException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
