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
        // TODO: Determine if launching backwards over the robot is possible

        // Move the trolley to the intake position
        Robot.Lift.setSetpoint(Robot.Lift.cargoIntakePosition);
    }

    @Override
    protected void execute() {
        // If the lift is in the intake position, run the cargo systems outwards
        if (Robot.Lift.atPosition(10)) {
            Robot.CargoScore.reverse(1);
            Robot.CargoEscalator.rollDown(1);
            Robot.CargoIntake.rollOut(1);
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