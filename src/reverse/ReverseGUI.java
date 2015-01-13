/*
 * Author: Daniel Wester
 * 
 * Date: 10/04/2012
 * 
 * Course: Comp 2247-01, Fall 2012
 * 
 * Assignment: PGM1
 * 
 * Description: This program will reverse all the words in a sentence.
 * When a sentence is being reversed the string will be broken into tokens.
 * Each token will then have each character added onto a stack from the last 
 * character to the first character. After that the stack in emptied into
 * a string making the word reverse while checking for certain characters
 * to excluded. None of the authors code was changed.
 * 
 * Big O Analysis: For everything except the ReverseWordButton method the
 * has a O(1). For ReverseWordButton worse case it has a O(n^2), to prevent
 * it from having a O(n^3) I built the Array Stack so it will never need to 
 * expand.
 */

package reverse;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ReverseGUI extends JFrame {
	
	final char PERIOD = ('.');
	final char COMMA = (',');
	final char SEMICOLON = (';');
	final char COLON = (':');
	final char EXCLAMATION_MARK = ('!');
	final char QUESTION_MARK = ('?');
	
	JTextArea descriptionJTA, inputJTA, outputJTA;
	JLabel inputJL, outputJL;
	JButton runJB, resetJB, exitJB;
	JPanel northPanel, centerPanel, centerPanelTop, centerPanelTopJL, 
		centerPanelTopJTA, centerPanelBottom, centerPanelBottomJL, 
		centerPanelBottomJTA, southPanel;
	
	public ReverseGUI() {
		
		//Description Fields
		descriptionJTA = new JTextArea(" Welcome, this program will the " +
				"reverse words you type.\n It will not reverse the " +
				"followning punctuation within the parenthesis: (" + 
				PERIOD + " " + COMMA + " " + SEMICOLON + " " + COLON + " " +
				EXCLAMATION_MARK + " " + QUESTION_MARK + ")");
		descriptionJTA.setLineWrap(true);
		descriptionJTA.setEditable(false);
		descriptionJTA.setBackground(null);
		inputJL = new JLabel("Input the sentence you want reversed");
		outputJL = new JLabel("This is the sentence reversed");
		
		//Input and output Fields
		inputJTA = new JTextArea();
		inputJTA.setEditable(true);
		inputJTA.setLineWrap(true);
		inputJTA.addFocusListener(new FocusJTAHandler());
		outputJTA = new JTextArea();
		outputJTA.setEditable(false);
		outputJTA.setLineWrap(true);
		outputJTA.setBackground(Color.WHITE);
		
		//The buttons
		runJB = new JButton ("Reverse");
		runJB.addActionListener(new ReverseWordButton());
		resetJB = new JButton ("Reset");
		resetJB.addActionListener(new ResetButtonHandler());
		exitJB = new JButton ("Exit");
		exitJB.addActionListener(new ExitButtonHandler());
		
		
		//Building the GUI
		northPanel = new JPanel(new GridLayout (1,1));
		centerPanel = new JPanel(new GridLayout(2,1));
		centerPanelTop = new JPanel(new GridLayout(2,1));
		centerPanelTopJL = new JPanel(new FlowLayout());
		centerPanelTopJTA = new JPanel(new GridLayout (1,1));
		centerPanelBottom = new JPanel(new GridLayout(2,1));
		centerPanelBottomJL = new JPanel(new FlowLayout());
		centerPanelBottomJTA = new JPanel(new GridLayout (1,1));
		southPanel = new JPanel(new FlowLayout (FlowLayout.CENTER));
		
		northPanel.add(descriptionJTA);
		centerPanelTopJL.add(inputJL);
		centerPanelTopJTA.add(inputJTA);
		centerPanelTop.add(centerPanelTopJL);
		centerPanelTop.add(centerPanelTopJTA);
		centerPanelBottomJL.add(outputJL);
		centerPanelBottomJTA.add(outputJTA);
		centerPanelBottom.add(centerPanelBottomJL);
		centerPanelBottom.add(centerPanelBottomJTA);
		centerPanel.add(centerPanelTop);
		centerPanel.add(centerPanelBottom);
		southPanel.add(runJB);
		southPanel.add(resetJB);
		southPanel.add(exitJB);
		
		//Display the GUI
		setLayout(new BorderLayout());
		setSize(450,250);
		setTitle("Reverse Words Program");
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);
		setVisible(true);
		
	}
	
	
	private class ReverseWordButton implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			
			//This will prevent the stack from ever expanding
			ArrayStack<Character> stack = new ArrayStack<Character>
				(inputJTA.getText().length()); 
			
			StringTokenizer st = new StringTokenizer(inputJTA.getText());
			String output = "", reverseWord = "";
			char inputChar;
			
		    while (st.hasMoreTokens()) {
		    	//The token is being put into a string.
		        String token = st.nextToken();
		        reverseWord = "";
		    	
		        //This will put each character into a stack.
		        for (int i = token.length() - 1;i >= 0;i--){
		        	stack.push(token.charAt(i));
		        }
		        
		        //Taking the characters off the stack and checking exclusions
		        while (!(stack.isEmpty())){
		        	inputChar = stack.pop();
		        
			        switch(inputChar) {
				        default:
				        	reverseWord = inputChar + reverseWord;
				        	break;
				        case PERIOD:
				        case COMMA:
				        case SEMICOLON:
				        case COLON:
				        case EXCLAMATION_MARK:
				        case QUESTION_MARK:
				        	reverseWord = reverseWord + inputChar;
				        	break;
			        }
		        }
		        
		        output = output + reverseWord;
		        
		        if (st.hasMoreTokens())
		        	output = output + " ";
		    }
		        
			if (output == "") {
				outputJTA.setText("Nothing was entered.");
			} else
				outputJTA.setText(output);
			inputJTA.requestFocus();
		}
	}
	
	//Reset Button
	private class ResetButtonHandler implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			inputJTA.setText("");
			outputJTA.setText("");
			inputJTA.requestFocus();
		}
	}
	
	//Exit Button
	private class ExitButtonHandler implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			System.exit(0);
		}
	}
	
	//Focus to select all the text in JTA
	private class FocusJTAHandler extends FocusAdapter {
		public void focusGained(FocusEvent fe) {
			JTextArea text = (JTextArea) fe.getSource();
			text.selectAll();
		} public void focusLost(FocusEvent fe) {
		
		}
	}

	//Main
	public static void main(String[] args){
		new ReverseGUI();		
	}
}

