/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/**
* ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
* This file defines what an ArmAngle is. (Ideally, it would have a more descriptive
* name.) An ArmAngle is the subsystem that lifts up our arm, lowers it, or stops it.
* ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
*/


package frc.robot.subsystems; //tells us what package this file is in. This lets 
                              //this file access other files in the same package.
                              //Note that the package is the same as the file path,
                              //except with . instead of /

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;
/**
* Talon and VictorSP are motor controllers. They're the little boxes between the
* Power Distribution Panel (the big red and black thing with all the little black 
* boxes, or breakers, on it) and the motors. They connect to the RoboRIO too and 
* are the way we control the motors.
* 
* Here, we're importing them as a type (or class) of somethings that do somethings.
* That way, we can represent objects that exist in the real world inside our code.
*/
//import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;
/**
* We need to know what ports everything is connected in (and what all our motor
* controllers are). Another part of our code already does know that, but since that 
* code is in a different folder, this file here does not know it yet. So, we have 
* to import the code that does know:
*/
import frc.robot.RobotMap;
/**
 * Add your docs here.
 */
public class ArmAngle extends Subsystem { //Means ArmAngle is a type of subsystem.
  //From now on, when we refer to "angle," we are referring to the motor controller
  //defined in RobotMap called wheelArmAngle. When we talk about "angle", we are
  //literally referencing the exact same thing. It's defined here as a matter of 
  //convenience, so we can refer to "angle" instead of "RobotMap.wheelArmAngle"
  private VictorSP angle = RobotMap.wheelArmAngle;
  //Same with armSwitch. RobotMap already defined the limit switch and said where it
  //was connected, but we want to call it "armSwitch" instead of "RobotMap.armSwitch."
  private DigitalInput armSwitch = RobotMap.armSwitch;
  
  //Same as above ^
  private DigitalInput armSwitch2 = RobotMap.armSwitch2;
  //Say how "fast" the arm will go when it's going up.
  private static double armAngleSpeedUp = .5;
  //Say how "fast" the arm will go when it's going down.
  private static double armAngleSpeedDown = -.5;
  //In the beginning, the arm is assumed not to be moving.
  //This variable is public so other parts of the code will know if 
  //the arm is moving or not.
  public boolean moving = false;

  /**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * Public ArmAngle is just saying that this subsystem needs to use the motor controller
   * called "angle".
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
  */
  //This is called a constructor. It makes things (it makes objects)
  //We know it's a constructor b/c it has the same name as the class 
  //ArmAngle and there is no return type (void, boolean, int, 
  //double, etc.)
  public ArmAngle(){
    //This is confusing and you can ignore it at first.
    //Subsystem comes with its own constructor, and since ArmAngle 
    //literally "is a" Subsystem, it has that constructor too. Super says,
    //Okay, even though I'm writing another constructor that normally would
    //override the parent Subsystem constructor, I still want that parent
    //constructor to run, and I want to pass "Arm Angle" to it to tell it that
    //this particular subsystem is called "Arm Angle"
    super("Arm Angle");
    //This is a different kind of child from the objects and inheritance kind
    //of programming. It's just saying, this Subsystem needs to use this 
    //particular motor controller: angle, which is the same thing as 
    //RobotMap.wheelArmAngle.
    addChild("Angle Motor", angle);
  }
  /**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * This just tells the robot how to spin the arm upwards.
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
  */
  public void spinUpwards(){
    System.out.println(angle.get());
    if(!armSwitch.get()){ //only spin up if the limit switch is not pressed.
    angle.set(armAngleSpeedUp);
    moving = true; //Now we know the arm is moving.
    }
    else{
      angle.set(.1); //Slight forward voltage to keep gravity from dragging arm down.
      moving = false; //Now we know the arm is NOT moving.
    }
    
  }
  
  //The next two methods are used to find out if the limit switches are pressed 
  //(limit switches are the little black boxes with metal arms that are pressed 
  //when the arm has gone too far)
  //
  //We didn't need these methods, and we probably shouldn't have defined them.
  //Their names are also confusing.
  public boolean returnUpper()
  {
    return armSwitch.get();
  }
  public boolean returnLower()
  {
    return armSwitch2.get();
  }
  /**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * This just tells the robot how to spin the arm downwards.
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
  */
  public void spinDownwards(){
    System.out.println(angle.get());
    if(!armSwitch2.get()){
      angle.set(armAngleSpeedDown);
      
      moving = true;
      }
      else{
        angle.set(-.1); //unnecessary because gravity is already keeping the arm down.
                        //However, the only harm it really does is make the motor 
                        //overheat a little, since it's working against the mechanical stop.
        moving = false;
      }
  }
  /**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * This just tells the robot how to stop moving the arm.
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
  */
  public void stopSpin(){
    angle.set(0);
    moving = false;
  }
  //This is called a "getter." It tells us whether the robot arm is moving.
  public boolean getMoving(){
    return moving;
  }
 
  
  @Override //This is called an annotation. It's not a comment, but it's not really code
            //either. It's like a comment in that it tells other people what you're doing,
            //but it's not a comment because the compiler might actually look at it too.
            //IT TELLS US THAT WE ARE OVERRIDING A METHOD DEFINED IN THE PARENT
            //CLASS Subsystem. Some compilers will actually tell you you have an error 
            //if you use this annotation but it turns out you aren't actually overriding anything.
  //Apparently we were required to override initDefaultCommand, even though we didn't want to use it.
  public void initDefaultCommand() {

    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
