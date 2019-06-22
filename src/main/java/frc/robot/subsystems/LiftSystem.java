/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * Our most complicated subsystem. The LiftSystem, apparently, does nine things, but
   * those nine things are just combinations of the same SIX things: lift up the front,
   * lower the front, lift up the back, lower the back, drive forward, stop driving forward.
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
  */

package frc.robot.subsystems; //Say what package this file is in. Explained in ArmAngle.java

import edu.wpi.first.wpilibj.DoubleSolenoid; //This file uses DoubleSolenoids, so it has to know 
                                             //what those are and what they do. Solenoids
                                             //explained in HatchPusher.java.
import edu.wpi.first.wpilibj.VictorSP; //The motor controller used to drive forward in the LiftSystem.
import edu.wpi.first.wpilibj.DoubleSolenoid.Value; //Used so we can say Value.kForward to mean we want 
                                                   //an air cylinder extended and Value.kReverse to mean
                                                   //we want an air cylinder extended. Explained in 
                                                   //HatchPusher.java
import edu.wpi.first.wpilibj.command.Subsystem; //This "is a" Subsystem, so we have to know what that is
import frc.robot.RobotMap; //know what motor controllers we have and where they're connected.
/*
 * Add your docs here.
 */
public class LiftSystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private  double driveSpeed = .5; //We could go twice as fast driving forward, but since accuracy is important,
                                   //we're going slowly.
  
  //The following is commented out because it is an experiment we never finished. Please ignore.
  /*
  private int cycleTime1 = 500;
  private double cycleRatio1 = .85;
  private int cycleTime2 = 500;
  private double cycleRatio2 = .85;
  */
  
  private DoubleSolenoid front = RobotMap.liftSystemFront; //From now on, we say "front" to refer to the
                                                           //double solenoid in the file RobotMap called liftSystemFront.
  private DoubleSolenoid back = RobotMap.liftSystemBack;   //Same ^
 
  private VictorSP leftWheel = RobotMap.leftLiftWheel;    //Same ^ but for the left wheel of the lift
  private VictorSP rightWheel = RobotMap.rightLiftWheel;  //Same ^
  //Comment: Since leftWheel and rightWheel move together (since we don't turn), we could have put them in 
  //a SpeedControllerGroup. However, we thought we might want to turn, so we didn't do that.

  public LiftSystem(){                  //Subsystem constructors explained in ArmAngle.java
    super("Lift System");
    addChild("Front Solenoid", front);
    addChild("Back Solenoid", back);
    addChild("Left Lift Wheel", leftWheel);
    addChild("Right Lift Wheel", rightWheel);
  }

  /**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * Tell the LiftSystem how to raise and lower the front of the robot.
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
  */
  public void engageFrontSolenoids(){
      front.set(Value.kForward);
  }
  public void retractFrontSolenoids(){
      front.set(Value.kReverse);
  }
  /**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * Tell the LiftSystem how to raise and lower the back of the robot.
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
  */
  public void engageBackSolenoids(){
      back.set(Value.kForward);
  }
  public void retractBackSolenoids(){
      back.set(Value.kReverse);
  }
  /**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * Tell the LiftSystem how to drive forward and how to stop.
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
  */
  public void driveForward(){
    leftWheel.set(driveSpeed);
    rightWheel.set(driveSpeed);
  }
  public void stopMotors(){
    leftWheel.set(0);
    rightWheel.set(0);
  }
  
  //The following are combinations of different actions. Since Mr. Gever told us
  //to have more driver control before we combined actions and automated things, I
  //think we just didn't use these
  public void endLiftSequence(){
    leftWheel.set(0);
    rightWheel.set(0);
    back.set(Value.kReverse);
  }
  public void engageAllSolenoids(){ //I don't think we ever use this.
    front.set(Value.kReverse);
    back.set(Value.kReverse);
  }
  public void retractAllSolenoids(){ //I don't think we ever use this.
    front.set(Value.kReverse);
    back.set(Value.kReverse);
  }

  @Override //annotation explained in ArmAngle.java
  public void initDefaultCommand() { //explained in ArmAngle.java
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
