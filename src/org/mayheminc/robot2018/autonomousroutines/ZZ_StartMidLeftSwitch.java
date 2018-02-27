package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.IntakeCloseJaw;
import org.mayheminc.robot2018.commands.IntakeOff;
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
public class ZZ_StartMidLeftSwitch extends CommandGroup {

    public ZZ_StartMidLeftSwitch() {
    	addParallel(new IntakeCloseJaw());
    	addParallel(new PivotMove(Pivot.SPIT_POSITION));

    	// go almost due west
    	addSequential(new DriveStraightOnHeading(1.0, DistanceUnits.INCHES, 45.0, 280.0));
    	// drive to near-side of fence
    	addSequential(new DriveStraightOnHeading(1.0, DistanceUnits.INCHES, 65.0, 0.0));

      	addSequential(new PrintAutonomousTimeRemaining("Dropping Cube"));
    	
    	addParallel(new IntakeOutForTime(1.0));
    	addSequential(new Wait(0.4));
    	
    	addParallel(new IntakeOff());

    	addSequential(new PrintAutonomousTimeRemaining("Start Right Right Switch Done"));


    }
}
