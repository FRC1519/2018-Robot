package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ZZ_Auto2CubeFromMid extends SmartAutoChooserBase {
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
    		return new ZZ_StartMidRightSwitchRightScale();
    	}
    	// switch on left, scale on right...
    	if( !ourswitch && scale)
    	{
    		// if this is a qual match, get the switch first
    		if (DriverStation.getInstance().getMatchType() == DriverStation.MatchType.Qualification)
    		{
    			return new ZZ_StartMidLeftSwitchRightScale();
    		}
    		else // this is elim match, get the scale first. 
    		{
    			return new ZZ_StartMidRightScaleLeftSwitch();
    		}
    	}
    	// switch on right, scale on left
    	if( ourswitch && !scale)
    	{
    		return new ZZ_StartMidRightSwitchLeftScale();
    	}
    	
    	// switch on left, scale on left
    	return new ZZ_StartMidLeftSwitchLeftScale();
    }
}
