/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class WheelArm extends Subsystem {
  private PWMVictorSPX left = RobotMap.wheelArmLeft;
  private PWMVictorSPX right = RobotMap.wheelArmRight; // Did we change this to a PWMPWMVictorSPXX ?

  private static double WHEEL_ARM_OUTTAKE = .40; // TODO: Add real values
  private static double WHEEL_ARM_INTAKE = .90;

  public WheelArm() {
    super("Wheel Arm");
    addChild("Left Motor", left);
    addChild("Right Motor", right);
    left.setInverted(false);
    right.setInverted(true);

  }

  public void stopArms() {
    left.set(0);
    right.set(0);
  }

  public void runInwards() {
    left.set(WHEEL_ARM_INTAKE);
    right.set(WHEEL_ARM_INTAKE);
  }

  public void runOutwards() {
    left.set(WHEEL_ARM_OUTTAKE);
    right.set(WHEEL_ARM_OUTTAKE);
  }

  /*
   * public boolean isInwards(){ return inwards; } public boolean isOutwards(){
   * return outwards; }
   */
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
