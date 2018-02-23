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
public class StartLeftLeftSwitch extends CommandGroup {

    public StartLeftLeftSwitch() {
    	addParallel(new IntakeCloseJaw());
    	addParallel(new PivotMove(Pivot.SPIT_POSITION));
    	addSequential(new DriveStraightOnHeading(1.0, DistanceUnits.INCHES, 90.0, 0.0));
    	addSequential(new DriveStraightOnHeading(0.70, DistanceUnits.INCHES, 40.0, 90.0));
    	
    	addSequential(new PrintAutonomousTimeRemaining("Dropping Cube"));
    	
    	addParallel(new IntakeOutForTime(1.0));
    	addSequential(new Wait(0.2));
    	
    	addSequential(new DriveStraightOnHeading(-0.80, DistanceUnits.INCHES, 35.0, 0.0));
    	addParallel(new IntakeOff());

    	addSequential(new PrintAutonomousTimeRemaining("Start Left Left Switch Done"));
    }
}
