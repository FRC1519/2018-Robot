package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ZZ_Auto2CubeFromRight extends SmartAutoChooserBase {
    /**
     * Based on the game data, get the auto routine to run to score on the switch first, then the scale.
     * @return
     */
    protected Command GetAutoroutine()
    {
    	boolean ourswitch = Robot.gameData.getNearSwitchOnRight();
    	boolean scale = Robot.gameData.getScaleOnRight();
    	
    	// switch on right, scale on right...
    	if( ourswitch && scale)
    	{
    		return new ZZ_StartRightRightSwitchRightScale();
    	}
    	// switch on left, scale on right...
    	if( !ourswitch && scale)
    	{
    		// if this is a qual match, get the switch first
    		if (DriverStation.getInstance().getMatchType() == DriverStation.MatchType.Qualification)
    		{
    			return new ZZ_StartRightLeftSwitchRightScale();
    		}
    		else // this is elim match, get the scale first. 
    		{
    			return new ZZ_StartRightRightScaleLeftSwitch();
    		}
    	}
    	// switch on right, scale on left
    	if( ourswitch && !scale)
    	{
    		return new ZZ_StartRightRightSwitchLeftScale();
    	}
    	
    	// switch on left, scale on left
    	return new ZZ_StartRightLeftSwitchLeftScale();
    }
}
