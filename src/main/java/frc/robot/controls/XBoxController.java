/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controls;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.RobotMap;
import static org.usfirst.frc.team4999.utils.Utils.*;

/**
 * Add your docs here.
 */
public class XBoxController implements DriveController{
    private XboxController controller2 = RobotMap.controller2;

    private static final double CURVE = 2;
    private static final double DEADZONE = .01;
    private static final double startLift = .75;

    private double[] speedLimits = {0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0};
    private int speedLimitIndex = speedLimits.length-1;
    @Override
    public double getMoveRequest(){
      double speed = controller2.getRawAxis(1);
        speed = curve(speed, CURVE);
        speed = deadzone(speed, DEADZONE);
        return speed;
        
    
    }
    
    @Override
    public double getTurnRequest(){
        double speed = controller2.getRawAxis(4);
        speed = curve(speed, CURVE);
        speed = deadzone(speed, DEADZONE);
        return speed;

    }
    @Override
    public double getSpeedLimit(){
       if(controller2.getRawButtonPressed(7) && speedLimitIndex>0)
            speedLimitIndex--;
        if(controller2.getRawButtonPressed(8) && speedLimitIndex<speedLimits.length-1)
            speedLimitIndex++;
        return speedLimits[speedLimitIndex];
        
    }
    
    @Override
    public boolean getReverseDirection(){
        return controller2.getRawButtonPressed(3);
    }

    @Override
    public boolean getToggleHatchPusher(){
        return controller2.getRawButton(11);
        //Not in use
    }

    @Override
    public boolean getToggleInwards(){
        return false; //controller2.getRawButtonPressed(15);
        //Not in use
    }

    @Override
    public boolean getToggleOutwards(){
        return controller2.getRawButtonPressed(13);
        //Not in use
    }

    
    public boolean getMoveArmsUp(){
        return controller2.getRawButton(12);
        //Not in use
    }

    @Override
    public boolean getMoveArmsDown(){
        return controller2.getRawButton(10);
        //not in use
    } 
    
    @Override
    public boolean getStartLift(){
        return (controller2.getRawAxis(2)>startLift&&controller2.getRawAxis(3)>startLift&&controller2.getRawButton(7)&&controller2.getRawButton(8));
	      //not in use
    }

    @Override
	    public boolean getStopLift(){
            return(controller2.getRawButton(9)&&controller2.getRawButton(10));
            //not in use
        }
        
        public boolean getEngageFrontSolenoid(){
            return(controller2.getRawButtonPressed(5)); //TODO: assign actual buttons
        }

        public boolean getEngageBackSolenoid(){
            return(controller2.getRawButtonPressed(6));
        }

        public boolean getRetractFrontSolenoid(){
            return(controller2.getRawButtonPressed(2)); // TODO: assign 
        }

        public boolean getRetractBackSolenoid(){
            return(controller2.getRawButtonPressed(4));
        }

        public boolean getDriveLiftForward(){
            return(controller2.getRawButton(1));
        }
	}

  
    

