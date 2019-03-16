/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;               //Are we just using Victor SPX ? Why are we not using PWMVictorSPX class?
import edu.wpi.first.wpilibj.VictorSP;
import frc.robot.RobotMap;
/**
 * Add your docs here.
 */
public class ArmAngle extends Subsystem {
  private VictorSP angle = RobotMap.wheelArmAngle;
  private DigitalInput armSwitch = RobotMap.armSwitch;
  private static double armAngleSpeed = .5;//TODO: Add real value
  public boolean moving = false;
  public ArmAngle(){
    super("Arm Angle");
    addChild("Angle Motor", angle);
  }
  public void spinUpwards(){
    if(!armSwitch.get()){
    angle.set(armAngleSpeed);
    moving = true;
    }
    else{
      angle.set(0);
      moving = false;
    }
    
  }
  public void spinDownwards(){
    angle.set(-armAngleSpeed);
    moving = true;
  }
  public void stopSpin(){
    angle.set(0);
    moving = false;
  }
  public boolean getMoving(){
    return moving;
  }
 
  
  @Override
  public void initDefaultCommand() {

    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
