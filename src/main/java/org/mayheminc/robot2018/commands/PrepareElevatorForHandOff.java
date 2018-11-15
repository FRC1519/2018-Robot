package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.subsystems.Elevator;
import org.mayheminc.robot2018.subsystems.Turret;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Rise the elevator if the turret is not centered.
 * Rotate the turret to the front.
 * Move the elevator to pick up the cube.
 */
public class PrepareElevatorForHandOff extends CommandGroup {

    public PrepareElevatorForHandOff() {
      	addSequential(new ElevatorRaiseIfTurretNotCentered());
    	addSequential(new TurretMoveTo(Turret.FRONT_POSITION));
   		addSequential(new ElevatorSetPosition(Elevator.PICK_UP_CUBE));
    }
}
