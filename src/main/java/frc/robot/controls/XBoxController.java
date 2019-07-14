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

package frc.robot.controls; //Duplicate code from F310Controller.java

import edu.wpi.first.wpilibj.XboxController; //Duplicate code from F310Controller.java
//import edu.wpi.first.wpilibj.GenericHID.Hand; //Duplicate code from F310Controller.java
import frc.robot.RobotMap;//Duplicate code from F310Controller.java
import static org.usfirst.frc.team4999.utils.Utils.*;//Duplicate code from F310Controller.java

/**
 * Add your docs here.
 */
public class XBoxController implements DriveController{//Duplicate code from F310Controller.java
    private XboxController controller2 = RobotMap.controller2;//Duplicate code from F310Controller.java

    private static final double CURVE = 2;//Duplicate code from F310Controller.java
    private static final double DEADZONE = .01;//Duplicate code from F310Controller.java
    //private static final double startLift = .75;//Duplicate code from F310Controller.java
 
    private double[] speedLimits = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0};
    private int speedLimitIndex = speedLimits.length - 1;
    @Override
    public double getMoveRequest(){//Duplicate code from F310Controller.java
      double speed = controller2.getRawAxis(1);
        speed = curve(speed, CURVE);
        speed = deadzone(speed, DEADZONE);
        return speed;
        
    
    }
    
    @Override
    public double getTurnRequest(){//Duplicate code from F310Controller.java
        double speed = controller2.getRawAxis(4);
        speed = curve(speed, CURVE);
        speed = deadzone(speed, DEADZONE);
        return speed;
    }
    /**
     * Change the maximum speed of the robot as needed and return the current maximum speed.
     */
    @Override
    public double getSpeedLimit() {//Duplicate code from F310Controller.java
        if(controller2.getRawButtonPressed(7) && speedLimitIndex>0)
            speedLimitIndex--;
        if(controller2.getRawButtonPressed(8) && speedLimitIndex<speedLimits.length-1)
            speedLimitIndex++;
        return speedLimits[speedLimitIndex];
    }
    
    @Override
    public boolean getReverseDirection(){//Duplicate code from F310Controller.java
        return controller2.getRawButtonPressed(3);
    }

    //All of the "not in use" methods are the reason why the way we used
	//the interface DriveController makes no sense. Ignore them. Let's not do this again.
    @Override
    public boolean getToggleHatchPusher(){
        return false;
        //Not in use
    }

    @Override
    public boolean getToggleInwards(){
        return false; //controller2.getRawButtonPressed(15);
        //Not in use
    }

    @Override
    public boolean getToggleOutwards(){
        return false;
        //Not in use
    }

    
    public boolean getMoveArmsUp(){
        return controller2.getRawButton(10);
        //Not in use
    }

    @Override
    public boolean getMoveArmsDown(){
        return false;
        //not in use
    } 
    
    @Override
    public boolean getStartLift(){
        return false;
	      //not in use
    }

    @Override
	    public boolean getStopLift(){
            return false;
            //not in use
        }
/**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * Translate from "should the front be raise up?" to "Is button #5 on controller #2 pressed?"
   * They're equivalent questions: the answer to one is the same as the answer to the other.
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
*/
        public boolean getEngageFrontSolenoid(){
            return(controller2.getRawButtonPressed(5));
        }

        public boolean getEngageBackSolenoid(){
            return(controller2.getRawButtonPressed(6));
        }

        public boolean getRetractFrontSolenoid(){
            return(controller2.getRawButtonPressed(2));
        }

        public boolean getRetractBackSolenoid(){
            return(controller2.getRawButtonPressed(4));
        }

        public boolean getDriveLiftForward(){
            return(controller2.getRawButton(1));
        }
	}

  
    

