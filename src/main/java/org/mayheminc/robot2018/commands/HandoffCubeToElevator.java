package org.mayheminc.robot2018.commands;

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
    public HandoffCubeToElevator(int elevatorHeightAfterHandoff) {
    	
    	addSequential(new HandoffCubeToElevatorWithoutRaisingElevator());
        
    	// move the elevator up a bit to the requested scoring height
    	addSequential(new ElevatorSetPosition(elevatorHeightAfterHandoff));
    	
    	// close the intake jaws so that they're ready for harvesting another cube in the future
    	addSequential(new IntakeCloseJaw());
    }
    

	
}
