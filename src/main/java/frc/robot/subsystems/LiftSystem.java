/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
/*
 * Add your docs here.
 */
public class LiftSystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private int driveSpeed = .5;

  private DoubleSolenoid frontLeft = RobotMap.liftSystemFrontLeft;
  private DoubleSolenoid frontRight = RobotMap.liftSystemFrontRight;
  private DoubleSolenoid backLeft = RobotMap.liftSystemBackLeft;
  private DoubleSolenoid backRight = RobotMap.liftSystemBackRight;
  private Timer t = new Timer();

  private VictorSP leftWheel = RobotMap.leftLiftWheel;
  private VictorSP rightWheel = RobotMap.rightLiftWheel;

  public HatchPusher(){
    super("Lift System");
    addChild("Front Left Solenoid", frontLeft);
    addChild("Front Right Solenoid", frontRight);
    addChild("Back Left Solenoid", backLeft);
    addChild("Back Right Solenoid", backRight);
    addChild("Left Lift Wheel", leftWheel);
    addChild("Right Lift Wheel", rightWheel);
  }

  public void engageSolenoids(){
    frontLeft.set(Value.kForward);
    frontRight.set(Value.kForward);
    backleft.set(Value.kForward);
    backRight.set(Value.kForward);
  }
  public void driveForward(){
    leftWheel.set(driveSpeed);
    rightWheel.set(driveSpeed);
  }
  public void retractBackSolenoids(){
    frontLeft.set(Value.kBackward);
    frontRight.set(Value.kBackward);
  }
  public void endingLiftSequence(){
    leftWheel.set(0);
    rightWheel.set(0);
    backLeft.set(Value.kBackward);
    backRight.set(Value.kBackward);

  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
