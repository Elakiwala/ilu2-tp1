package villagegaulois;

public class VillageSansChefException extends NullPointerException{
	private static final long serialVersionUID = 1L;
	
	public VillageSansChefException() {
	}
	
	public VillageSansChefException(String message) {
		super(message);
	}
	
	public VillageSansChefException(Throwable cause) {
		super();
	}
	
	public VillageSansChefException(String message, Throwable cause) {
		super(message);
	}
}
