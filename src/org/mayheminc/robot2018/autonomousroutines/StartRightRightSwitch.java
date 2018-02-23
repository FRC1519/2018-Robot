package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.subsystems.Pivot;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drive straight, curve into the switch, drop cube, backup to 0 heading.
 */
public class StartRightRightSwitch extends CommandGroup {

    public StartRightRightSwitch() {
//    	addSequential(new DriveStraightOnHeading(1.0, DistanceUnits.INCHES, 100.0, 0.0)); // 141 = inches to switch corner
//    	addSequential(new DriveStraightOnHeading(0.70, DistanceUnits.INCHES, 250.0, -90.0));
//    	
//    	addSequential(new Wait(5.0));

    	
    	
//    	addParallel(new IntakeCloseJaw());
//    	addParallel(new PivotMove(Pivot.SPIT_POSITION));
    	addSequential(new IntakeCloseJaw());
    	addSequential(new PivotMove(Pivot.SPIT_POSITION));
    	
    	addSequential(new DriveStraightOnHeading(1.0, DistanceUnits.INCHES, 110.0, 0.0)); // 141 = inches to switch corner
    	addSequential(new DriveStraightOnHeading(0.70, DistanceUnits.INCHES, 45.0, -90.0));
    	
    	addSequential(new PrintAutonomousTimeRemaining("Dropping Cube"));
    	
    	addParallel(new IntakeOutForTime(1.0));
    	addSequential(new Wait(0.2));
    	
    	addSequential(new DriveStraightOnHeading(-0.80, DistanceUnits.INCHES, 45.0, 0.0));
    	addParallel(new IntakeOff());

    	addSequential(new PrintAutonomousTimeRemaining("Start Right Right Switch Done"));
    }
}
