/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controls;
import org.usfirst.frc.team4999.controllers.LogitechF310;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.RobotMap;
import static org.usfirst.frc.team4999.utils.Utils.*;

/**
 * Add your docs here.
 */
public class F310Controller implements DriveController{
    private LogitechF310 controller1 = RobotMap.controller1;

    private static final double CURVE = 2;
    private static final double DEADZONE = .01;
    private static final double startLift = .75;

    private double[] speedLimits = {0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0};
    private int speedLimitIndex = speedLimits.length-1;
    @Override
    public double getMoveRequest(){
        double speed = controller1.getRawAxis(1);
        speed = curve(speed, CURVE);
        speed = deadzone(speed, DEADZONE);
        return speed;

    }
    @Override
    public double getTurnRequest(){
        double speed = controller1.getRawAxis(4);
        speed = curve(speed, CURVE);
        speed = deadzone(speed, DEADZONE);
        return speed;

    }
    @Override
    public double getSpeedLimit(){
        if(controller1.getRawButtonPressed(7) && speedLimitIndex>0)
            speedLimitIndex--;
        if(controller1.getRawButtonPressed(8) && speedLimitIndex<speedLimits.length-1)
            speedLimitIndex++;
        return speedLimits[speedLimitIndex];
    }
    @Override
    public boolean getReverseDirection(){
        return controller1.getRawButtonPressed(3);
    }

    @Override
    public boolean getToggleHatchPusher(){
        return controller1.getRawButton(2);
    }

    @Override
    public boolean getToggleInwards(){
        return controller1.getRawButtonPressed(6);
    }

    @Override
    public boolean getToggleOutwards(){
        return controller1.getRawButtonPressed(5);
    }

    
    public boolean getMoveArmsUp(){
        return controller1.getRawButtonPressed(4);
    }

    @Override
    public boolean getMoveArmsDown(){
        return controller1.getRawButtonPressed(1);
    } 
    
    @Override
    public boolean getStartLift(){
        return (controller1.getRawAxis(2)>startLift&&controller1.getRawAxis(3)>startLift&&controller1.getRawButton(7)&&controller1.getRawButton(8));
	      
    }

    @Override
	    public boolean getStopLift(){
	        return(controller1.getRawButton(9)&&controller1.getRawButton(10));
	    }
	}

  
    

