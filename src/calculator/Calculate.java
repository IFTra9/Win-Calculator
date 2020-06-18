package calculator;

	import javax.swing.JTextField;


	/** @author UserAuthor: Ifat Traubas 
	 * 
	 * This Class handles calculator button clicks and performs 
	 *  required calculator operations.
	 * 
	 * @param Operand +,-,/,*,//=
	 * @param number1 1st number to calculate
	 * @param number2 2nd number to calculate
	 * @param DotFlag flag to indicate if there is already a dot ('.') in number
	 * @param PrevNumber2 = saves previous number2 used in previous calculation
	 * @param stage: indicate gui stage:
	 * 		  - stage1 handles input of 1st number and operand
	 * 		  - stage2 handles input of 2nd number,
	 * 			and '=' OR another operand, and performs required calculations
	 * @param CalculationEnded flag to indicate when a calculation ended by
	 * 			using '=' button (to differentiate with calculation ended by operand button
	 */
	public class Calculate {
		JTextField Stringbox = new JTextField(17);
		String Operand1="";
	    String number1="0";
	    String number2="";
	    String PrevNumber2="";
	    int DotFlag=0,stage=1, CalculationEnded=0;
		String result="";
	    
		/** 
		 * 
		 * This function handles calculator button clicks and performs 
		 *  required calculator operations.
		 * 
		 * @argument event is a String holds the button clicked in calculator GUI
		 * @argument DisplayText is the text shown on calculator display
		 * @return String DisplayText   calculator display string 
		 */
	    public String operate(String event, String DisplayText)
	    {	
	    	/* clear display and reset calculator if clicked 'C' button */
			if(event.equals("c"))
				return ClearAll();
				
			/* stage1 handles input of 1st number and operand */
			if (stage==1) {
					/* Dot ('.') can be used once per number */
			       if(event.equals(".") && DotFlag==0 ) {
			    	   DotFlag = 1;
			    	   if (number1 == "")
					    	number1 = number1+"0.";
			    	   else 
					    	number1 = number1+"."; 
				    return (number1);
			    }
			    /* clicked numeric button */
				if(event.equals("1")||event.equals("2")||event.equals("3")||event.equals("4")
				   ||event.equals("5")||event.equals("6")||event.equals("7")||event.equals("8")
				   ||event.equals("9")||event.equals("0")) {
						/* if this is the 1st numeric button clicked */
						if(DisplayText.equals("0"))
							number1=event;
						else
							number1=number1+event;
						return (number1);
				}
				/* clicked operand button */
				if(event.equals("+")||event.equals("-")||event.equals("/")||event.equals("*"))
				{
					/* if clicked operand without entering number before,
					 * behave as 1st number was 0 */
					if(number1.equals("")) 
						number1="0";
					stage=2; // mark that 1st stage done, switch to handle 2nd number
					DotFlag=0; // allow dot for 2nd number
					Operand1=event; // save operand for later calculation
					return (number1+" "+Operand1);
				}
				/* clicked '+/-' button */
				if(event.equals("+/-")) {				
					number1=CalcResult("+/-",number1);
					return (number1);
				}
				PrevNumber2 = number1;
				return (number1);
			/* stage2 handles input of 2nd number,
			 * and '=' OR another operand, and performs required calculations */	
			} else if(stage==2) {
				/* handle dot button */
	 		   if(event.equals(".")&&(DotFlag==0)) {
				  DotFlag=1;
				  if (number2 == "")
				    	number2 = number2+"0.";
		    	   else 
				    	number2 = number2+".";     
	     	     return (number1+" " +Operand1+" "+number2);
			   }    
			   if(event.equals("1")||event.equals("2")||event.equals("3")||event.equals("4")
				  ||event.equals("5")||event.equals("6")||event.equals("7")||event.equals("8")
				  ||event.equals("9") || event.equals("0")) {
				   /* if previous calculation ended by '=' button, then next numeric button in 
				    * Stage2 should be considered as Stage1 button (and kept in number1), and Operand should be reset */
				   if (CalculationEnded == 1) {
					   number1=event;
					   stage=1;
			   		   PrevNumber2 = number2;
			   		   Operand1="";
			   		   CalculationEnded = 0;
					   return (number1);
				   }
				   if(number2.equals("0"))
					   number2=event;
				   else
					   number2=number2+event;
			      return (number1+" " +Operand1+" "+number2);
			   }
			   if (event.equals("+/-")) {
				   if (number2.equals("")) {
					   /* if number2 is empty, that means calculation was ended,
					    * and even though we are in Stage2, we don't have number2 
					    * or operand yet, so handle only number1 */
					   number1=CalcResult("+/-",number1);
					   return (number1+" "+Operand1);
				   } else {
					   number2=CalcResult("+/-",number2);			   
					   return (number1+" " +Operand1+" "+number2);
				   }
			   }
			   if(event.equals("+")||event.equals("*")||event.equals("-")||
				  event.equals("/")||event.equals("=")) {
				   String ResultText;
				   /* handle a request to perform calculation operation */
				  if (event == "=") {
					  /* if didn't enter new number2, use the previous 
					   * number 2 from previous calculation 
					   * (or if 1st time, use number1) */
					  if(number2.equals("")) {
						  if(PrevNumber2=="")
							  number2=number1;
						  else 
							  number2 = PrevNumber2;
					  }
					  else 
						  PrevNumber2 = number2;
					  /* check division by 0 */
					  if (Operand1.equals("/") && number2.equals("0"))
							return DivisionByZero();
					  /* perform calculation */
					  result=CalcResult(Operand1,number1,number2);
					  ResultText = number1+" " +Operand1+" "+number2+ " = "+result;
					  /* mark calculation was ended by '=' button (and not operand) */
					  CalculationEnded = 1;
					  number1=result;
				  } else  { 
					  /* if number is empty, that may mean new operand button,\
					   * so update display */
					  if (number2 == "")
						  	ResultText = number1+" " + event;
					  else {
						  	/* check division by 0 */
						    if (Operand1.equals("/") && number2.equals("0"))
								return DivisionByZero();
						    PrevNumber2 = number2;
						    /* perform calculation */
						  	result=CalcResult(Operand1,number1,number2);
						  	ResultText = result+" " + event;
						  	number1=result;
					  }
					  /* save operand for next operation */
					  Operand1=event;
					  /* mark calculation was not ended by '=' button */
					  CalculationEnded = 0;
					  
				  }
				  /* enable dot for next number */
	              DotFlag=0;
	              number2="";
	              return ResultText;
			   }
			}
			return "";
		}  
	    
	    /* when division by zero use this to exit */
		public String DivisionByZero() {
			/* clear display and reset calculator if clicked 'C' button */
			ClearAll(); // ignore ClearAll return value and print other error 
			return ("wrong input");
		}
		
		/* clear display and reset calculator if clicked 'C' button */
		public String ClearAll() {
			DotFlag=CalculationEnded=0;
			stage=1;
			result=number1=number2=PrevNumber2=Operand1="";
			return ("0");
		}

		/** used to handle '+/-' request: multiply number by '-1' 
		 * 
		 * @param x convert string number to double
		 * 
		 * */
		public String CalcResult(String action,String number) {
			double x=Double.parseDouble(number);
			if (action.equals("+/-"))
			{
				x=x*(-1);
				if (!number.contains(".")) // if number didn't had a dot, return it as int
					number=String.valueOf((int)x);
				else
					number=String.valueOf(x);
			}
			return number;	
		}
		
		/** this function perform math calculations : +,-,/,*, and returns result in string 
		 * 
		 * @param double x equivalent to num1
		 * @param double y equivalent to num 2
		 * @
		 * 
		 * @Return  String ViewReasult contaion result of 2 numbers and 1 command
		 * 
		 * */
		public String CalcResult(String action,String num1,String num2){		
			String ViewResult="";
			Double x=Double.parseDouble(num1);
			Double y=Double.parseDouble(num2);
			if(action.equals("*"))
			{
				x=x*y;
				ViewResult=String.valueOf(x);	
			}
			else if(action.equals("+"))
			{
				x=x+y;
				ViewResult=String.valueOf(x);		
			}
			else if(action.equals("-"))
			{
				x=x-y;
				ViewResult=String.valueOf(x);
			}
			else if(action.equals("/"))
			{    
				x=x/y;
				ViewResult=String.valueOf(x);
			}
			if (action.equals("+/-"))
			{
				x=x*(-1);
				ViewResult=String.valueOf(x);
			}
			return ViewResult;	
		}
	}

