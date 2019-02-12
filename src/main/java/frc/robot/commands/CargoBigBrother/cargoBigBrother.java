package frc.robot.commands.CargoBigBrother;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * This command will run the escalator upwards to move cargo towards the scoring
 * mechanism
 * 
 * @author Jack E.
 */
public class cargoBigBrother extends Command {

    double intakeSpeed = 1;
    double escalatorSpeed = 1;
    double scoreSpeed = 1;

    boolean startWithBall = false;

    public cargoBigBrother() {
        requires(Robot.CargoBigBrother);
    }

    // Set the speed of the cargo escalator motors
    @Override
    protected void initialize() {
        // Moves the lift to the position to receive the cargo ball
        Robot.Lift.setSetpoint(150);
    }

    @Override
    protected void execute() {

    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.CargoEscalator.stop();
    }

    @Override
    protected void interrupted() {
        this.end();
    }
}