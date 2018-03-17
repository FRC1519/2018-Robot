package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.subsystems.Elevator;
import org.mayheminc.robot2018.subsystems.ElevatorArms;
import org.mayheminc.robot2018.subsystems.Pivot;
import org.mayheminc.robot2018.subsystems.Turret;
import org.mayheminc.robot2018.subsystems.ElevatorArms.JawPosition;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeInAndLiftTheCube extends Command {
	
		private boolean m_handoffStarted;
		private Command m_handoffCommand;
	
	    public IntakeInAndLiftTheCube() {
	        super();
	        
//	    	m_handoffCommand = new BackupTwoInchesAndHandoff();
	    	m_handoffCommand = new HandoffCubeToElevator();
	    }

	    protected void initialize() {
	    	m_handoffStarted = false;
	    	Robot.intake.takeInCube();
	    }
	    
	    protected void execute()
	    {
	    	// if the pivot is down, and we're in this code, we must be trying to get a cube
	    	// we should ensure the elevator arms are open and the turret is centered
	    	if( Robot.pivot.getCurrentPosition() < Pivot.DOWNWARD_POSITION + Pivot.PIVOT_TOLERANCE )
		    	{
		    		Robot.elevatorArms.setJaw(JawPosition.OPEN);
		    		Robot.turret.setDesiredPosition(Turret.FRONT_POSITION);
		    		Robot.elevator.setDesiredPosition(Elevator.PICK_UP_CUBE);
		    	}
	    	
	    	// if the pivot is down and the cube is in, lift it.
	    	if( Robot.pivot.getCurrentPosition() < Pivot.DOWNWARD_POSITION + Pivot.PIVOT_TOLERANCE &&
	    		Robot.cubeDetector.isCubeSquare() )
	    	{
	    		// want to backup and then automatically lift the cube.
	    		// There is a special command we made for this to back up two inches and then handoff
	    		
	    		
	    		// automatically lift the cube
// NOTE:  Temporarily commenting this out to experiment with auto-handoff	    		
//	    		Robot.pivot.setDesiredPosition(Pivot.UPRIGHT_POSITION);
	    		
	    		// automatically handoff the cube to the elevator
	    		if (!m_handoffStarted) {
	    			// call a command to make a handoff
	    			m_handoffCommand.start();
	    		}

	    	}
	    	
	    	// if the pivot is up, no special handling is needed
	    	// (NOTE: the arm used to not suck in when up, but this is sometimes desirable)
//	    	if( Robot.pivot.getCurrentPosition() > Pivot.UPRIGHT_POSITION - Pivot.PIVOT_TOLERANCE )
//	    	{
//	    		Robot.intake.stop();
//	    	}
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
