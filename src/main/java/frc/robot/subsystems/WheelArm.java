/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;


public class WheelArm extends Subsystem {
private VictorSP left = RobotMap.wheelArmLeft;
private VictorSP right = RobotMap.wheelArmRight;           //Did we change this to a PWMVictorSPX ?

private boolean inwards = false;
private boolean outwards = false;
private static double wheelArmOuttake = .40;//TODO: Add real value
private static double wheelArmIntake = .80;
public WheelArm(){
    super("Wheel Arm");
    addChild("Left Motor", left);
    addChild("Right Motor", right);
    
  }
  public void spinInwards(){
    inwards = true;
    left.set(wheelArmIntake);
    right.set(-wheelArmIntake);

  }
  public void spinOutwards(){
    outwards = true;
    left.set(-wheelArmOuttake);
    right.set(wheelArmOuttake);

  }
  public void stopArms(){
    inwards = false;
    outwards = false;
    left.set(0);
    right.set(0);



  }
  
  public boolean isInwards(){
    return inwards;
  }
  public boolean isOutwards(){
    return outwards;
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
