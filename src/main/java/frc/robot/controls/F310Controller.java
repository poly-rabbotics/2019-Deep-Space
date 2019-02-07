/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controls;
//import org.usfirst.frc.team4999.controllers.LogitechF310;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.RobotMap;
//import static org.usfirst.frc.team4999.utils.Utils.*;

/**
 * Add your docs here.
 */
public class F310Controller implements DriveController{
  //  private LogitechF310 controller1 = RobotMap.controller1;
    private Joystick controller1 = RobotMap.controller1;

    private static final double CURVE = 2;
    private static final double DEADZONE = .03;

    private double[] speedLimits = {0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0};
    private int speedLimitIndex = speedLimits.length-1-3;
    @Override
    public double getMoveRequest(){
        // double speed = controller1.getY(Hand.kRight);
        // speed = curve(speed, CURVE);
        // speed = deadzone(speed, DEADZONE);
        double speed = controller1.getRawAxis(1);
        return speed;

    }
    @Override
    public double getTurnRequest(){
        // double speed = controller1.getX(Hand.kLeft);
        // speed = curve(speed, CURVE);
        // speed = deadzone(speed, DEADZONE);
        double speed = controller1.getRawAxis(0);
        return speed;

    }
    @Override
    public double getSpeedLimit(){
        // if(controller1.getXButtonPressed() && speedLimitIndex>0)
        //     speedLimitIndex--;
        // if(controller1.getYButtonPressed() && speedLimitIndex<speedLimits.length-1)
        //     speedLimitIndex++;
        return speedLimits[speedLimitIndex];
    }
    @Override
    public boolean getReverseDirection(){
        //return controller1.getBButtonPressed();
        return false;
    }

    
}
