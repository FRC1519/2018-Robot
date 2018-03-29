package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.subsystems.Elevator;
import org.mayheminc.robot2018.subsystems.Pivot;
import org.mayheminc.robot2018.subsystems.Turret;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class HandoffCubeToElevator extends CommandGroup {

	/**
	 * Open the elevator hands. Move the Elevator to the low scale position.
	 * Close the intake jaws
	 * Pivot up the cube.
	 * Move the elevator to the pick up position.
	 * Spit the cube up.
	 * Catch the cube in the elevator hands.
	 * Move the elevator up.
	 */
    public HandoffCubeToElevator() {
    	addSequential(new ElevatorArmOpenAndWait());
    	addSequential(new PrepareElevatorForHandOff());
    	
    	addSequential(new IntakeCloseJaw());
    	addSequential(new PivotMove(Pivot.UPRIGHT_POSITION));
    	addSequential(new Wait(0.1)); // wait a fraction of a second for arm pivot "bounce" to stop
    	addSequential(new ElevatorArmSetMotorAuto(0.5));   // sucks in at half speed
    	addSequential(new IntakeOutInstant());
//    	addSequential(new Wait(0.03));  
    	addSequential(new ElevatorArmClose());
    	addSequential(new Wait(0.2));    // wait for the pistons to close
    	addSequential(new IntakeOff());

    	// before moving the elevator up, turn on the T-Rex motors to hold the cube gently
    	// and also open up the intake jaws to make sure that the intake is no longer holding the cube
    	addSequential(new ElevatorArmSetMotorAuto(0.25));  // was 0.20 before 24 March 2018 
    	addSequential(new IntakeOpenJaw());

    	// move the elevator up a bit to the scoring height for the switch
    	addSequential(new ElevatorSetPosition(Elevator.SWITCH_HEIGHT));
    	
    	// close the intake jaws so that they're ready for harvesting another cube in the future
    	addSequential(new IntakeCloseJaw());
    }
}
