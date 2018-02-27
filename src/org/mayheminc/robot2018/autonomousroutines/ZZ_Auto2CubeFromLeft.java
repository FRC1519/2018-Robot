package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This 2 Cube Auto starts on the left.  
 * Based on the scale/switch randomization, it chooses the best path.
 */
public class ZZ_Auto2CubeFromLeft extends SmartAutoChooserBase {
    /**
     * Based on the game data, get the auto routine to run to score on the switch first, then the scale.
     * @return
     */
    protected Command GetAutoroutine()
    {
    	boolean ourswitch = Robot.gameData.GetNearSwitch();
    	boolean scale = Robot.gameData.GetScale();
    	
    	// switch on right, scale on right...
    	if( ourswitch && scale)
    	{
    		return new ZZ_StartLeftRightSwitchRightScale();
    	}
    	// switch on left, scale on right...
    	if( !ourswitch && scale)
    	{
    		
    		return new ZZ_StartLeftLeftSwitchRightScale();
    	}
    	// switch on right, scale on left
    	if( ourswitch && !scale)
    	{
    		// if this is a qual match, get the switch first
    		if (DriverStation.getInstance().getMatchType() == DriverStation.MatchType.Qualification)
    		{
    			return new ZZ_StartLeftRightSwitchLeftScale();
    		}
    		else // this is elim match, get the scale first. 
    		{
    			return new ZZ_StartLeftLeftScaleRightSwitch();
    		}
    	}
    	
    	// switch on left, scale on left
    	return new ZZ_StartLeftLeftSwitchLeftScale();
    }
}
