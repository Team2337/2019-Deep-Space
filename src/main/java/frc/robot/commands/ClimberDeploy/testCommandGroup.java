package frc.robot.commands.ClimberDeploy;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.nerdyfiles.testCommand;
import frc.robot.nerdyfiles.testCommand2;

public class testCommandGroup extends CommandGroup {
    // CONSTRUCTOR
    public testCommandGroup() {
        addSequential(new testCommand(true));
        addSequential(new testCommand2(true));
    }
}