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
	
		private boolean m_inAuto;
		private boolean m_doneEnough;
		private boolean m_handoffStarted;
		private Command m_handoffCommand;
	
	    public IntakeInAndLiftTheCube(boolean inAuto) {
	        super();
	        
	        m_inAuto = inAuto;
	    	m_handoffCommand = new BackupAndHandOff();
	    }

	    protected void initialize() {
	    	m_doneEnough = false;
	    	m_handoffStarted = false;
	    	Robot.intake.takeInCube();
	    	Robot.elevatorArms.setMotor(ElevatorArms.MOTOR_IN_FAST);
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
	    	
    		if (m_inAuto) {
    			// in auto, creep forward a little bit while getting the cube
        		Robot.drive.speedRacerDrive(0.1, 0, false);
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
	    		
	    		if (m_inAuto) {
	    			// this is done enough for autonomous operation
	    			m_doneEnough = true;
	    			// stop creeping forward
	        		Robot.drive.speedRacerDrive(0.0, 0, false);
	    		} else {
	    			// we're in teleop, so automatically handoff the cube to the elevator
	    			if (!m_handoffStarted) {
	    			// 	call a command to make a handoff
	    				m_handoffCommand.start();
	    			}
	    		}

	    	}
	    }
	    
	    protected boolean isFinished() {
	    	// this command will never finish in teleop, but that's what we want...
	    	return (m_inAuto && m_doneEnough);
	    }
	    
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
