package org.mayheminc.robot2018.subsystems;

import java.net.SocketException;
import java.util.List;

import org.mayheminc.util.*;
//import org.mayheminc.ObjectLocation;
import org.mayheminc.util.ObjectLocation.ObjectTypes;

//import org.mayheminc.robot2018.Robot;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The Targeting subsystem gets data from the Network Tables (put there by the targeting application),
 * and returns 
 */
public class Targeting extends Subsystem {
	public double cubeDrive = 0.0;
	public double cubepercentage = 0.0;
	private float cubex = 1000;
	private float cubey = 1000;
	private float cubewidth = 1000;
	private float cubeheight = 1000;
	private float cubeprobability = 1000;
	private boolean m_yoloPacket;
	private double m_probability;
	
	ObjectListener listener;
	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public Targeting()
	{		
		try {
			listener = new ObjectListener();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listener.start();
	}

	public void initDefaultCommand() { }
	
	//This function returns the value x value of the cube.
	public float locationCubeX(){return cubex;}	
	public float locationCubeY(){return cubey;}
	public float locationCubeW(){return cubewidth;}
	public float locationCubeH(){return cubeheight;}
	public float locationCubeP() {return cubeprobability;}
	
	int m_lastFrame;
	public void periodic()
	{	
		// get the list of objects from the listener
		int frame = listener.getLastFrame();
		
		if(frame == m_lastFrame)
		{
			return;
		}
		m_lastFrame = frame;
		
//		System.out.println("Frame == " + frame);
		List<ObjectLocation> objects = listener.getObjectList();
		
		if( objects != null )
		{
//			System.out.println("Targeting: Received non-null list");
			// loop through the objects.  This may be an empty list.
			cubex = 1000;
			cubey = 1000;
			cubewidth = 1000;
			cubeheight = 1000;
			cubeprobability = 1000;
			
			ObjectLocation bestCube = null;
			float maxScore = 0.0f;
			ObjectLocation bestExchange = null;
			float maxScoreExchange = 0.0f;
//			SmartDashboard.putNumber("Number of Targets", objects.size());
			
			for(ObjectLocation obj : objects)
			{
//				System.out.println("Target: " + obj.toString());

				// if there is a cube...
				if( obj.type == ObjectTypes.OBJ_CUBE &&
					obj.probability > 0.4 )
				{
					float score = ScoreCube(obj);
					
					if( score > maxScore)
					{
						maxScore = score;
						bestCube = obj;
					}
				}
				//if there is an exchange red or blue...
				if((obj.type == ObjectTypes.OBJ_EXCHANGE_RED && obj.probability > 0.4) ||
					(obj.type == ObjectTypes.OBJ_EXCHANGE_BLUE && obj.probability > 0.4))
					{
						float score = ScoreExchange(obj);
						
						if( score > maxScoreExchange)
						{
							maxScoreExchange = score;
							bestExchange = obj;
						}
					}
			}
			if( bestCube != null )
			{
				SaveBestCube(bestCube, maxScore);
			}
		}
		else
		{
//			System.out.println("Targeting: objects is null");
		}
		
//		latestCenterX = 1000; // no cube visible
	}
	
	private void SaveBestCube(ObjectLocation obj, float Score) {
		// TODO Auto-generated method stub
		m_probability = obj.probability;
		// get its center
		cubex = obj.x;
		cubey = obj.y;
		cubewidth = obj.width;
		cubeheight = obj.height;
		cubeprobability = obj.probability;
		
//		latestFrameNum++;
		m_yoloPacket = !m_yoloPacket;
		
		SmartDashboard.putBoolean("Target Packet", m_yoloPacket);
		SmartDashboard.putNumber("Target X",  obj.x);
		SmartDashboard.putNumber("Target Y",  obj.y);
		SmartDashboard.putNumber("Target width",  obj.width);
		SmartDashboard.putNumber("Target height",  obj.height);
		SmartDashboard.putNumber("target prob", m_probability);
		SmartDashboard.putNumber("Score", Score);
//		cubeDrive = Math.abs((obj.x * 2) + 1 / 4);
		cubepercentage = obj.x;
	}

	public void updateSmartDashboard()
	{
		//SmartDashboard.putNumber("Target X",  latestCenterX);
//		SmartDashboard.putNumber("Target Frame", latestFrameNum);
		SmartDashboard.putNumber("Target Prob", m_probability);
	}
	
//	public double getRobotHeading() {return latestImageHeading;}
//	public int getCubeCenterOffset() {return latestCenterX;}
//	public boolean isCubeVisible() 
//	{
//		return (getCubeCenterOffset() < 1000);
//	}
	
	public float ScoreCube(ObjectLocation obj) 
	{
		float centerscore = (float) (1-Math.abs(obj.x-0.5));
		float likeableness = obj.width*centerscore;
		return likeableness;
	}

	public float ScoreExchange(ObjectLocation obj) 
	{
		float centerscore = (float) (1-Math.abs(obj.x-0.5));  //This needs to be changed to the right thing for the exchange. 
		float likeableness = obj.width*centerscore;
		return likeableness;
	}
	
}

