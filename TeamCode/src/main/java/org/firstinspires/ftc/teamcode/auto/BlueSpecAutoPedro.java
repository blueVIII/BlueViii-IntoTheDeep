package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.pedro.PedroConstants;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Timer;
import org.firstinspires.ftc.teamcode.NextFTCLib.Command;
import org.firstinspires.ftc.teamcode.NextFTCLib.SequentialGroup;
import org.firstinspires.ftc.teamcode.NextFTCLib.ParallelGroup;
import org.firstinspires.ftc.teamcode.NextFTCLib.Delay;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.RobotServos;

@Autonomous(name = "PedroNextFTC_Auto", group = "Autonomous")
public class PedroNextFTCAuto extends NextFTCOpMode {

    private Follower follower;
    private Timer pathTimer;
    private int pathState;

    // Define starting position
    private final Pose initialPose = new Pose(16.5625, 63.875, Math.toRadians(90));
    private final Pose midPose = new Pose(-7, -45.5, Math.toRadians(90));
    private final Pose highJunctionPose = new Pose(47, -45.5, Math.toRadians(90));
    private final Pose scorePose = new Pose(51, -54, Math.toRadians(245));
    private final Pose finalPose = new Pose(42, -50, Math.toRadians(270));

    // Define paths
    private Path drive1, drive2, drive3, drive4;

    public PedroNextFTCAuto() {
        super(Claw.INSTANCE, Lift.INSTANCE);
    }

    public void buildPaths() {
        // Move from start to mid-point
        drive1 = new Path(new BezierLine(new Point(initialPose), new Point(midPose)));
        drive1.setLinearHeadingInterpolation(initialPose.getHeading(), midPose.getHeading());

        // Move to high junction
        drive2 = new Path(new BezierLine(new Point(midPose), new Point(highJunctionPose)));
        drive2.setLinearHeadingInterpolation(midPose.getHeading(), highJunctionPose.getHeading());

        // Move to scoring position
        drive3 = new Path(new BezierLine(new Point(highJunctionPose), new Point(scorePose)));
        drive3.setLinearHeadingInterpolation(highJunctionPose.getHeading(), scorePose.getHeading());

        // Move to final pose
        drive4 = new Path(new BezierLine(new Point(scorePose), new Point(finalPose)));
        drive4.setLinearHeadingInterpolation(scorePose.getHeading(), finalPose.getHeading());
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(drive1);
                setPathState(1);
                break;
            case 1:
                if (!follower.isBusy()) {
                    firstRoutine().invoke(); // Execute first lift and claw sequence
                    follower.followPath(drive2);
                    setPathState(2);
                }
                break;
            case 2:
                if (!follower.isBusy()) {
                    secondRoutine().invoke(); // Execute second lift and claw sequence
                    follower.followPath(drive3);
                    setPathState(3);
                }
                break;
            case 3:
                if (!follower.isBusy()) {
                    finalRoutine().invoke(); // Execute final routine
                    follower.followPath(drive4);
                    setPathState(4);
                }
                break;
            case 4:
                if (!follower.isBusy()) {
                    setPathState(-1); // Stop execution
                }
                break;
        }
    }

    public void setPathState(int newState) {
        pathState = newState;
        pathTimer.resetTimer();
    }

    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();
    }

    @Override
    public void init() {
        pathTimer = new Timer();
        follower = new Follower(hardwareMap);
        follower.setStartingPose(initialPose);
        buildPaths();
    }

    @Override
    public void start() {
        setPathState(0);
    }

    public Command firstRoutine() {
        return new SequentialGroup(
                Lift.INSTANCE.toHigh(),
                new ParallelGroup(
                        Lift.INSTANCE.toMiddle(),
                        Claw.INSTANCE.close()
                ),
                new Delay(0.5),
                new ParallelGroup(
                        Claw.INSTANCE.open(),
                        Lift.INSTANCE.toLow()
                )
        );
    }

    public Command secondRoutine() {
        return new SequentialGroup(
                Lift.INSTANCE.toHigh(),
                new ParallelGroup(
                        Lift.INSTANCE.toMiddle(),
                        Claw.INSTANCE.close()
                ),
                new Delay(0.5),
                new ParallelGroup(
                        Claw.INSTANCE.open(),
                        Lift.INSTANCE.toLow()
                )
        );
    }

    public Command finalRoutine() {
        return new SequentialGroup(

                Lift.INSTANCE.toLow(),
                Claw.INSTANCE.open(),
                new Delay(0.5)
        );
    }



    @Override
    public void onStartButtonPressed() {
        firstRoutine().invoke();
    }
}


