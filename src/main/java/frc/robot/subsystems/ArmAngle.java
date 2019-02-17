/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Talon;               //Are we just using Victor SPX ? Why are we not using PWMVictorSPX class?
import edu.wpi.first.wpilibj.VictorSP;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.PIDController;

/**
 * Add your docs here.
 */

public class ArmAngle extends Subsystem {
  
  private static final int DOWN_DEGREES = 0;         //TODO: Add real value
  private static final int ROCKET_DEGREES = 30;
  private static final int CARGO_SHIP_DEGREES = 45;
  private static final int UP_DEGREES = 75;
  private static final int[] POSITIONS = {DOWN_DEGREES, ROCKET_DEGREES, CARGO_SHIP_DEGREES, UP_DEGREES};
  
  private int position;
  
  private VictorSP angle = RobotMap.wheelArmAngle;
  private CTREMagneticEncoder encoder = RobotMap.wheelArmEncoder;
  private PIDController wheelArmController = new PIDController(0.1, 0.01, 0.1, &wheelArmEncoder, &angle);
  //private static double armAngleSpeed = .5;   //TODO: Add real value
  //public boolean moving = false;
  public ArmAngle(){
    super("Arm Angle");
    addChild("Angle Motor", angle);
    position = 3;
  }
  /*public void spinUpwards(){
    angle.set(armAngleSpeed);
    moving = true;
  }
  public void spinDownwards(){
    angle.set(-armAngleSpeed);
    moving = true;
  }*/
  public void spinUpwards() {
    if (position < 3)
      position++;
    
    wheelArmController.setSetPoint(POSITIONS[position]);
  }
  public void spinDownwards() {
    if (position > 0)
      position--;
    
    wheelArmController.setSetPoint(POSITIONS[position]);
  }
  public void stopSpin(){
    wheelArmController.setSetPoint(POSITIONS[position])
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
