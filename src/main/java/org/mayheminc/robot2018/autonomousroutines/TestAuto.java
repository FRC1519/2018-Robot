package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraight;
//import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorWaitUntilAtPosition;
import org.mayheminc.robot2018.commands.IntakeInAndLiftTheCube;
import org.mayheminc.robot2018.commands.PivotMove;
import org.mayheminc.robot2018.commands.Wait;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.subsystems.Elevator;
import org.mayheminc.robot2018.subsystems.Pivot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TestAuto extends CommandGroup {

    public TestAuto() {
    	addSequential (new Wait(1.0));  // pause briefly before placing cube

    	addSequential(new PivotMove(Pivot.DOWNWARD_POSITION));
    	addSequential(new DriveStraight(0.1, DriveStraight.DistanceUnits.INCHES, 10.0, 2.0)); 
    	
    	addParallel(new IntakeInAndLiftTheCube(true));
    	addSequential (new Wait(1.0));  // pause briefly before placing cube

    	addSequential(new ElevatorWaitUntilAtPosition(Elevator.SWITCH_HEIGHT));

    	addSequential(new DriveStraight(-0.1, DriveStraight.DistanceUnits.INCHES, 10.0, 2.0)); 
    }
}
