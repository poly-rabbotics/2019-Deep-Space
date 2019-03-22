/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
/*
 * Add your docs here.
 */
public class LiftSystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private  double driveSpeed = .75;

  private DoubleSolenoid front = RobotMap.liftSystemFront;
  private DoubleSolenoid back = RobotMap.liftSystemBack;
  
  

  private VictorSP leftWheel = RobotMap.leftLiftWheel;
  private VictorSP rightWheel = RobotMap.rightLiftWheel;

  public LiftSystem(){
    super("Lift System");
    addChild("Front Solenoid", front);
    addChild("Back Solenoid", back);
    addChild("Left Lift Wheel", leftWheel);
    addChild("Right Lift Wheel", rightWheel);
  }

  public void engageSolenoids(){
    front.set(Value.kForward);
    
    back.set(Value.kForward);
   
  }
  public void driveForward(){
    leftWheel.set(driveSpeed);
    rightWheel.set(driveSpeed);
  }
  public void retractFrontSolenoids(){
    front.set(Value.kReverse);
    
  }
  public void endLiftSequence(){
    leftWheel.set(0);
    rightWheel.set(0);
    back.set(Value.kReverse);
    

  }
  public void retractAllSolenoids(){
    front.set(Value.kReverse);
    back.set(Value.kReverse);
  }
  public void stopMotors(){
    leftWheel.set(0);
    rightWheel.set(0);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
