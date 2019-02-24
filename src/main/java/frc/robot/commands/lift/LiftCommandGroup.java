/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.lift;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class LiftCommandGroup extends CommandGroup {

  public LiftCommandGroup() {
    addSequential(new EngageLiftSolenoidCommand());
    addSequential(new LiftDriveForwardFront());
    addSequential(new LiftRetractFront());
    addSequential(new LiftDriveForwardBack());
    // addSequential(new LiftSystemEnd());
  }
}
