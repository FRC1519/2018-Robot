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
	    	Robot.elevatorArms.setMotor(0.5);
	    }
	    
	    protected void execute()
	    {
	    	if( Robot.pivot.getCurrentPosition() < Pivot.DOWNWARD_POSITION + Pivot.PIVOT_TOLERANCE &&
	    		Robot.cubeDetector.isCubeSquare() )
	    	{
	    		Robot.pivot.setDesiredPosition(Pivot.UPRIGHT_POSITION);
	    	}
	    }
	    protected boolean isFinished() {return false;}
	    protected void end()
	    {
	    	Robot.intake.stop();
	    	Robot.elevatorArms.setMotor(0.0);
	    }
	    protected void interrupt()
	    {
	    	Robot.intake.stop();
	    	Robot.elevatorArms.setMotor(0.0);
	    }
	}
