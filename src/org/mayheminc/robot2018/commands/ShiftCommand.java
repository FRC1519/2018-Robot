package org.mayheminc.robot2018.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A Shift Command will look at the button that is passed in and see if
 * it is pressed.  If it is, then cmdA is run.  If it is not, then cmdB is run.
 */
public class ShiftCommand extends Command {

	Button m_button; 
	Command m_cmdA;
	Command m_cmdB;
	Command m_runningCmd;
	boolean m_cmdStarted;
	
	public ShiftCommand(Button button, Command cmdA, Command cmdB) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	m_button = button;
    	m_cmdA = cmdA;
    	m_cmdB = cmdB;
    	m_cmdStarted = false;
    }

    // Called just before this Command runs the first time
	// get the command that we are going to run.
    protected void initialize() {
    	DriverStation.reportError("Shift Cmd init", false);
    	m_runningCmd = (m_button.get() ? m_cmdA : m_cmdB);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	DriverStation.reportError("Shift Cmd execute", false);
    	// if this command has not been told to start, start it.
    	if( !m_cmdStarted )
    	{
    		DriverStation.reportError("Shift Cmd started", false);
    		m_runningCmd.start();
    		m_cmdStarted = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    // This command is done when cmdA or cmdB has been started.
    protected boolean isFinished() {
    	DriverStation.reportError("Shift Cmd is Finished " + m_runningCmd.isRunning(), false);
        return m_cmdStarted;
    }

    // Called once after isFinished returns true
    protected void end() {
    	DriverStation.reportError("Shift Cmd end ", false);
        
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	DriverStation.reportError("Shift Cmd interrupted ", false);
    }
}
