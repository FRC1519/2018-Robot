package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.subsystems.Pivot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeInAndLiftTheCube extends Command {
	    public IntakeInAndLiftTheCube() {
	        super();
	    }

	    protected void initialize() {
	    	Robot.intake.takeInCube();
	    }
	    
	    protected void execute()
	    {
	    	// if the pivot is down and the cube is in, lift it.
	    	if( Robot.pivot.getCurrentPosition() < Pivot.DOWNWARD_POSITION + Pivot.PIVOT_TOLERANCE &&
	    		Robot.cubeDetector.isCubeSquare() )
	    	{
	    		Robot.pivot.setDesiredPosition(Pivot.UPRIGHT_POSITION);
	    	}
	    	// if the pivot is up, turn off the intake.
	    	if( Robot.pivot.getCurrentPosition() > Pivot.UPRIGHT_POSITION - Pivot.PIVOT_TOLERANCE )
	    	{
	    		Robot.intake.stop();
	    	}
	    }
	    protected boolean isFinished() {return false;}
	    protected void end()
	    {
	    	Robot.intake.stop();
	    }
	    protected void interrupt()
	    {
	    	Robot.intake.stop();
	    }
	}
