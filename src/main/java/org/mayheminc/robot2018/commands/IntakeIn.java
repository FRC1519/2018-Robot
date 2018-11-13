package org.mayheminc.robot2018.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.mayheminc.robot2018.Robot;
//import org.mayheminc.robot2018.subsystems.Intake;

public class IntakeIn extends Command {

    public IntakeIn() {
        super();
    }

    protected void initialize() {
    	Robot.intake.takeInCube();
    }
    protected boolean isFinished() {return false;}
    protected void end()
    {
    	Robot.intake.stop();
    }
    protected void interrupt()
    {
    	Robot.intake.stop();
    }
}
