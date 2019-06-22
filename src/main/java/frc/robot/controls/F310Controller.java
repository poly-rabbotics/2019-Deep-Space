/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


/**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * Reminder: this is a wrapper. When we ask this controller how fast we want to turn 
   * the robot (for example), it takes that question, and it turns that into a question
   * of what input we are getting from the driver's controller. In other words, it
   * basically translates from one question to another.
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
*/

package frc.robot.controls; //say what package this file is in
import org.usfirst.frc.team4999.controllers.LogitechF310; //this is the wrapper that we were suppposed to use inside this wrapper, but didn't

import edu.wpi.first.wpilibj.GenericHID.Hand; //Import this for readability, so that when we mean left we can write "Hand.kLeft"
import frc.robot.RobotMap; //We need this to access the controllers
import static org.usfirst.frc.team4999.utils.Utils.*; //Import all the boring utility functions we borrowed from Momentum (team 4999)

/**
 * Add your docs here.
*/
public class F310Controller implements DriveController{
    private LogitechF310 controller1 = RobotMap.controller1; //from now on we can write controller1 when we want to 
							     //reference to the same controller1 defined in RobotMap

	//Here we are declaring constants using the word final, so that they can't change.
	//Why? to eliminate magic numbers (numbers without names to describe them).
    private static final double CURVE = 2; //We raise all drive request to the 2nd power so that small inputs
					//extra small (such as 0.25 instead of 0.5).
					//It makes it easier to control the robot precisely.
    private static final double DEADZONE = .01; //Ignore inputs that are really really small. They could just be from
						//imperfections in our physical controller, not intentional driver input.
    private static final double startLift = .75; //the joysticks have to be pushed 3/4 of the way out to be interpreted as a command to start the lift sequence.

    //private double[] speedLimits = {0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0}; //for a method we didn't use.
    // private int speedLimitIndex = speedLimits.length-1; 			//same ^
/**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * translate the driver input into a request to drive forward or backward.
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
*/
    @Override
    public double getMoveRequest(){
        double speed = controller1.getRawAxis(1);
        speed = curve(speed, CURVE); //explained above^ next to definition for variable CURVE
        speed = deadzone(speed, DEADZONE); //explained above^ next to definition for variable DEADZONE
        return speed;

    }
/**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * translate the driver input into a request to turn the robot.
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
*/
    @Override
    public double getTurnRequest(){
        double speed = controller1.getRawAxis(4);
        speed = curve(speed, CURVE);
        speed = deadzone(speed, DEADZONE);
        return speed;

    }
    //Ignore this method. We didn't use it, so I am taking the liberty of commenting it out.
    /*@Override
    public double getSpeedLimit(){
        if(controller1.getRawButtonPressed(7) && speedLimitIndex>0)
            speedLimitIndex--;
        if(controller1.getRawButtonPressed(8) && speedLimitIndex<speedLimits.length-1)
            speedLimitIndex++;
        return speedLimits[speedLimitIndex];
    }*/
/**
   * The intention was to find out if the driver wanted to change which side of the 
   * robot was considered the front, so that when the move request says go forward the robot
   * would go the other way. Potentially useful for switching between using the hatch mechanism
   * and using the cargo mechanism on the other side. Not sure if this function was implemented.
*/
    @Override
    public boolean getReverseDirection(){
        return controller1.getRawButtonPressed(3);
    }
/**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * translate the driver input into a request to either extend or retract the air cylinders in
   * the hatch mechanism.
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
*/
    @Override
    public boolean getToggleHatchPusher(){
        return controller1.getRawButton(2);
    }
/**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * translate the driver input into a request to spin the intake inwards, to intake cargo.
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
*/
    @Override
    public boolean getToggleInwards(){
        return controller1.getRawButtonPressed(6);
    }
/**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * translate the driver input into a request to spin the intake outwards, to outtake cargo.
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
*/
    @Override
    public boolean getToggleOutwards(){
        return controller1.getRawButtonPressed(5);
    }
/**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * translate the driver input into a request to move the arm up.
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
*/
    public boolean getMoveArmsUp(){
        return controller1.getRawButton(4);
    }
/**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * translate the driver input into a request to move the arm down.
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
*/
    @Override
    public boolean getMoveArmsDown(){
        return controller1.getRawButton(1);
    } 
/**
   * translate the driver input into a request to start the lift sequence (ultimately we didn't use this, so it could be commented out)
*/
    @Override
    public boolean getStartLift(){
        return (controller1.getRawAxis(2)>startLift&&controller1.getRawAxis(3)>startLift&&controller1.getRawButton(7)&&controller1.getRawButton(8));
    }
/**
   * translate the driver input into a request to stop the lift sequence (ultimately we didn't use this, so it could be commented out)
*/
    @Override
	    public boolean getStopLift(){
	        return(controller1.getRawButton(9)&&controller1.getRawButton(10));
        }
         //All of the "not in use" methods are the reason why the way we used
	//the interface DriveController makes no sense. Ignore them. Let's not do this again.   
        public boolean getEngageFrontSolenoid(){
            return false;
	    	//not in use
        }

        public boolean getEngageBackSolenoid(){
            return false;
			//not in use
        }

        public boolean getRetractFrontSolenoid(){
            return false;
			//not in use
        }

        public boolean getRetractBackSolenoid(){
            return false;
			//not in use
        }

        public boolean getDriveLiftForward(){
            return false;
			//not in use
        }

    }
    


  
    

