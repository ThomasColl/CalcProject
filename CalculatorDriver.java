import javax.swing.JFrame;

/**
 * @author Thomas Coll
 * StudentNumber C00204384
 * @version 2.0
*/
public class CalculatorDriver
{
	public static void main(String[] args) 
	{
		/**
		 * @desc Defines the calculators frame, its size and location on the screen and its visibility 
		 *
		 */
		//Create the frame 
    	Calculator frame = new Calculator("Calculator");
    	frame.setSize(350, 400);
    	frame.setLocation(500,400);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setVisible(true);
  	}
}
