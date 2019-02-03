package frc.robot.commands.Auto.setpaths;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import jaci.pathfinder.Trajectory;

public class autoWriteTrajectoryFile extends Command{

    String fileName;
    Trajectory trajectory;

    public autoWriteTrajectoryFile(Trajectory trajectory, String fileName) {
        this.trajectory = trajectory;
        this.fileName = fileName;
    }

    @Override
    protected void initialize() {
        Robot.NerdyPath.makeTrajectoryFile(trajectory, fileName);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }


}