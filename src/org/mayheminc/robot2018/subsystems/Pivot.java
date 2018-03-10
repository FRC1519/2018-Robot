package org.mayheminc.robot2018.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.RobotMap;
import org.mayheminc.util.MayhemTalonSRX;
//import org.mayheminc.util.MayhemTalonSRX;
import org.mayheminc.util.PidTunerObject;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.*;

/**
 * The Pivot lifts the intake to the upright position so the elevator can
 * take the cube.
 */
public class Pivot extends Subsystem implements PidTunerObject {

	public static final int UPRIGHT_POSITION = 2800; //JUST PLACEHOLDER!
	public static final int SPIT_POSITION = 2100; //Was 1900 02-02-2018
	public static final int FLIP_CUBE_POSITION = 700;
	public static final int EXCHANGE_POSITION = 700;//was 500 02-02-2018
	public static final int DOWNWARD_POSITION = 0;
	
	private static final int PIVOT_TOLERANCE = 150; // 75 seemed like not quite enough at Week 1
	
	private static final int DOWN_TOLERANCE = 50;
	
	private static final double ZERO_MAX_CURRENT = 1.0; // Amps
	private static final double ZERO_MOTOR_POWER = 0.25;
	private static final double DOWN_MOTOR_POWER = -0.15;
	
	MayhemTalonSRX m_pivotmotor = new MayhemTalonSRX(RobotMap.PIVOT_TALON);
	int m_position;
	boolean m_manualMode = false;
	boolean m_zero = false;
	int m_zeroWaitToMove;
	int m_zeroWaitForSame;
	int m_zeroEncoderCount;
	
	public Pivot()
	{
		super();
		
		m_pivotmotor.configNominalOutputForward(0.0,  0);
		m_pivotmotor.configNominalOutputReverse(0.0, 0);
		m_pivotmotor.configPeakOutputForward(0.8,  0);  // was 0.5 before bag day; for going up
		m_pivotmotor.configPeakOutputReverse(-0.6,  0);    // was -0.3 before bag day; for going down
		
		m_pivotmotor.config_kP(0, 3, 0);
		m_pivotmotor.config_kI(0, 0, 0);
		m_pivotmotor.config_kD(0, 0, 0);
		m_pivotmotor.config_kF(0, 0, 0);
		
		m_pivotmotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		m_pivotmotor.setInverted(false);
		m_pivotmotor.setSensorPhase(true);
	}
    protected void initDefaultCommand() { }
    
    /**
     * Set the zero flag. Set the counters.  Let periodic run the zero routine. 
     */
    public void zeroPivot() {	
    	m_pivotmotor.set(ControlMode.PercentOutput, ZERO_MOTOR_POWER);
    	m_zero = true;
    	m_zeroWaitToMove = 10;
    	m_zeroWaitForSame = 5;
    }
    
    /**
     * Where ever the pivot is, hold that position.
     */
    public void LockCurrentPosition()
    {
    	m_pivotmotor.set(ControlMode.Position, m_pivotmotor.getSelectedSensorPosition(0));
    }
    
    public void moveTo(int position)
    {
//    	m_pivotmotor.set(ControlMode.Position, position);
    	m_position = position;
    	m_manualMode = false;
    }
    
    public int getPosition()
    {
    	return m_pivotmotor.getSelectedSensorPosition(0);
    }
    
    public boolean IsPivotInPosition()
    {
    	int diff = Math.abs(m_pivotmotor.getSelectedSensorPosition(0) - m_position);
    	return diff < PIVOT_TOLERANCE;
    }
    
    public void periodic()
    {
    	double power = Robot.oi.pivotArmPower();
    	// if the joystick is commanding a movement, go to manual mode.,
    	if( power > 0.01 ||
    		power < -0.01 )
    	{
    		m_manualMode = true;
    	}
    	else {    			// this is position control (not just manual mode)
    		if (m_manualMode) { // we must have just previously been in manual mode, set to "hold position"
    			m_manualMode = false;
    			m_position = m_pivotmotor.getSelectedSensorPosition(0);
    		}
    	}

    	// if we are zeroing the pivot...
    	if ( m_zero )
    	{
//    		System.out.println("Zero Pivot");
    		
    		// if the wait to move has expired...
    		if( m_zeroWaitToMove == 0 )
    		{
    			int encoder = m_pivotmotor.getSelectedSensorPosition(0);
//    			System.out.println("Zero Wait Expired: " + encoder + " " + m_zeroEncoderCount);
    			// if the encoder has not moved...
    			if( encoder == m_zeroEncoderCount )
    			{
    				// count down the same encoder counts.
    				m_zeroWaitForSame--;
    				if( m_zeroWaitForSame == 0 )
    				{
    					// zero is done.
//    	    			System.out.println("ZeroDone");
    					m_zero = false;
    					m_pivotmotor.setSelectedSensorPosition(UPRIGHT_POSITION, 0, 0);
    					m_position = UPRIGHT_POSITION;
    				}
    			}
    			else
    			{
    				// reset the wait for same counter
    				m_zeroWaitForSame = 5;
    				// reset the encoder count we are looking for.
    				m_zeroEncoderCount = encoder;
    			}
    		}
    		else
    		{
    			m_zeroWaitToMove--;
    		}
    	} 
    	else // not zeroing
    	{
    		// control the arm pivot normally if not zeroing

    		if( m_manualMode )
    		{
    			m_pivotmotor.set(ControlMode.PercentOutput, power);
    		}
    		else // positioning mode
    		{
    			switch(m_position)
    			{
    			case DOWNWARD_POSITION:
    				// if we wanted the DOWN position...
    				int pos = m_pivotmotor.getSelectedSensorPosition(0);
    				// and we are within a tolerance of being down...
    				if( pos < DOWNWARD_POSITION + DOWN_TOLERANCE)
    				{
    					// set the motor to press down lightly.
    					m_pivotmotor.set(ControlMode.PercentOutput,  DOWN_MOTOR_POWER);;
    				}
    				else
    				{
    					m_pivotmotor.set(ControlMode.Position, m_position);
    				}
    				break;
    				
    			default:
    					m_pivotmotor.set(ControlMode.Position, m_position);
    					break;		
    			}
    		}
    	
     	}
    }
    
    public void UpdateSmartDashboard()
    {
    	SmartDashboard.putNumber("Pivot Encoder Pos", m_pivotmotor.getSelectedSensorPosition(0));
    	SmartDashboard.putBoolean("Pivot Manual Mode", m_manualMode);
    }
    
    ////////////////////////////////////////////////////////
    // PidTunerObject
    ////////////////////////////////////////////////////////
	@Override
	public double getP() { return m_pivotmotor.getP();}
	@Override
	public double getI() { return m_pivotmotor.getI();}
	@Override
	public double getD() { return m_pivotmotor.getD();}
	@Override
	public double getF() { return m_pivotmotor.getF();}
	@Override
	public void setP(double d) {m_pivotmotor.config_kP(0,  d,  0); }
	@Override
	public void setI(double d) {m_pivotmotor.config_kI(0,  d,  0); }
	@Override
	public void setD(double d) {m_pivotmotor.config_kD(0,  d,  0); }
	@Override
	public void setF(double d) {m_pivotmotor.config_kF(0,  d,  0); }
}
