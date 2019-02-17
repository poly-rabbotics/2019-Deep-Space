package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.RobotMap;
import org.usfirst.frc.team4999.utils.PDPWrapper;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Grab extends Command {
	

	private static final double HUNT_SPEED = 0.8;
	
	private static final double CUTOFF_CURRENT = 5;
	private static final int CUTOFF_TIME = 500;
	
	private PDPWrapper pdp;

    public Grab() {
    	requires(Robot.arms);
    	pdp = new PDPWrapper();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.arms.setIntakeSpeed(HUNT_SPEED);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return pdp.checkOvercurrent(new int[] {RobotMap.ARM_WHEEL_1_PDP, RobotMap.ARM_WHEEL_2_PDP}, CUTOFF_CURRENT, CUTOFF_TIME);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
