package org.mayheminc.robot2018.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.mayheminc.robot2018.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

/**
 *
 */
public class Lifter extends Subsystem {
    public static double LIFTER_POWER_RETRACT = -0.6;
    public static double LIFTER_POWER_DEPLOY = 0.6;
    public static double LIFTER_OFF = 0.0;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private static CANTalon m_lifterTalon = new CANTalon(RobotMap.LIFTER_TALON);
	
	public Lifter() {
		m_lifterTalon.configNominalOutputVoltage(+0.0f, -0.0f);
		m_lifterTalon.configPeakOutputVoltage(+12.0, -12.0);
		
		m_lifterTalon.changeControlMode(TalonControlMode.PercentVbus);
		m_lifterTalon.enableControl();
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setLifterPower(double power){
    	m_lifterTalon.set(power);
    }

    public void stopLifter(){
    	setLifterPower(0.0);
    }    
    
}