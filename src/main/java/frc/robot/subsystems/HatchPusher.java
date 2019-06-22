/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * The HatchPusher is the part of the robot that tells the robot to push the hatch off
   * the hook-and-loop tape. It has to do two things: extend the cylinders that push off
   * the hatch, and retract them.
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
  */

package frc.robot.subsystems; //says what package this file is in. Explained in ArmAngle.java.

//Solenoids are like motor controllers, except for pneumatics. They are connected to both wires (for
//for control data from the PCM, or pneumatics control module) and tubes (so they can control the 
//flow or air).
//Double solenoids control both extension and retraction, instead of just extension.
import edu.wpi.first.wpilibj.DoubleSolenoid; //This file has to know what a Double Solenoid is, so we 
                                             //get that information from FIRST.
import edu.wpi.first.wpilibj.DoubleSolenoid.Value; //This is used for readability, so that we can refer
                                                  //to the retracted state as Value.kReverse and the 
                                                  //extended state as Value.kForward. The code would be 
                                                  //more confusing if we just said "false" or "true" when 
                                                  //we meant "reverse" or "forward"
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;//know what motors/motor controllers we have and where they're connected

/**
 * Add your docs here.
 */
public class HatchPusher extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private DoubleSolenoid top = RobotMap.hatchSolenoidTop; //A badly named variable. top is refers to 
                                                          //the solenoid that controls all the air cylinders
                                                          //we're working with.
  

 private boolean out = false; //Say whether the air cylinders are extended. I don't know if 
                              //we ever needed this.


  public HatchPusher(){ //Constructor for a subsystem. Explained in ArmAngle.java
    super("Hatch Pusher");
    addChild("Top Solenoid", top);
   
  }

  /**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * Tell the hatch system how to extend (push off the hatch)
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
  */
  public void extend(){
   out = true;
    top.set(Value.kForward);
    

  }
  /**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * Tell the hatch system how to retract the air cylinders (after pushing off the hatch)
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
  */
  public void retract(){
    out = false;
    top.set(Value.kReverse);//Value is explained above ^
    
  }
  //A getter. Tell other parts of our code if the air cylinders are extended.
  public boolean isOut(){
    return out;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
