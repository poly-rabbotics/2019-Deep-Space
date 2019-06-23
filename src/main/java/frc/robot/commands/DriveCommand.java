/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/**
* ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
* Literally all this file does is constantly ask to things: 
* a) how fast is the robot told to move in the forward/backward direction, and 
* b) how fast it is being told to turn
* (and the answer to either question can be zero or negative).
* Then, it just tells the drivetrain to act accordingly.
* And in this file, we don't care HOW the drivetrain does it -- we just have to tell
* it what we want it to do, and it does it.
* Nothing complicated.
* 
* And remember that this is constantly running ever since the robot was first commanded
* to respond to user input, and it is never finished responding to user input.
* ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
*/

//Most of this is the same as ArmAngleCommand.java
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.controls.DriveController;

public class DriveCommand extends Command { //A DriveCommand "is a" command.
  private boolean reverse = false;
  public DriveCommand() {
    requires(Robot.drive); //Says that this command is using the drivetrain, so any other action (command) 
                          //that was using the drivetrain had better stop now.
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    DriveController controller = Robot.controller2; //This only responds to controller2, not controller1.
    
    /**
      * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
      * The next two lines find out a) how fast the driver wants to go forward, and b) how fast the driver wants to turn
      * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
    */
    double moveRequest = controller.getMoveRequest();
    double turnRequest = controller.getTurnRequest();
    //The code below makes the robot act as if its front is its back, and vice versa, if you tell it to.
    //I think it's untested though
    if(controller.getReverseDirection()) {
      reverse = !reverse;
    }
    if(reverse) {
      moveRequest=-moveRequest;
      turnRequest=-turnRequest;
    }
    /**
      * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
      * The next two lines tell the drive train to respond accordingly.
      * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
    */
    Robot.drive.arcadeDrive(moveRequest, controller.getTurnRequest(), controller.getSpeedLimit());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false; //This command -- drive the robot based on input -- is NEVER finished.
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
