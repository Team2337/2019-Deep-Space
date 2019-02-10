package frc.robot.commands.Auto.setpaths;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Trajectory;

/**
 * @author Bryce G.
 */
public class autoWriteTrajectoryFile extends Command{

    String fileName;
    Trajectory trajectory;

    /**
     * Makes a trajectory path file
     * @param trajectory - trajectory from Robot.java to build the file
     * @param fileName - name for the file
     */
    public autoWriteTrajectoryFile(Trajectory trajectory, String fileName) {
        this.trajectory = trajectory;
        this.fileName = fileName;
    }

    @Override
    protected void initialize() {
        // Robot.NerdyPath.makeTrajectoryFile(trajectory, fileName);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }


}