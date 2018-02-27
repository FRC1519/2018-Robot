package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class ElevatorArmSetMotor extends InstantCommand {

	private static double EJECT_TIMEOUT = 1.0;
	double m_motorSpeed;
	
    public ElevatorArmSetMotor(double motorSpeed) {
//        super(EJECT_TIMEOUT);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        m_motorSpeed = motorSpeed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.elevatorArms.setMotor(m_motorSpeed);
    }

//    // Called repeatedly when this Command is scheduled to run
//    protected void execute() {
//    }
//
//    // Called once after timeout
//    protected void end() {
//    	Robot.elevatorArms.setMotor(0.0);
//    }
//
//    // Called when another command which requires one or more of the same
//    // subsystems is scheduled to run
//    protected void interrupted() {
//    	Robot.elevatorArms.setMotor(0.0);
//    }
}
