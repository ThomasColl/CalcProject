

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;


/**
 * @author Thomas Coll
 * StudentNumber C00204384
 * @version 2.0
*/
public class Calculator extends JFrame 
{
	private static final long serialVersionUID = 1L;
	/**
	 * Create the engine and engine manager
	 */
	private ScriptEngineManager mgr = new ScriptEngineManager();
	private ScriptEngine engine = mgr.getEngineByName("JavaScript");
	
	/**
	 * Create the arrays for the buttons
	 */
	private String[] numArray = {"7","8","9","4","5","6","1","2","3","0","."};
	private String[] functArray = {"+","-","*","/"};
	private String[] compArray = {"AC","="};
	private String[] memoryArray = {"M+","M-","MC","MR"}; //to add
	
	/**
	 *  Define the string needed for the engine to evaluate 
	 */
	private String inputString = "";
	
	/**
	 *  Define the first and ansSet value
	 */
	private boolean first = true;
	private boolean ansSet = false;
	
	/**
	 *  define i (used for for loops) and memoryNum (0)
	 */
	private int i;
	private double memoryNum = 0;
	
	/**
	 *  Define the button arrays number, function, computations and memory
	 */
	private JButton[] number = new JButton[11];
	private JButton[] function = new JButton[4];
	private JButton[] computations = new JButton[2];
	private JButton[] memory = new JButton[4];
	
	/**
	 *  Create the screen
	 */
	private JTextArea screen = new JTextArea(1, 20);
	
	/**
	 *  Create the button listener
	 */
	private ButtonListener listener = new ButtonListener();
	


	/**
	 *  Adds the number buttons via an array and for loop
	 *  numberPanel, defines a panel to hold all the buttons
	 */
	public void addNum()
	{
		
		JPanel numberPanel = new JPanel();
		numberPanel.setLayout(new GridLayout(4,3, 2, 2));
		for(i = 0; i < numArray.length; i++)
		{
			number[i] = new JButton(numArray[i]);
			number[i].setPreferredSize(new Dimension(10, 10));
			numberPanel.add(number[i]);
			number[i].addActionListener(listener);
		}
		numberPanel.add(new JButton());
		add("Center", numberPanel);
	}

	//Adds the function buttons via an array
	/**
	 *  Adds the function buttons via an array and for loop
	 *  functionPanel, defines a panel to hold all the buttons
	 */
	public void addFunc()
	{
		
		JPanel functionPanel = new JPanel();
		functionPanel.setLayout(new GridLayout(4,1));
		for(i = 0; i < functArray.length; i++)
		{
			function[i] = new JButton(functArray[i]);
			function[i].setPreferredSize(new Dimension(60, 60));
		    functionPanel.add(function[i]);
		    function[i].addActionListener(listener);
		}
		add("East", functionPanel);
	}
	
	//Add the computation buttons via an array
	/**
	 *  Adds the computation buttons via an array and for loop
	 *  compPanel, defines a panel to hold all the buttons
	 */
	public void addComp()
	{
		
		JPanel compPanel = new JPanel();
		compPanel.setLayout(new GridLayout(1,2));
		for(i = 0; i < compArray.length; i++)
		{
			computations[i] = new JButton(compArray[i]);
			computations[i].setPreferredSize(new Dimension(30, 30));
		    compPanel.add(computations[i]);
		    computations[i].addActionListener(listener);
		}
		add("South", compPanel);
	}
	
	//Adds the memory buttons via an array
	/**
	 *  Adds the memory buttons via an array and for loop
	 *  memPanel, defines a panel to hold all the buttons
	 */
	public void addMem()
	{
		
		JPanel memPanel = new JPanel();
		memPanel.setLayout(new GridLayout(4,1));
		for(i = 0; i < memoryArray.length; i++)
		{
			memory[i] = new JButton(memoryArray[i]);
			memory[i].setPreferredSize(new Dimension(60, 60));
			memPanel.add(memory[i]);
			memory[i].addActionListener(listener);
		}
		add("West", memPanel);
	}
	
