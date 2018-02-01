package org.mayheminc.robot2018.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.subsystems.Intake;

public class IntakeIn extends InstantCommand {

    public IntakeIn() {
        super();
    }

    protected void initialize() {
    	Robot.intake.takeInCube();
    }
}
