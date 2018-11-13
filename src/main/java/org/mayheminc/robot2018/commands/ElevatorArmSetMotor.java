package org.mayheminc.robot2018.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.mayheminc.robot2018.Robot;
//import org.mayheminc.robot2018.subsystems.Intake;

public class ElevatorArmSetMotor extends Command {

	double m_motorSpeed;
	
    public ElevatorArmSetMotor(double motorSpeed) {
//        super();
        m_motorSpeed = motorSpeed;
    }
    
    protected void initialize() {
    	Robot.elevatorArms.setMotor(m_motorSpeed);
    }
    protected boolean isFinished() {return false;}
    
    protected void end()
    {
    	Robot.elevatorArms.setMotor(0.0);
    }
    protected void interrupt()
    {
    	Robot.elevatorArms.setMotor(0.0);
    }
}
