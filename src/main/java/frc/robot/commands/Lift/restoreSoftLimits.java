package frc.robot.commands.Lift;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.subsystems.Lift;

/**
 * Restores soft limits on the lift
 * 
 * @author Bryce G.
 */
public class restoreSoftLimits extends InstantCommand {

    /**
     * Restores soft limits on the lift
     */
    public restoreSoftLimits() {
        requires(Robot.Lift);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Lift.setSoftLimits(660, 100);
    }
}