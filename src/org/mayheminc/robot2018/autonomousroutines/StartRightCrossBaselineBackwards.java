package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.subsystems.Autonomous;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StartRightCrossBaselineBackwards extends SmartAutoChooserBase {

	protected Command GetAutoroutine() {
		return new CrossBaselineBackwards(Autonomous.StartOn.RIGHT);
	}
}
