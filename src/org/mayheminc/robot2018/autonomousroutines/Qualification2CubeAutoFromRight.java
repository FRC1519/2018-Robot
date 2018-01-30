package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Qualification2CubeAutoFromRight extends Command {
    boolean hasStarted = false;
    Command autoRoutine;
    	
    public Qualification2CubeAutoFromRight() {

    }

    /**
     * Based on the game data, get the auto routine to run to score on the switch first, then the scale.
     * @return
     */
    Command GetAutoroutine()
    {
    	boolean ourswitch = Robot.gameData.GetOurSwitch();
    	boolean scale = Robot.gameData.GetOurSwitch();
    	
    	if( ourswitch && scale)
    	{
    		return new RunRightSwitchRightScale();
    	}
    	if( !ourswitch && scale)
    	{
    		if (DriverStation.getInstance().getMatchType() == DriverStation.MatchType.Qualification)
    		{
    		return new RunLeftSwitchRightScale();
    	
    		}else {
    			return new RunRightScaleLeftSwitch();
    		}
    	}
    	if( ourswitch && !scale)
    	{
    		return new RunRightSwitchLeftScale();
    	}
    	return new RunLeftSwtichLeftScale();
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	hasStarted = false;
    	autoRoutine = GetAutoroutine();
    	autoRoutine.start();
    	DriverStation.reportWarning("Starting appropriate defense command: " + autoRoutine.getName(), false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(autoRoutine.isRunning()){
    		hasStarted = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (hasStarted && !autoRoutine.isRunning());
   }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Finished auto routine.");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.out.println("interrupted auto routine.");
    }
}
