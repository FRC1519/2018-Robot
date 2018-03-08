package org.mayheminc.robot2018.subsystems;

import java.util.Dictionary;
import java.util.HashMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class BlackBox extends Subsystem {

	HashMap<Button, Integer> m_map = new HashMap<Button, Integer>();
	
    public void initDefaultCommand() {}
    
    /**
     * Print the button presses to the driver station as errors.
     */
    public void print()
    {
    	for(Button b : m_map.keySet())
    	{
    		// form a string of "BlackBox: [button name]: [count]"
    		String str = "BlackBox: ";
    		str += b.getName();
    		str += ": ";
    		str += m_map.get(b);
    		
    		DriverStation.reportError(str, false);
    	}
    }
    
    /**
     * reset all the button press counts.
     */
    public void reset()
    {
    	for(Button b : m_map.keySet())
    	{
    		m_map.put(b, 0);
    	}
    }
    
    /**
     * Add a button to the blackbox.
     * @param button
     */
    public void addButton(String name, Button button)
    {
    	button.setName(name);
    	m_map.put(button,  0);
    	
    	// when the button is pressed, run the command.
    	button.whenPressed(new BlackBoxButtonPress(this, button));
    }
    
    private void tallyButtonPress(Button button)
    {
    	Integer i = m_map.get(button);
    	i++;
    	m_map.put(button, i);
    }
    
    private class BlackBoxButtonPress extends Command
    {
    	BlackBox m_blackBox;
    	Button m_button;
    	
    	public BlackBoxButtonPress(BlackBox blackBox, Button button)
    	{
    		m_blackBox = blackBox;
    		m_button = button;
    	}
    	
    	protected void initialize() 
    	{
    		m_blackBox.tallyButtonPress(m_button);
    	}
    	
    	protected void execute() {}
    	protected boolean isFinished() {return true;}
    	protected void end() {}
    	protected void interrupted() {}
    }
}

