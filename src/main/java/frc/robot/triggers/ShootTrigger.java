package frc.robot.triggers;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class ShootTrigger extends Trigger {

    public boolean get() {
        return Robot.controlChooser.getSelected().getShoot();
    }
}
