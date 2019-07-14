
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * This code is the center of everything our robot does because it is the interface
   * between the system built by FRC and the code that we wrote. It makes everything else go.
   * FRC gave us an object that performs actions either once on startup or periodically (many
   * times per second), but we just have to fill in the blanks and say what those actions are.
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
*/


package frc.robot; //Say what package this file belongs in, thereby defining what
                   //other files it can access. Explained in ArmAngle.java.

//both of the imports below are for the accelerometer, for detecting acceleration
//(and the force of gravity). I don't think we ended up using it.
import com.kauailabs.navx.frc.AHRS;
import com.kauailabs.navx.frc.AHRS.SerialDataType;

//The "mysterious other code" from FRC can only work with a few types
//(actually classes) of things (objects). TimedRobot is one of those things that
//works with the code FRC uses. To create something that FRC can work with, we
//have to get something that literally "is a" TimedRobot, and then just extend it
//with our own code. As a result, what we give back to FRC literally IS the thing
//that they gave us and told us they could work with, just altered a bit.
import edu.wpi.first.wpilibj.TimedRobot;

//A command is another something that does something. A Command tells parts of the 
//robot (or subsystems) to do things.
import edu.wpi.first.wpilibj.command.Command;
//The scheduler makes commands keep on happening until they are finished.
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.cscore.UsbCamera; //lets us get input from a camera.
//import edu.wpi.cscore.VideoSource; //Commented out b/c we don't use this.
import edu.wpi.first.cameraserver.CameraServer; //lets us send our camera input 
                                                //back to the driver station.
//import edu.wpi.first.wpilibj.DriverStation; //Commented out b/c we don't use this.
//import edu.wpi.first.wpilibj.SerialPort;    //commented out b/c we don't use this.
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser; //This is used to present 
                                                //choices for us to select in the 
                                                //SmartDashboard (part of the Driver Station?
                                                //Ask Mr. Gever or someone)
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; //for user interface in SmartDashboard

/**
* ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
* The next 7 lines are just importing our own commands so that we will be able to tell the
* robot to do things.
* ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
*/
import frc.robot.commands.DriveCommand;
//import frc.robot.commands.EngageLiftSolenoidCommand; //deleted file, please ignore
import frc.robot.commands.HatchPusherCommand;
//import frc.robot.commands.LiftCommandGroup;  //deleted file, please ignore
import frc.robot.commands.ManualLiftCommand;
import frc.robot.commands.WheelArmCommand;
import frc.robot.commands.ArmAngleCommand;

import frc.robot.controls.*; //The asterisk means we import everything in the controls package.
import frc.robot.subsystems.*; //The asterisk means we import everything in the subsystems package.
//import frc.robot.subsystems.vision.Cameras;//Not used so commented out

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  //Now we are just saying what all the kinds of parts (what classes of parts) 
  //the robot has, but we still haven't said exactly what those parts are (we
  //haven't instantiated them as objects)
  public static Drive drive = new Drive();
  public static HatchPusher hatchPusher = new HatchPusher();
  public static WheelArm wheelArm = new WheelArm();
  public static LiftSystem liftSystem = new LiftSystem();
  public static ArmAngle armAngle = new ArmAngle();
  //Now we say what kinds of controllers we're using.
  public static DriveController controller1 = new F310Controller();
  public static DriveController controller2 = new XBoxController();
  public static OI m_oi; //for output and input. The m_ says it's a member variable, but almost 
                         //all of these are member variables so it's kind of meaningless.
                         //To keep things simple, we didn't use this, but in the future we should.
  public AHRS ahrs;      //Attitude and Heading Reference System. I thinks it's just an accelerometer
                         //(for linear acceleration and gravity) and gyroscope (for rotational acceleration, or turning)
  //Say what kinds of cameras we're using (they're UsbCameras).
  public static UsbCamera frontCam;
  public static UsbCamera backCam;

  Command m_autonomousCommand; //Used to store the autonomous program we choose in SmartDashboard.
                               //We didn't really use this.
  SendableChooser<Command> m_chooser = new SendableChooser<>(); //SendableChooser is actually a template.
                                                                //This is a SendableChooser for choosing commands. 

  /**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   * 
   * The first thing we are doing when the robot starts is start sending video to the driver
   * station. That is what we set up in robotInit.
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   */
  @Override //annotation that says we are overriding a method
  public void robotInit() {
    //Get a camera server to start sending images from the front camera to the dashboard.
    frontCam = CameraServer.getInstance().startAutomaticCapture("Front Camera", 0); //Apparently the device # of the front camera is 0. Why? ask Mr. Powers or Mr. Gever.
    //frontCam.setResolution(320, 240); 
    frontCam.setFPS(15); //Set frames per second.
    // frontCam.setBrightness(3);
    backCam = CameraServer.getInstance().startAutomaticCapture("Back Camera", 1); //same as above ^
    backCam.setResolution(320, 240);//We can't send information too fast, so low resolution can address that problem
    backCam.setFPS(15);
    m_oi = new OI(); //Declare our output/input. To keep things simple, we didn't use this, but in the future we should.
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
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * Deep Space is unusual because there wasn't really a true autonomous, so our
   * autonomousInit is the same as our teleopInit.
   * This is where we first command the various parts of our robot to start checking for and 
   * responding to input from our driver.
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   */
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
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * Throughout autonomous, we have to make the Scheduler keep all the commands going, so that
   * the parts of the robot keep responding to controller input.
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   */
  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * This is where we first command the various parts of our robot to start checking for and 
   * responding to input from our driver.
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   */
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
  //  new LiftCommandGroup().start();
    new ManualLiftCommand().start();
    new ArmAngleCommand().start();
    //SmartDashboard.putBoolean("TeleOp Enabled", isOperatorControl());
  }

  /**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * Throughout autonomous, we have to make the Scheduler keep all the commands going, so that
   * the parts of the robot keep responding to controller input.
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   */
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
