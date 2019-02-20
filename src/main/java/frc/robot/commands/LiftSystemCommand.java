/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
<<<<<<< HEAD:src/main/java/frc/robot/commands/LiftDriveForwardBack.java
import edu.wpi.first.wpilibj.Timer;

public class LiftDriveForwardBack extends Command {
  Timer t = new Timer();
    private static final double DRIVE_DELAY  = 3.0;
  public LiftDriveForwardBack() {
    
      requires(Robot.liftSystem);
      // Use requires() here to declare subsystem dependencies
      // eg. requires(chassis);
    }
=======

public class LiftSystemCommand extends Command {
  public LiftSystemCommand() {
    requires(Robot.liftSystem);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

>>>>>>> parent of e30b30a... "Fixed" Delays for Lift System:src/main/java/frc/robot/commands/LiftSystemCommand.java
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }
  

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
<<<<<<< HEAD:src/main/java/frc/robot/commands/LiftDriveForwardBack.java
    return (t.get()>=DRIVE_DELAY);
  }
=======
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
>>>>>>> parent of e30b30a... "Fixed" Delays for Lift System:src/main/java/frc/robot/commands/LiftSystemCommand.java
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}