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
  
  private DigitalInput armSwitch2 = RobotMap.armSwitch2;
  private static double armAngleSpeedUp = .5;
  private static double armAngleSpeedDown = -.5;
  public boolean moving = false;

  public ArmAngle(){
    super("Arm Angle");
    addChild("Angle Motor", angle);
  }
  public void spinUpwards(){
    System.out.println(angle.get());
    if(!armSwitch.get()){
    angle.set(armAngleSpeedUp);
    moving = true;
    }
    else{
      angle.set(.1);
      moving = false;
    }
    
  }
  public boolean returnUpper()
  {
    return armSwitch.get();
  }
  public boolean returnLower()
  {
    return armSwitch2.get();
  }
  public void spinDownwards(){
    System.out.println(angle.get());
    if(!armSwitch2.get()){
      angle.set(armAngleSpeedDown);
      
      moving = true;
      }
      else{
        angle.set(-.1);
        moving = false;
      }
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
