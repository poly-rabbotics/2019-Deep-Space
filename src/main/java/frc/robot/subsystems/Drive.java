/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

  /**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * Drive is the subsystem that lets us drive around. FIRST made a "something that does 
   * something" called DifferentialDrive that basically does that for us -- we just have
   * tell DifferentialDrive what motors/motor controllers it's working with and what
   * we want it to do.
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
  */

package frc.robot.subsystems; //tells us what package this file is in. This lets 
                              //this file access other files in the same package.
                              //Note that the package is the same as the file path,
                              //except with . instead of /

//SpeedControllerGroup lets us make a group of motor controllers all run together.
//It's useful for systems like our intake, where the left and right sides were always turning
//at the same speed at the same time, just in reverse directions.
import edu.wpi.first.wpilibj.SpeedControllerGroup;
//We are describing a kind of Subsystem, so we need to know what a Subsystem is first.
import edu.wpi.first.wpilibj.command.Subsystem;
//This subsystem uses a type of drive called a DifferentialDrive, so we need to import that
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//import edu.wpi.first.wpilibj.Spark; //A Spark is just a cheap white motor controller that we know and love
import frc.robot.RobotMap; //We need to know what motor controllers we have, and where they're connected
//import frc.robot.commands.DriveCommand; //I have no idea why we need this, ask Juliana or Mr. Powers

//We borrowed code from Momentum (Team 4999), just so we could use their method called "map"
import static org.usfirst.frc.team4999.utils.Utils.map;

/**
 * Add your docs here.
 */
public class Drive extends Subsystem { //This says that Drive "is a" Subsystem. We are just inheriting and adding
                                       //to the characteristics and capabilities of a Subsystem.
  //In our tank  drivetrain, all motors on the left side move together, and all motors on the right move together.
  //They have to, because they're connected to the same gearbox.
  //Therefore, they're grouped together.
  private SpeedControllerGroup leftMotors = new SpeedControllerGroup(RobotMap.leftFront, RobotMap.leftBack);
  private SpeedControllerGroup rightMotors = new SpeedControllerGroup(RobotMap.rightFront, RobotMap.rightBack);

  //Here we tell the DifferentialDrive called drive what groups of speed controllers it's working with.
  private DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);


  public Drive(){ //Constructor (explained in ArmAngle)
    super("Drive"); //Uses the Subsystem constructor, since Subsystem is the parent class (explained in ArmAngle)
    addChild("Left Motors", leftMotors);
    addChild("Right Motors", rightMotors);
    drive.setDeadband(0); //A deadband is used to ignore any controls coming from the controller that are very small.
                          //We need it because sometimes, the controller isn't perfect, and it will think we are saying the
                          //robot should be moving very slowly when we actually aren't telling it to move at all.
                          //Since the deadband is 0, we apparently aren't worried about that.
  }
  /**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * arcadeDrive tells the drivetrain how to drive, once we've told it how fast we want to 
   * move forward (moveRequest) and how fast we want to turn (turnRequest).
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
  */
  public void arcadeDrive(double moveRequest, double turnRequest, double speedLimiter){ //speedLimiter defines max speed.
    //the next two lines change moveRequest and turnRequest to make sure we don't go too fast.
    moveRequest = map(moveRequest, -1, 1, -speedLimiter, speedLimiter); //map gets an input between -1 and +1 and returns 
                                                                        //a value between -(speedLimiter) and +(speedLimiter)
    turnRequest = map(turnRequest, -1, 1, -speedLimiter, speedLimiter);
    drive.arcadeDrive(moveRequest, turnRequest, false);
  }
  @Override //annotations explained in ArmAngle.java
  public void initDefaultCommand() { //explained in ArmAngle.java. If you want to know what this method is normally for,
                                     //ask Juliana or Mr. Powers. Anyway, we didn't use it.
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
  
}
