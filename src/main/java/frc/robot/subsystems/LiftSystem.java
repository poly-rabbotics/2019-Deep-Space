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

  private DoubleSolenoid frontLeft = RobotMap.liftSystemFrontLeft;
  private DoubleSolenoid frontRight = RobotMap.liftSystemFrontRight;
  private DoubleSolenoid backLeft = RobotMap.liftSystemBackLeft;
  private DoubleSolenoid backRight = RobotMap.liftSystemBackRight;
  private final double driveSpeed = .5;//TODO: Add real value

  private VictorSP leftWheel = RobotMap.leftLiftWheel;
  private VictorSP rightWheel = RobotMap.rightLiftWheel;

  public LiftSystem(){
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
    backLeft.set(Value.kForward);
    backRight.set(Value.kForward);
  }
  public void driveForward(){
    leftWheel.set(driveSpeed);
    rightWheel.set(driveSpeed);
  }
  public void withdrawFrontSolenoids(){
    frontLeft.set(Value.kReverse);
    frontRight.set(Value.kReverse);
  }
  public void finishLift(){
    leftWheel.set(0);
    rightWheel.set(0);
    backLeft.set(Value.kReverse);
    backRight.set(Value.kReverse);
  }





  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
