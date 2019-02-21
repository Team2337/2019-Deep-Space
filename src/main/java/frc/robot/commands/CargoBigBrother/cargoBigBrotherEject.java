package frc.robot.commands.CargoBigBrother;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * This command eject the cargo ball from the robot as quickly as possible
 * 
 * @author Jack E.
 */
public class cargoBigBrotherEject extends Command {

    double tolerance = 10;

    /**
     * Ejects the cargo from the cargo intake insystem
     * <p><br/><strong>NOTE:</strong> the lift will not automatically move to the eject position, 
     * in order to fully eject the ball, the lift MUST be in position</p>
     */
    public cargoBigBrotherEject() {
        requires(Robot.CargoBigBrother);
        requires(Robot.CargoScore);
    }

    @Override
    protected void initialize() {
        // TODO: Determine if launching backwards over the robot is possible
        Robot.CargoEscalator.rollDown(1);
        Robot.CargoIntake.rollOut(1);
    }

    @Override
    protected void execute() {
        if(!Robot.Lift.atCargoIntakePosition(tolerance) && Robot.CargoBigBrother.cargoTrolleySensor.get()) {
            Robot.CargoScore.rollReverse(1);
        } else if(!Robot.Lift.atCargoIntakePosition(tolerance) && !Robot.CargoBigBrother.cargoTrolleySensor.get()) {
            Robot.CargoScore.rollReverse(0);
        } else {
            Robot.CargoScore.rollReverse(1);
        }
    }

    // This command is not meant to stop until the trigger is released
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Stop all the cargo systems
    @Override
    protected void end() {
        Robot.CargoBigBrother.stop();
    }

    @Override
    protected void interrupted() {
        this.end();
    }
}