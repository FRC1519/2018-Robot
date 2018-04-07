package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.subsystems.Elevator;
import org.mayheminc.robot2018.subsystems.Pivot;
import org.mayheminc.robot2018.subsystems.Turret;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class HandoffCubeToElevatorWithoutRaisingElevator extends CommandGroup {

	/**
	 * Open the elevator hands. Move the Elevator to the low scale position.
	 * Close the intake jaws
	 * Pivot up the cube.
	 * Move the elevator to the pick up position.
	 * Spit the cube up.
	 * Catch the cube in the elevator hands.
	 * Move the elevator up.
	 */
    public HandoffCubeToElevatorWithoutRaisingElevator() {
        
    	// ensure upper assembly is ready to receive the handoff
    	addParallel(new ElevatorArmOpen());
      	addSequential(new ElevatorRaiseIfTurretNotCentered());
    	addSequential(new TurretMoveTo(Turret.FRONT_POSITION));
   		addSequential(new ElevatorSetPosition(Elevator.PICK_UP_CUBE));
    	
    	addParallel(new IntakeCloseJaw());
    	addParallel(new ElevatorArmSetMotorAuto(0.5));   // sucks in at half speed
    	addSequential(new PivotMove(Pivot.UPRIGHT_POSITION));
    	addSequential(new Wait(0.1)); // wait a fraction of a second for arm pivot "bounce" to stop

    	addSequential(new IntakeOutInstant());  // spit the cube (upwards) out of the intake
//    	addSequential(new Wait(0.03));  
    	addSequential(new ElevatorArmClose());
    	addSequential(new Wait(0.1));    // wait for the pistons to close
    	addParallel(new IntakeOff());

    	// before moving the elevator up, turn on the T-Rex motors to hold the cube gently
    	// and also open up the intake jaws to make sure that the intake is no longer holding the cube
    	addParallel(new ElevatorArmSetMotorAuto(0.25));  // was 0.20 before 24 March 2018 
    	addSequential(new IntakeOpenJaw());
    }
}
