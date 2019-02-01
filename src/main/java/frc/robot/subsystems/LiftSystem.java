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

  private DoubleSolenoid frontLeft = RobotMap.liftSystemFrontRight;
  private DoubleSolenoid frontRight = RobotMap.liftSystemFrontRight;
  private DoubleSolenoid backLeft = RobotMap.liftSystemFrontRight;
  private DoubleSolenoid backRight = RobotMap.liftSystemFrontRight;
  private Timer t= new Timer();

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

  public void lift(){
    frontLeft.set(Value.kForward);
    frontRight.set(Value.kForward);
    backleft.set(Value.kForward);
    backRight.set(Value.kForward);
    t.delay(3.0);
    leftWheel.set(Value.kForward);
    rightWheel.set(Value.kForward);
    t.delay(1.5);
    frontLeft.set(Value.kBackward);
    frontRight.set(Value.kBackward);
    t.delay(2.5);
    leftWheel.set(Value.kStop);
    rightWheel.set(Value.kStop);
    backLeft.set(Value.kBackward);
    backRight.set(Value.kBackward);






  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
