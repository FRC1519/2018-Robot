//package org.mayheminc.robot2018.commands;
//
//import org.mayheminc.robot2018.Robot;
//import org.mayheminc.robot2018.subsystems.Pivot;
//
//import edu.wpi.first.wpilibj.command.Command;
//
///**
// *
// */
//public class PivotToFloor extends Command {
//
//    public PivotToFloor() {
//        // Use requires() here to declare subsystem dependencies
//        // eg. requires(chassis);
//    }
//
//    // Called just before this Command runs the first time
//    protected void initialize() {
//    	Robot.pivot.moveTo(Pivot.DOWNWARD_POSITION);
//    }
//
//    // Called repeatedly when this Command is scheduled to run
//    protected void execute() {
//    }
//
//    // Make this return true when this Command no longer needs to run execute()
//    protected boolean isFinished() {
//        return Robot.pivot.IsPivotInPosition();
//    }
//
//    // Called once after isFinished returns true
//    protected void end() {
//    }
//
//    // Called when another command which requires one or more of the same
//    // subsystems is scheduled to run
//    protected void interrupted() {
//    }
//}