	/**
	 *  Adds the display screen with a custom design to the calculator 
	 *  displayPanel, defines a panel to hold all the buttons
	 */
	public void addDisplay()
	{
		
		JPanel displayPanel = new JPanel();
		displayPanel.setLayout(new GridLayout(2,1));
		Font f = new Font(Font.SANS_SERIF, 13, 20);
		screen.setEditable(false);
		screen.setFont(f);
		screen.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		screen.setBorder(BorderFactory.createCompoundBorder(border, 
		            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		displayPanel.add(screen);
		add("North", displayPanel);
	}
	

	/**
	 * Calls all the add methods
	 */
	public void build()
	{
		addNum();
		addFunc();
		addComp();
		addDisplay();
		addMem();
	}
	
	/**
	 * @param title, the title calculator assigned in the driver class
	 * Creates the title and defined the layout
	 */
	public Calculator(String title) 
	{
	    super(title);
	    setLayout(new BorderLayout(1, 1));
		
		build();
		
	}
	
	/**
	 * perform the arithmetic calculation and throw any errors that occur
	 * result is an object used to store the engines result
	 * inputNum is the parsed version of inputString after evaluation
	 * The function checks if the string is empty, if not then it attempts the functions and throws errors based on this
	 * Errors catches implemented are no input error, script error and divide by zero exception
	 * The inputString is defined to have the answer, whether it is an error message or the answer
	 * @throws DivideByZeroException IF you divide by zero
	 * @return inputString 
	 */
	public String calculate() throws DivideByZeroException
	{
		Object result = null;
		double inputNum;
		if(inputString != "")
		{
			try 
			{
				result = engine.eval(inputString);
				inputString = result.toString();
				inputNum = Double.parseDouble(inputString);
				if(inputNum == Double.longBitsToDouble(0x7ff0000000000000L))
				{
					throw new DivideByZeroException();
				}
				return inputString;
        		
		    }
			//divide by zero error, input error
			catch (ScriptException e) 
			{
		        System.out.println("Error evaluating input string.");
		        screen.setText("Error Parsing input");
		        inputString = "";
        		screen.setText(inputString);
        		first = true;
		    }
		}
		else
		{
			System.out.println("ERROR: No input");
	        screen.setText("");
	        inputString = "ERROR: No input";
    		first = true;
		}

		return inputString;
	}
	
	
	class ButtonListener implements ActionListener 
  	{
		/**
		 * @desc The reader compares the input to the buttons available and sees if they're a match, if so they perform the specific function
		 */
  		public void actionPerformed(ActionEvent evt) 
    	{
  			
  			
  			 // NUMBERS
  			 
  			if(evt.getSource() == number[0])
    		{
      			//Number 7
      			//If the variable first is true then define the string as "7" and set the screen to show as such, then define first as false
      			//Else add it to the string and display the inputString
      			if(first)
    			{
    				
    				inputString = "7";
    				screen.setText(inputString);	
        			first = false;
    			}
    			else
    			{
    				inputString += "7";
    				screen.setText(inputString);
    			}
    		}
    		else if(evt.getSource() == number[1])
    		{
    			/*
      			 * @Param Number 8
      			 * If the variable first is true then define the string as "8" and set the screen to show as such, then define first as false
      			 * Else add it to the string and display the inputString
      			 */
    			if(first)
    			{
    				inputString = "8";
    				screen.setText(inputString);	
        			first = false;
    			}
    			else
    			{
    				inputString += "8";
        			screen.setText(inputString);
    			}
    		}
    		else if(evt.getSource() == number[2])
    		{
    			/*
      			 * Number 9
      			 * If the variable first is true then define the string as "9" and set the screen to show as such, then define first as false
      			 * Else add it to the string and display the inputString
      			 */
    			if(first)
    			{
    				inputString = "9";
    				screen.setText(inputString);
        			first = false;
    			}
    			else
    			{
    				inputString += "9";
    				screen.setText(inputString);
    			}
    		}
    		else if(evt.getSource() == number[3])
    		{
    			/*
      			 * Number 4
      			 * If the variable first is true then define the string as "4" and set the screen to show as such, then define first as false
      			 * Else add it to the string and display the inputString
      			 */
    			if(first)
    			{
    				inputString = "4";
    				screen.setText(inputString);	
        			first = false;
    			}
    			else
    			{
    				inputString += "4";
    				screen.setText(inputString);
    			}
    		}
    		else if(evt.getSource() == number[4])
    		{
    			/*
      			 * Number 5
      			 * If the variable first is true then define the string as "5" and set the screen to show as such, then define first as false
      			 * Else add it to the string and display the inputString
      			 */
    			if(first)
    			{
    				inputString = "5";
    				screen.setText(inputString);	
        			first = false;
    			}
    			else
    			{
    				inputString += "5";
    				screen.setText(inputString);
    			}
    		}
    		else if(evt.getSource() == number[5])
    		{
    			/*
      			 * Number 6
      			 * If the variable first is true then define the string as "6" and set the screen to show as such, then define first as false
      			 * Else add it to the string and display the inputString
      			 */
    			if(first)
    			{
    				inputString = "6";
    				screen.setText(inputString);	
        			first = false;
    			}
    			else
    			{
    				inputString += "6";
    				screen.setText(inputString);	
    			}
    		}
    		else if(evt.getSource() == number[6])
    		{
    			/*
      			 * Number 1
      			 * If the variable first is true then define the string as "1" and set the screen to show as such, then define first as false
      			 * Else add it to the string and display the inputString
      			 */
    			if(first)
    			{
    				inputString = "1";
    				screen.setText(inputString);	
        			first = false;
    			}
    			else
    			{
    				inputString += "1";
    				screen.setText(inputString);	
    			}
    		}
    		else if(evt.getSource() == number[7])
    		{
    			/*
      			 * Number 2
      			 * If the variable first is true then define the string as "2" and set the screen to show as such, then define first as false
      			 * Else add it to the string and display the inputString
      			 */
    			if(first)
    			{
    				inputString = "2";
    				screen.setText(inputString);	
        			first = false;
    			}
    			else
    			{
    				inputString += "2";
    				screen.setText(inputString);
    			}
    		}
    		else if(evt.getSource() == number[8])
    		{
    			/*
      			 * Number 3
      			 * If the variable first is true then define the string as "3" and set the screen to show as such, then define first as false
      			 * Else add it to the string and display the inputString
      			 */
    			if(first)
    			{
    				inputString = "3";
    				screen.setText(inputString);	
        			first = false;
    			}
    			else
    			{
    				inputString += "3";
    				screen.setText(inputString);	
    			}
    		}
    		else if(evt.getSource() == number[9])
    		{
    			/*
      			 * Number 0
      			 * If the variable first is true then define the string as "0" and set the screen to show as such, then define first as false
      			 * Else add it to the string and display the inputString
      			 */
    			if(first)
    			{
    				inputString = "0";
    				screen.setText(inputString);	
        			first = false;
    			}
    			else
    			{
    				inputString += "0";
    				screen.setText(inputString);
    			}
    		}
    		else if(evt.getSource() == number[10])
    		{
    			/*
      			 * Symbol .
      			 * If the variable first is true then define the string as "." and set the screen to show as such, then define first as false
      			 * Else add it to the string and display the inputString
      			 */
    			if(first)
    			{
    				inputString = ".";
    				screen.setText(inputString);	
        			first = false;
    			}
    			else
    			{
    				inputString += ".";
    				screen.setText(inputString);
    			}
    		}
    		
    		/*
    		 *  FUNCTIONS    		
    		 */
    		
    		else if(evt.getSource() == function[0])
    		{
    			/*
    			 *  Function Add
    			 *  If the first and andSet vars are true then merely add the "+" to the string and display and set first to false
    			 *  If not then add it to the string and display inputString on the screen
    			 */
    			if(ansSet && first)
    			{
    				inputString = inputString + "+";
    				screen.setText(inputString);
    				first = false;
    			}
    			else
    			{
    				inputString += "+";
        			screen.setText(inputString);
    			}
    		}
    		//Subtraction
    		else if(evt.getSource() == function[1])
    		{
    			/*
    			 *  Function Subtract
    			 *  If the first and andSet vars are true then merely add the "-" to the string and display and set first to false
    			 *  If not then add it to the string and display inputString on the screen
    			 */
    			if(ansSet && first)
    			{
    				inputString = inputString + "-";
    				screen.setText(inputString);
    				first = false;
    			}
    			else
    			{
    				inputString += "-";
        			screen.setText(inputString);
    			}
    		}
    		else if(evt.getSource() == function[2])
    		{
    			/*
    			 *  Function Multiplication
    			 *  If the first and andSet vars are true then merely add the "*" to the string and display and set first to false
    			 *  If not then add it to the string and display inputString on the screen
    			 */
    			if(ansSet && first)
    			{
    				inputString = inputString + "*";
    				screen.setText(inputString);
    				first = false;
    			}
    			else
    			{
    				inputString += "*";
        			screen.setText(inputString);
    			}
    		}
    		else if(evt.getSource() == function[3])
    		{
    			/*
    			 *  Function Division
    			 *  If the first and andSet vars are true then merely add the "*" to the string and display and set first to false
    			 *  If not then add it to the string and display inputString on the screen
    			 */
    			if(ansSet && first)
    			{
    				inputString = inputString + "/";
    				screen.setText(inputString);
    				first = false;
    			}
    			else
    			{
    				inputString += "/";
        			screen.setText(inputString);
    			}
    		}
    		
    		/*
    		 *  MEMORY
    		 */
    		
    		else if(evt.getSource() == memory[0])
    		{
    			/*
    			 *  Memory Add
    			 *  Try to parse the string and set first to true along with adding the new memoryNum to the inputString and displaying it
    			 *  Catch any number formatting exceptions 
    			 */
    			try
    			{
    				memoryNum += Double.parseDouble(inputString);
        			first = true;
            		inputString = "" + memoryNum;
            		screen.setText(inputString);   				
    			}
    			catch(NumberFormatException a)
    			{
    				inputString = "Error parsing int";
        			screen.setText(inputString);
        			first = true;
    			}
    		}
    		//Memory Subtract
    		else if(evt.getSource() == memory[1])
    		{
    			/*
    			 *  Memory Subtract
    			 *  Try to parse the string and set first to true along with subtracting the new memoryNum to the inputString and displaying it
    			 *  Catch any number formatting exceptions, if the string is empty declare it to be an empty string exception, else call it a parsing error
    			 */
    			try
    			{
    				memoryNum -= Double.parseDouble(inputString);
	    			first = true;
	        		inputString = "" + memoryNum;
	        		screen.setText(inputString);
    			}
    			catch(NumberFormatException a)
    			{
    				if(inputString == "")
    				{
    					screen.setText("ERROR: no input to read");
    				}
    				else
    				{
        				screen.setText("Error Parsing input");
    				}
    			}
    		}
    		else if(evt.getSource() == memory[2])
    		{
    			/*
    			 *  Memory Clear
    			 *  Set the memoryNum to 0
    			*/
    			memoryNum = 0;
    		}
    		else if(evt.getSource() == memory[3])
    		{
    			/*
    			 *  Memory Subtract
    			 *  Try to paste the memoryNum to the screen
    			 *  Catch any number formatting exceptions, if the string is empty declare it to be an empty string exception, else call it a parsing error
    			 */
    			try
    			{
    				inputString += memoryNum;
        			screen.setText(inputString);
    			}
    			catch(NumberFormatException a)
    			{
    				if(inputString == "")
    				{
    					screen.setText("No input to read");
    				}
    				else
    				{
        				screen.setText("Error Parsing input");
    				}
    			}
    		}
    		
    		/*
    		 *  COMPUTATIONS
    		 */
    		
    		else if(evt.getSource() == computations[0])
    		{
    			/*
    			 * Clear button
    			 * Sets first to true
    			 * Sets ansSet to false
    			 * Clears the inputString and the screen
    			 */
    			first = true;
    			ansSet = false;
    			inputString = "";
    			screen.setText("");
    		}
    		else if(evt.getSource() == computations[1])
    		{
    			/*
    			 * Equals button
    			 * Try and define the inputString as the result of the calculate() function
    			 * Set the screen to display the inputString
    			 * Set first and ansSet to true
    			 * Catch any divide by zero exceptions
    			 * If an error occurs then display the error via the inputString
    			 * set first to true and ansSet to false
    			 */
    			try
    			{
    				inputString = calculate();
    				screen.setText(inputString);
            		first = true;
            		ansSet = true;
    			}
    			catch(DivideByZeroException e)
    			{
    				inputString = "Tried to divide by Zero";
    				screen.setText(inputString);
    				inputString = "";
            		first = true;
            		ansSet = false;
    			}
    		}
    		
    	}
  	}
}