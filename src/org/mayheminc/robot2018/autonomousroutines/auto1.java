package org.mayheminc.robot2018.autonomousroutines;
import org.mayheminc.robot2018.commands.*;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.subsystems.Drive;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class auto1 extends CommandGroup {

    public auto1() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
  
    	
    	
    	addSequential(new SetShifter(Drive.LOW_GEAR));  	
    	addSequential(new DriveStraightOnHeading(0.20, DistanceUnits.INCHES, 150.0, -90.0));
    	
    	addSequential(new Wait(10000));
    	
    	addSequential(new DriveStraight(1.0, DriveStraight.DistanceUnits.INCHES, 150.0));//bring the robot just before the edge of the switch fence.
    	addSequential(new ArcingTurn(30.0, .5, -0.80));//needs to find cube while curving
    	addSequential(new DriveStraight(1.0, DriveStraight.DistanceUnits.INCHES, 180.0));//bring the robot just before the edge of the switch fence.
    	 	
    	
    	
    	addSequential(new Wait(10000));
    	
//    	addParallel(new SetArmPosition(Arm.UPRIGHT_POSITION_COUNT, Arm.DONT_REQUIRE_ARM_SUBSYSTEM));
    	addSequential(new SetShifter(Drive.LOW_GEAR));  	
    	addSequential(new DriveStraight(1.0, DriveStraight.DistanceUnits.INCHES, 20.0));//bring the robot just before the edge of the switch fence.
//    	addSequential(new SetShifter(Drive.HIGH_GEAR));
    	addSequential(new DriveStraight(1.0, DriveStraight.DistanceUnits.INCHES, 60.0));//bring the robot just before the edge of the switch fence.
    	addSequential(new SetShifter(Drive.LOW_GEAR)); 
    	
    	addSequential(new ArcingTurn(50.0, 1.0, -.5));//this makes the robot arc into the fence on the switch.
    	addSequential(new ArcingTurn(40.0, -1.0, -.5));//does the same thing as the last line but backwards?
    	addSequential(new DriveStraight(1.0, DriveStraight.DistanceUnits.INCHES, 70.0));
    	addSequential(new ArcingTurn(40.0, 1.0, -.5));//needs to find cube while curving
    	
    	addSequential(new DriveStraight(1.0, DriveStraight.DistanceUnits.INCHES, 100.0));//bring the robot just before the edge of the switch fence.

//    	//hopefully find a cube?
//    	addSequential(new ArcingTurn(1.0, 1.0, .5));//curve towards the "NULL ZONE"
//    	addSequential(new DriveStraight(1.0, DriveStraight.DistanceUnits.INCHES, 50.0));//drive into the "NULL ZONE"
    	
    	
    }
}
