package frc.robot.commands.ClimberDeploy;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.RoboWrangler.driveWranglerBySpeed;

/**
 * 1. Move the trolley to the position where it can deploy the climber
 */
public class CGClimb extends CommandGroup {
    // CONSTRUCTOR
    public CGClimb() {
        // if (Robot.ClimberDeploy.readyToClimb) {
        switch (Robot.ClimberDeploy.climberPhase) {
        case 0:
            System.out.println("0000000000000000000000000000000");
            addSequential(new goToPositionClimber(Robot.Lift.climbDeployPosition));
        case 1:
            System.out.println("111111111111111111111111111");
            addSequential(new deployClimber(4));
        case 2:
            System.out.println("2222222222222222222222222222222222");
            addSequential(new goToPositionClimber(Robot.Lift.climbLevel3Position));
        case 3:
            System.out.println("33333333333333333333333333333333333333");
            addSequential(new driveWranglerBySpeed(1, 5));
        case 4:
            System.out.println("444444444444444444444444444444444");
            addSequential(new goToPositionClimber(Robot.Lift.climbWheelsUpPosition));
        }
        // } else {

        // }
    }
}