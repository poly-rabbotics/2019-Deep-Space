/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

private VictorSP left = RobotMap.wheelArmLeft;
private VictorSP right = RobotMap.wheelArmRight;

private boolean inwards = false;
private boolean outwards = false;
public class WheelArm extends Subsystem {

public WheelArm(){
    super("Wheel Arm");
    addChild("Left Motor", left);
    addChild("Right Motor", right);
    
  }
  public void spinInwards(){
    inwards = true;
    left.set(Value.kForward);//Check if Forwards means clockwise
    right.set(Value.kReverse);

  }
  public void spinOutwards(){
    outwards = true;
    left.set(Value.kReverse);
    right.set(Value.kForward);

  }
  public void stopArms(){
    inwards = false;
    outwards = false;
    left.set(Value.kOff);
    right.set(Value.kOff)



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
