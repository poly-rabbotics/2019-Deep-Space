/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/**
* ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
* Literally all this file does is constantly ask if the robot is being told to move
* arms up or down, and if yes, tell the armAngle subsystem to act accordingly.
* Nothing complicated.
* ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
*/

package frc.robot.commands;//tells us what package this file is in. This lets 
                           //this file access other files in the same package.
                           //Note that the package is the same as the file path,
                           //except with . instead of /

import edu.wpi.first.wpilibj.command.Command;//Since ArmAngleCommand is a command,
                                             //we have to get the information from FIRST
                                             //code that says what a Command is before we
                                             //start extending that with details
                                             //about this particular type of Command.
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; //Looks like this isn't used
import frc.robot.Robot; //The Robot is composed of all the subsystems, and to access the 
                        //subsystems, this file has to access the Robot.
import frc.robot.controls.DriveController; //Each controller we're working with here "is a" 
                                        //DriveController, so to use them we have to know 
                                       //what a DriveController is. (we have to import it)

public class ArmAngleCommand extends Command { //ArmAngleCommand "is a" Command. Command is
                                               //what FIRST gave us; ArmAngleCommand is our
                                               //modified version of what they gave us.
  public ArmAngleCommand() {
    requires(Robot.armAngle); //This prevents other commands from using the part of the robot
                              //that this command requires. So if some other command were doing
                              //something with this subsystem, that other command would have to 
                              //stop because now an ArmAngleCommand needs it.
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    DriveController controller1 = Robot.controller1;
    DriveController controller2 = Robot.controller2;
    if(controller1.getMoveArmsUp()||controller2.getMoveArmsUp()){ //Check to see if the drive
                                                         //controller is telling the robot to move arms up
        Robot.armAngle.spinUpwards(); //If yes, tell the part of the robot that moves the arms to move arms up.
    }
    
   else if(controller1.getMoveArmsDown()||controller2.getMoveArmsDown()){ //same as with up ^
    Robot.armAngle.spinDownwards();
   }
   else Robot.armAngle.stopSpin(); //Stop if not being told to move down OR up
   
   /* 
   * The next four lines are for debugging: reporting what the limit switches say and what the armAngle subsystem
   * says that the limit switches say.
   */
   SmartDashboard.putBoolean("Upper Switch", Robot.armAngle.returnUpper());
   SmartDashboard.putBoolean("Lower Switch", Robot.armAngle.returnLower());
   System.out.println(Robot.armAngle.returnUpper());
   System.out.println(Robot.armAngle.returnLower());
    

  }
/**
* ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
* The command stops executing every 20 milliseconds when it is finished. Below, we say
* it is never finished, so the command just goes until the robot gets disabled.
* ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
*/
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false; //never finished.
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
