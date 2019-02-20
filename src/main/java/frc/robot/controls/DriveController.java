/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controls;

/**
 * Add your docs here.
 */
public interface DriveController {
    Object getArmsSpeed = null;
	public double getMoveRequest();
    public double getTurnRequest();
    public double getSpeedLimit();
    public boolean getReverseDirection();
    public boolean getToggleHatchPusher();
    public boolean getToggleInwards();
    public boolean getToggleOutwards();
    public boolean getStartLift();
    public boolean getMoveArmsUp();
    public boolean getMoveArmsDown();

}
