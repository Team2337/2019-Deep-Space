package frc.robot.commands.CargoBigBrother;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * This command eject the cargo ball from the robot as quickly as possible
 * 
 * @author Jack E.
 */
public class cargoBigBrotherEject extends Command {

    public cargoBigBrotherEject() {
        requires(Robot.CargoBigBrother);
    }

    @Override
    protected void initialize() {
        Robot.Lift.setSetpoint(Robot.Lift.cargoIntakePosition);
    }

    @Override
    protected void execute() {
        // Is the lift within 10 of its given setpoint (whatever it may be)
        if (Robot.Lift.atPosition(10)) {
            Robot.CargoScore.reverse(1);
            Robot.CargoEscalator.rollDown(1);
            Robot.CargoIntake.rollOut(1);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        // If the trigger is released mid-travel or the ball has exited
        Robot.CargoBigBrother.stop();
    }

    @Override
    protected void interrupted() {
        // If the trigger is released, stop the cargo system regardless
        this.end();
    }
}