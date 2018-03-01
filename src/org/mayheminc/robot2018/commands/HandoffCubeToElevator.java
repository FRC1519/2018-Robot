package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.subsystems.Elevator;
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
    	addSequential(new ElevatorArmOpen());
    	addSequential(new ElevatorSetPosition(Elevator.HANDOFF_HEIGHT));
    	addSequential(new TurretMoveTo(Turret.FRONT_POSITION));
    	addSequential(new ElevatorSetPosition(Elevator.PICK_UP_CUBE));
    	addSequential(new IntakeCloseJaw());
    	addSequential(new PivotToUpright());
    	addSequential(new ElevatorArmSetMotorAuto(.5));
    	addSequential(new IntakeOutInstant());
    	addSequential(new Wait(0.1));
    	addSequential(new ElevatorArmClose());
    	addSequential(new Wait(0.2));
    	addSequential(new IntakeOff());
    	addSequential(new Wait(0.5));
    	addSequential(new ElevatorArmSetMotorAuto(0.2));
    	addSequential(new IntakeOpenJaw());
    	addSequential(new ElevatorSetPosition(Elevator.SWITCH_HEIGHT));
    	addSequential(new IntakeCloseJaw());
    }
}
