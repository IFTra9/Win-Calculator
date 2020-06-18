package calculator;

	
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

	/** Author: Ifat Traubas 
	 * 
	 * This class handles calculator GUI button clicks and button's actions listeners, 
	 * and triggers Calculator by sending clicked button to it
	 * 
	 * @param StringBox is the calculator display , using disabled text box 
	 * @param Panelnumbers/Panelactions are the calculator buttons
	 * @param handler is the Calculator class
	 */
	public class GuiCalc extends JFrame implements ActionListener{
		JTextField Stringbox;
	    JPanel Panelnumbers;
	    JPanel Panelactions;
	    Calculate handler;
      // JFrame me;
       
	    public GuiCalc () {
	    	super ("Calculator");
	    	initGame();
	    }
	    
	    public void initGame()
	    {
	        Stringbox = new JTextField(17);
	        Panelnumbers=new JPanel();
	        Panelactions=new JPanel();
	        handler = new Calculate();
	        Border blackline = BorderFactory.createLineBorder(Color.black);
           JButton[] NumberButtons=new JButton[12];
           JButton[] ActionButtons=new JButton[6];
           String[] b= {"1","2","3","4","5","6","7","8","9","0","+/-","."};
           String [] action= {"+","-","/","*","c","="};
           Stringbox.setText("0");
       
           /* create buttons and set action listeners */
	        for(int i=0;i<ActionButtons.length;i++) {
	        	ActionButtons[i]=new JButton(action[i]);
	        	ActionButtons[i].setSize(5,5);
	        	ActionButtons[i].setBackground(Color.gray);
	        	ActionButtons[i].setFont(new Font("David",Font.BOLD,18));
	        	ActionButtons[i].setForeground(Color.white);
	        	ActionButtons[i].setActionCommand(action[i]);
	        	ActionButtons[i].addActionListener(this); 
	        	Panelactions.add(ActionButtons[i]);   }
           /* create buttons and set number listeners */
	        for(int i=0;i<NumberButtons.length;i++) {
           	NumberButtons[i]=new JButton(b[i]);
               NumberButtons[i].setSize(5,5);
               NumberButtons[i].setBorder(blackline);
               NumberButtons[i].setActionCommand(b[i]);
               NumberButtons[i].addActionListener(this);
               NumberButtons[i].setBackground(Color.gray);
               NumberButtons[i].setFont(new Font("David",Font.BOLD,15));
               NumberButtons[i].setForeground(Color.white);
               Panelnumbers.add(NumberButtons[i]);    }

	        Stringbox.setPreferredSize(new Dimension(350,80));
	        Stringbox.setFont(new Font("David",Font.BOLD,20));
	        Stringbox.setDisabledTextColor(Color.black);
	        
	        Stringbox.setBackground(Color.cyan);
	        Stringbox.setEnabled(false); //disable option to type using keyboard*/

	        Panelactions.setLayout(new GridLayout(6,1,1,4));
	        Panelnumbers.setLayout(new GridLayout(4,3,5,5));
	        add(Panelnumbers, BorderLayout.CENTER);
	        add(Panelactions, BorderLayout.EAST);
	        add(Stringbox, BorderLayout.PAGE_START);   
	        setSize(370,320);
	        setVisible(true);
	    }

		public void actionPerformed(ActionEvent Event) {
			/* call Calculator operation on clicked button, and send current display text */
			String text = handler.operate(Event.getActionCommand(),Stringbox.getText());
			/* update display with result from calculator */
			Stringbox.setText(text);
		}
	}
