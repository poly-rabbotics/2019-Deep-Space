/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.VictorSP;
import frc.robot.RobotMap;
/**
 * Add your docs here.
 */
public class ArmAngle extends Subsystem {
  private VictorSP angle = RobotMap.wheelArmAngle;
  private static double armAngleSpeed = .3;//TODO: Add real value
  public ArmAngle(){
    super("Arm Angle");
    addChild("Angle Motor", angle);
  }
  public void spinUpwards(){
    angle.set(armAngleSpeed);
  }
  public void spinDownwards(){
    angle.set(-armAngleSpeed);
  }
  public void stopSpin(){
    angle.set(0);
  }

  
  @Override
  public void initDefaultCommand() {

    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
