package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TransitionCubeToElevator extends CommandGroup {

	/**
	 * Open the elevator hands. Move the Elevator to the low scale position.
	 * Pivot up the cube.
	 * Move the elevator to the pick up position.
	 * Spit the cube up.
	 * Catch the cube in the elevator hands.
	 * Move the elevator up.
	 */
    public TransitionCubeToElevator() {
    	addSequential(new ElevatorArmOpen());
    	addSequential(new ElevatorSetPosition(Elevator.ElevatorPositions.SCALE_LOW));
    	addSequential(new PivotToElevator());
    	addSequential(new ElevatorSetPosition(Elevator.ElevatorPositions.PICK_UP_CUBE));
    	addSequential(new IntakeOut());
    	addSequential(new Wait(400));
    	addSequential(new ElevatorArmClose());
    	addSequential(new ElevatorSetPosition(Elevator.ElevatorPositions.SWITCH_HIGH));
    }
}
