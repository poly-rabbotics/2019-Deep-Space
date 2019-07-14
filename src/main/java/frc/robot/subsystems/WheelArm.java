/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
/**
* ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
* This file defines what a WheelArm is and tells it how to intake (spinInwards), outtake
* (spinOutwards), stop (stopArms) and hold a ball (neutral).
* ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
*/
package frc.robot.subsystems; //Say what package this file is in. Explained in ArmAngle.java.

//We need to use the Talon and VictorSP motor controllers, so we'd better import them from FIRST.
//import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem; //The thing we are making "is a" subsystem, so we'd
                                                //better know what a Subsystem is first.
import frc.robot.RobotMap; //This file has to know what motor controllers etc. it is working with 
                           //where they're connected.


public class WheelArm extends Subsystem {
private VictorSP left = RobotMap.wheelArmLeft;  //left now refers to the same motor controller in 
                                                //RobotMap called wheelArmLeft.
private VictorSP right = RobotMap.wheelArmRight;//right now refers to the same motor controller in 
                                                //RobotMap called wheelArmRight.

private boolean inwards = false;   //Keep track if the intake is intaking or outtaking. Not sure if this
private boolean outwards = false;  //is necessary.
private static double wheelInSpeed = .90; //say how fast the intake goes when intaking
private static double rocket2Speed = .90; //Say how fast the intake goes when shooting to rocket level 2. Failed experiment. We can't shoot to the rocket level 2.
private static double wheelOutSpeed = .5;//say how fast the intake goes when outtaking (shooting out a ball)
public WheelArm(){ //Subsystem constructor explained in ArmAngle.java
    super("Wheel Arm");
    addChild("Left Motor", left); //say that this subsystem needs to use the motor controllers leftMotor and RightMotor.
    addChild("Right Motor", right);
  }
  /**
* ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
* Say how to spin inwards and how to spin outwards.
* ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
*/
  public void spinInwards() {
    inwards = true;
    left.set(wheelInSpeed);
    right.set(-wheelInSpeed); //YUCK. These should be in a SpeedControllerGroup set up so that the motors 
                              //spin together, in opposite directions. The left/right reverse speed should
                              //not be done by the electrical team flipping things around, nor should it be
                              //done like this, with a minus sign. Never do this!
  }
  public void spinOutwards() {
    outwards = true;
    left.set(-wheelOutSpeed); //Same as above ^  Don't do this.
    right.set(wheelOutSpeed);

  }
  /**
   * This is outtaking faster so that the cargo (ball) can go high enough to reach
   * rocket level 2. We can't do that though.
   */
  public void spinOutwardsBig(){
    outwards = true;
    left.set(-rocket2Speed);
    right.set(rocket2Speed);
  }
  /**
* ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
* Say how to stop (when not holding a ball)
* ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
*/
  public void stopArms() {
    inwards = false;
    outwards = false;
    left.set(0);
    right.set(0);
  }
    /**
* ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
* Say how to stop (when holding a ball). The difference is that the motors are given a 
* small amount of power to go slightly inward, and hold the ball in place. Unfortunately,
* since this stalls the motors (makes them try to move inward when they can't), we broke
* some fragile air-cooled motors this way.
* ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
*/
  public void neutral() {
    left.set(-.05);
    right.set(.05);
  }
  
  //These are basically getters -- perhaps they should be called "getInwards" and "getOutwards"
  //They say if the intake is going in or if it's going out. (note that both could be false if 
  //it's stopped)
  public boolean isInwards() {
    return inwards;
  }
  public boolean isOutwards() {
    return outwards;
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
