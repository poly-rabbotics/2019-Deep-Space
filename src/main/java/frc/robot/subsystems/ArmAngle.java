/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Talon; //Are we just using Victor SPX ? Why are we not using PWMVictorSPX class?
import edu.wpi.first.wpilibj.VictorSP;
import frc.robot.RobotMap;
import frc.robot.controls.DriveController;
/**
 * Add your docs here.
 */
public class ArmAngle extends Subsystem {
  private double armAngleSpeed = RobotMap.controller1.getArmsSpeed();
  private VictorSP angle = RobotMap.wheelArmAngle;
  public ArmAngle(){
    super("Arm Angle");
    addChild("Angle Motor", angle);
  }
  public void spinSpeed(){
    angle.set(armAngleSpeed);
  }
 
  @Override
  public void initDefaultCommand() {

    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
