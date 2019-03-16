/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.kauailabs.navx.frc.AHRS;
import com.kauailabs.navx.frc.AHRS.SerialDataType;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.EngageLiftSolenoidCommand;
import frc.robot.commands.HatchPusherCommand;
import frc.robot.commands.LiftCommandGroup;
import frc.robot.commands.WheelArmCommand;
import frc.robot.commands.ArmAngleCommand;
import frc.robot.controls.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.vision.Cameras;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static Drive drive = new Drive();
  public static HatchPusher hatchPusher = new HatchPusher();
  public static WheelArm wheelArm = new WheelArm();
  public static LiftSystem liftSystem = new LiftSystem();
  public static ArmAngle armAngle = new ArmAngle();
  public static DriveController controller1 = new F310Controller();
  public static DriveController controller2 = new XBoxController();
  public static OI m_oi;
  public AHRS ahrs;
  public static UsbCamera frontCam;
  public static UsbCamera backCam;

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    CameraServer.getInstance().startAutomaticCapture(0);
    CameraServer.getInstance().startAutomaticCapture(1);
    SmartDashboard.putBoolean("TeleOp Enabled", isOperatorControl());
    m_oi = new OI();

    // VideoSource frontCam = new UsbCamera("Front Camera", 0); // did not work as 0 or 2, with pixy2 on spi
    // frontCam.setResolution(320, 240);
    // //frontCam.setFPS(15);
    // CameraServer.getInstance().startAutomaticCapture(frontCam);

    // VideoSource backCam = new UsbCamera("Back Camera", 1); //this worked in inboard usb port, connected rr by usb to station
    // backCam.setResolution(640, 480);
    // backCam.setFPS(15);
    // CameraServer.getInstance().startAutomaticCapture(backCam);
    // frontCam = CameraServer.getInstance().startAutomaticCapture("Front Camera",0);
    // frontCam.setResolution(320, 240);
    //  frontCam.setFPS(15);
    // //frontCam.setBrightness(3);
     //backCam = CameraServer.getInstance().startAutomaticCapture("Back Camera",1);
     //backCam.setResolution(320, 240);
    //  backCam.setFPS(15);


     //Cameras.setup(); // Setup and Connection to Pixy2

    /*try {
      ahrs = new AHRS(SerialPort.Port.kMXP);
    } catch (RuntimeException ex) {
      DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), false);
    }*/

  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    /*if (ahrs != null) {
     SmartDashboard.putData(ahrs);
    }*/
    SmartDashboard.putBoolean("TeleOp Enabled", isOperatorControl());
    //Cameras.run();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();
    new DriveCommand().start();
    new HatchPusherCommand().start();
    new WheelArmCommand().start();
    new LiftCommandGroup().start();
    new ArmAngleCommand().start();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null)
      m_autonomousCommand.cancel();
    new DriveCommand().start();
    new HatchPusherCommand().start();
    new WheelArmCommand().start();
    new LiftCommandGroup().start();
    new ArmAngleCommand().start();
    SmartDashboard.putBoolean("TeleOp Enabled", isOperatorControl());
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
