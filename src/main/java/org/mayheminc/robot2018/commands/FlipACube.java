package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.subsystems.Pivot;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class FlipACube extends CommandGroup {

    public FlipACube(int degrees) {
    	
    	//pivot to flip Position.
//    	addSequential(new PrintToDriverStation("Pivot"));
    	addSequential(new PivotMove(Pivot.FLIP_CUBE_POSITION));
    	
    	addSequential(new IntakeOutInstant());
    	
		//    	turn 45 degrees
//    	addSequential(new PrintToDriverStation("Rotate"));
    	addSequential(new DriveRotateDegrees(degrees));
		//    	back up 
//    	addSequential(new PrintToDriverStation("Backup"));
    	
    	addSequential(new IntakeOff());
    	addSequential(new DriveStraight(-0.5, DriveStraight.DistanceUnits.INCHES, 5.0));
		//    	pivot down
    	addSequential(new PivotMove(Pivot.DOWNWARD_POSITION));

    	addSequential(new DriveStraight(0.5, DriveStraight.DistanceUnits.INCHES, 5.0));
    	//    	ATTACK!
    	//addSequential(new AutoGatherCubeSeq());
    	addSequential(new WaitForever());
    }
    
    // Can be overwritten by teams
    protected void interrupted() {
    	Robot.intake.stop();
    }
}
