package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.IntakeCloseJaw;
import org.mayheminc.robot2018.commands.IntakeOff;
import org.mayheminc.robot2018.commands.IntakeOut;
import org.mayheminc.robot2018.commands.IntakeOutForTime;
import org.mayheminc.robot2018.commands.PivotMove;
import org.mayheminc.robot2018.commands.PrintAutonomousTimeRemaining;
import org.mayheminc.robot2018.commands.Wait;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.subsystems.Pivot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StartRightLeftSwitch extends CommandGroup {

    public StartRightLeftSwitch() {
    	addParallel(new IntakeCloseJaw());
    	addParallel(new PivotMove(Pivot.SPIT_POSITION));
    	addSequential(new DriveStraightOnHeading(1.0, DistanceUnits.INCHES, 170.0, 0.0));
    	addSequential(new DriveStraightOnHeading(0.70, DistanceUnits.INCHES, 100.0, -90.0));
    	
    	addSequential(new DriveStraightOnHeading(0.70, DistanceUnits.INCHES, 45.0, -1800.0));

    	addSequential(new PrintAutonomousTimeRemaining("Dropping Cube"));
    	
    	addParallel(new IntakeOutForTime(1.0));
    	addSequential(new Wait(0.2));
    	
    	addSequential(new DriveStraightOnHeading(-0.80, DistanceUnits.INCHES, 45.0, 0.0));
    	addParallel(new IntakeOff());

    	addSequential(new PrintAutonomousTimeRemaining("Start Right Right Switch Done"));
    }
}
