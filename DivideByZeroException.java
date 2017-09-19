
/**
 * @author  Thomas Coll 
 * StudentNumber 
 * C00204384 
 * @version 2.0
 */
public class DivideByZeroException extends Exception 
{
	private static final long serialVersionUID = 1L;
	
	/**
	 *  Defines the error as attempting to divide by 0
	 */
	public DivideByZeroException()
    {
		
		super( "Attempted to Divide by Zero" );
    }
   public DivideByZeroException( String message )
   {
	   super( message );
   }
}
