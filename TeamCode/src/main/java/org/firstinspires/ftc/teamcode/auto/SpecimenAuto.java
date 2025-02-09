package org.firstinspires.ftc.teamcode.auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

@Autonomous(name = "Specimen Auto", group = "Auto")
public class SpecimenAuto extends OpMode{

    double halfWidth = 7.4375;
    double halfLength = 8.125;

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    /** This is the variable where we store the state of our auto.
     * It is used by the pathUpdate method. */
    private int pathState;

    // rr tab 1
    private final Pose startPose = new Pose(48 + halfWidth, halfLength, Math.toRadians(90));  // Starting position
    private final Pose scorePose1 = new Pose(87 - halfWidth, 27 + halfLength, Math.toRadians(90)); // Scoring position

    // rr tab 5
    private final Pose backUpPose = new Pose(87 - halfWidth, 22 + halfLength, Math.toRadians(90)); // Scoring position
    private final Pose strafePose1 = new Pose(25 + halfWidth, 26.6 + halfLength, Math.toRadians(90)); // Scoring position
    private final Pose strafePose2 = new Pose(25 + halfWidth, 50 + halfLength, Math.toRadians(90)); // Scoring position
    private final Pose strafePose3 = new Pose(19 + halfWidth, 50 + halfLength, Math.toRadians(90)); // Scoring position
    private final Pose strafePose4 = new Pose(19 + halfWidth, 20, Math.toRadians(90)); // Scoring position
    private final Pose strafePose5 = new Pose(21, 37, Math.toRadians(90)); // Scoring position
    private final Pose pickUpPose1 = new Pose(21, 18 + halfLength, Math.toRadians(270)); // Scoring position

    // rr tab 3
    private final Pose pickUpPose2 = new Pose(21, 11, Math.toRadians(270)); // Scoring position

    // rr tab 4
    private final Pose scorePose2 = new Pose(87 - halfWidth, 25 + halfLength, Math.toRadians(90)); // Scoring position

    // rr tab 2
    // pickupPose1

    // rr tab 6
    // pickUpPose2

    // rr tab 7
    private final Pose scorePose3 = new Pose(87 - halfWidth, 23 + halfLength, Math.toRadians(90)); // Scoring position

    //rr tab back
    private final Pose backUpPose2 = new Pose(74 + halfWidth, 27 + halfLength, Math.toRadians(270)); // Scoring position

    private Path scorePreload, park;
    private PathChain pushSamples, pickup1, grabPickup2, grabPickup3, scorePickup1, scorePickup2, scorePickup3;

    public void buildPaths() {
        // Path for scoring preload
        scorePreload = new Path(new BezierLine(new Point(startPose), new Point(scorePose1)));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), scorePose1.getHeading());

        // Path chains for picking up and scoring samples
        pushSamples = follower.pathBuilder()
                .addPath(new BezierLine(new Point(backUpPose), new Point(strafePose1)))
                .addPath(new BezierLine(new Point(strafePose1), new Point(strafePose2)))
                .addPath(new BezierLine(new Point(strafePose2), new Point(strafePose3)))
                .addPath(new BezierLine(new Point(strafePose3), new Point(strafePose4)))
                .addPath(new BezierLine(new Point(strafePose4), new Point(strafePose5)))
                .setLinearHeadingInterpolation(backUpPose.getHeading(), strafePose5.getHeading())
                .build();

        pickup1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(strafePose5), new Point(pickUpPose1)))
                .setLinearHeadingInterpolation(backUpPose.getHeading(), strafePose5.getHeading())
                .build();

        /*scorePickup1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup1Pose), new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup1Pose.getHeading(), scorePose.getHeading())
                .build();

        grabPickup2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(pickup2Pose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup2Pose.getHeading())
                .build();

        scorePickup2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup2Pose), new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup2Pose.getHeading(), scorePose.getHeading())
                .build();

        grabPickup3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(pickup3Pose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup3Pose.getHeading())
                .build();

        scorePickup3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup3Pose), new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup3Pose.getHeading(), scorePose.getHeading())
                .build();

        // Curved path for parking
        park = new Path(new BezierCurve(new Point(scorePose), new Point(parkControlPose), new Point(parkPose)));
        park.setLinearHeadingInterpolation(scorePose.getHeading(), parkPose.getHeading()); */
    }

    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }
}
