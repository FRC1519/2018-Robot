package org.mayheminc.robot2018.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.mayheminc.robot2018.RobotMap;
import org.mayheminc.util.MayhemTalonSRX;

import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 * 
 */
public class Pivot extends Subsystem {

public static final int UPRIGHT_POSITION = 2000; //JUST PLACEHOLDER!
public static final int DOWNWARD_POSITION = 0; //JUST A PLACEHOLDER!
MayhemTalonSRX m_pivotmoter = new MayhemTalonSRX(RobotMap.PIVOT_TALON);

    protected void initDefaultCommand() {
		
	}
    
    public void zeroPivot() {
    	
    	m_pivotmoter.setPosition(0);
    	
    }
    
    public void pivotUp() {
    	
    	m_pivotmoter.set(ControlMode.Position, UPRIGHT_POSITION );
    	
    }
    
    public void pivotDown() {
    	
    	m_pivotmoter.set(ControlMode.Position, DOWNWARD_POSITION);
    	
    }
	
	
}

