package frc.robot.commands.ClimberDeploy;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Lift;

public class climbBigBrother extends Command {

    int wranglerDriveTimer, climbTimer;
    boolean finished;

    // CONSTRUCTOR
    public climbBigBrother() {
        requires(Robot.Lift);
        requires(Robot.ClimberDeploy);
        requires(Robot.RoboWrangler);
    }

    protected void initialize() {
        // Set up the timer variable
        wranglerDriveTimer = 0;
        // Forward from case 1 to case 2 when the button is released and pressed again
        if (Robot.ClimberDeploy.climberPhase == 1) {
            Robot.ClimberDeploy.climberPhase = 2;
        }
        // Forward from case 3 to case 4 when the button is released and pressed again
        if (Robot.ClimberDeploy.climberPhase == 3) {
            Robot.ClimberDeploy.climberPhase = 4;
        }

        // This changes to true when the climber has reached its final phase
        finished = false;
    }

    @Override
    protected void execute() {

        switch (Robot.ClimberDeploy.climberPhase) {
        // Move the trolley into position to release the climber arms - The T-Rex arms
        // must miss the platform, and the RoboWrangler must hit the bumpers
        case 0:
            Robot.ClimberDeploy.deployClimber();
            Robot.Lift.setSetpoint(Robot.Lift.climbDeployPosition);
            if (Robot.Lift.atPosition(10)) {
                Robot.ClimberDeploy.climberPhase = 1;
            }
            break;
        // Release the climber arms
        case 1:
            // move servo to release T-rex arms and legs.
            // Operator needs to hit button again to continue to next case.
            
            break;

        // Lower the trolley (raising the robot)
        case 2:
            Lift.setSoftLimits(Lift.forwardLiftSoftLimit, 62);
            Robot.Lift.setSetpoint(Robot.Lift.climbPlatformLocation);
            if(climbTimer > 8) {
                Robot.Lift.setSetpoint(Robot.Lift.climbLevel3Position);
                if (Robot.Lift.atPosition(10) || Robot.Lift.getPosition() < 75) {
                    Robot.ClimberDeploy.climberPhase = 3;
                    Robot.ClimberDeploy.undeployClimber(); // Reset the servo
                }
            }
            climbTimer++;
            break;

        // Drive forward onto level 3 platform until the line sensor senses the platform
        case 3:
            Robot.RoboWrangler.drive(0.75);

            if (!Robot.ClimberDeploy.climberLineSensor.get()) {
                Robot.RoboWrangler.stop();
                Robot.ClimberDeploy.climberPhase = 4;
            }

            // TODO: Remove timer once line sensor is installed
            wranglerDriveTimer = wranglerDriveTimer + 1;
            if (wranglerDriveTimer > 80) {
                Robot.RoboWrangler.stop();
                // Robot.ClimberDeploy.climberPhase = 5;
            }
            break;

        // Raise the trolley (lifting the RoboWrangler wheels off the lvl. 1 platform)
        case 4:
            Robot.Lift.setSetpoint(Robot.Lift.climbWheelsUpPosition);
            if (Robot.Lift.atPosition(10)) {
                // Lift.setSoftLimits(455, 91);
                Robot.ClimberDeploy.climberPhase = 5;
            }
            break;

        // We have finished climbing
        case 5:

            finished = true;
            break;
        }
    }

    @Override
    protected boolean isFinished() {
        return finished;
    }

    protected void end() {
        Robot.RoboWrangler.stop();
        Robot.ClimberDeploy.undeployClimber();
        Robot.Lift.setSetpoint(Robot.Lift.getPosition());
    }

    protected void interrupted() {
        end();
    }
}
