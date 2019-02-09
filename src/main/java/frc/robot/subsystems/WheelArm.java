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
private Talon right = RobotMap.wheelArmRight;

private boolean inwards = false;
private boolean outwards = false;
private static double wheelArmSpeed = .3;//TODO: Add real value
public WheelArm(){
    super("Wheel Arm");
    addChild("Left Motor", left);
    addChild("Right Motor", right);
    
  }
  public void spinInwards(){
    inwards = true;
    left.set(wheelArmSpeed);
    right.set(-wheelArmSpeed);

  }
  public void spinOutwards(){
    outwards = true;
    left.set(-wheelArmSpeed);
    right.set(wheelArmSpeed);

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
