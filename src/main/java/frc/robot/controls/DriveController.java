/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/**
   * This code declares everything that a drive controller can do. After this, we just
   * have to explain how it does those things.
   * We should talk about why most of these methods should not be declared here. That discussion
   * can happen later
*/
package frc.robot.controls;

/**
 * Add your docs here.
 */
public interface DriveController {
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
    public boolean getStopLift();
    public boolean getEngageFrontSolenoid();
    public boolean getEngageBackSolenoid();
    public boolean getRetractFrontSolenoid();
    public boolean getRetractBackSolenoid();
    public boolean getDriveLiftForward();
}
