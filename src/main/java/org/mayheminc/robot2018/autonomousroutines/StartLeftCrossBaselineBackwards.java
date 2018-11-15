package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.subsystems.Autonomous;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StartLeftCrossBaselineBackwards extends SmartAutoChooserBase {

	protected Command GetAutoroutine() {
		return new CrossBaselineBackwards(Autonomous.StartOn.LEFT);
	}
}
