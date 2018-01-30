package org.mayheminc.robot2018.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GameData extends Subsystem {

    boolean OurSwitch;
    boolean Scale;
    boolean OpposingSwitch;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void Read()
    {
    	String gameData = DriverStation.getInstance().getGameSpecificMessage();
    	OurSwitch = (gameData.charAt(0) == 'R');
    	Scale = (gameData.charAt(0) == 'R');
    	OpposingSwitch = (gameData.charAt(0) == 'R');
    }
    public boolean GetOurSwitch() {return OurSwitch;}
    public boolean GetScale() {return Scale;}
    public boolean GetOpposingSwitch() {return OpposingSwitch;}
    
    public String toString()
    {
    	return "";
    }
    
}

