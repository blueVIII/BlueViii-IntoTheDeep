package org.firstinspires.ftc.teamcode.auto;

import androidx.annotation.NonNull;

// RR-specific imports
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous(name = "BlueSpecAuto", group = "Autonomous")
public class BlueSpecAuto extends LinearOpMode {

    public class Lift {
        private DcMotorEx liftMotor1, liftMotor2;

        public Lift(HardwareMap hardwareMap) {
            liftMotor1 = hardwareMap.get(DcMotorEx.class, "liftMotor1");
            liftMotor2 = hardwareMap.get(DcMotorEx.class, "liftMotor2");

            liftMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            liftMotor1.setDirection(DcMotorSimple.Direction.FORWARD);
            liftMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            liftMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            liftMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            liftMotor2.setDirection(DcMotorSimple.Direction.REVERSE);
            liftMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            liftMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            int liftMotor1StartPosition;
            int liftMotor1EndPosition;
        }

        private void moveLiftToPosition(int targetPosition, double power) {
            liftMotor1.setTargetPosition(targetPosition);
            liftMotor2.setTargetPosition(targetPosition);

            liftMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            liftMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            liftMotor1.setPower(power);
            liftMotor2.setPower(power);

            while (liftMotor1.isBusy() && liftMotor2.isBusy() && opModeIsActive()) {
                telemetry.addData("LiftMotor1 Position", liftMotor1.getCurrentPosition());
                telemetry.addData("LiftMotor2 Position", liftMotor2.getCurrentPosition());
                telemetry.update();
            }

            liftMotor1.setPower(0);
            liftMotor2.setPower(0);
            liftMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            liftMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        public Action moveLiftAction(int targetPosition, double power) {
            return new Action() {
                private boolean initialized = false;

                @Override
                public boolean run(@NonNull TelemetryPacket packet) {
                    if (!initialized) {
                        moveLiftToPosition(targetPosition, power);
                        initialized = true;
                    }
                    return false;
                }
            };
        }
    }

    public class SlideIntake {
        private DcMotorEx slideMotor;

        public SlideIntake(HardwareMap hardwareMap) {
            slideMotor = hardwareMap.get(DcMotorEx.class, "slideIntake");
            slideMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        private void moveToPosition(int targetPosition, double power) {
            slideMotor.setTargetPosition(targetPosition);
            slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            slideMotor.setPower(power);

            while (slideMotor.isBusy() && opModeIsActive()) {
                telemetry.addData("SlideIntake Position", slideMotor.getCurrentPosition());
                telemetry.update();
            }

            slideMotor.setPower(0);
            slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }


        public Action slideMoveAction(int targetPosition, double power) {
            return new Action() {
                private boolean initialized = false;

                @Override
                public boolean run(@NonNull TelemetryPacket packet) {
                    if (!initialized) {
                        moveToPosition(targetPosition, power);
                        initialized = true;
                    }
                    return false;
                }
            };
        }
    }

    public class RobotServos {
        private Servo rotateArm;
        private Servo bottomClaw;
        private Servo rotateBClaw;
        private Servo flipTClaw;
        private Servo rotateTClaw;
        private Servo topClaw;

        public RobotServos(HardwareMap hardwareMap) {
            rotateArm    = hardwareMap.get(Servo.class, "rotateArm");
            bottomClaw   = hardwareMap.get(Servo.class, "bottomClaw");
            rotateBClaw  = hardwareMap.get(Servo.class, "rotateBClaw");
            flipTClaw    = hardwareMap.get(Servo.class, "flipTClaw");
            rotateTClaw  = hardwareMap.get(Servo.class, "rotateTClaw");
            topClaw      = hardwareMap.get(Servo.class, "topClaw");
        }


        public Action setServoPosition(Servo servo, double targetPos) {
            return new Action() {
                private boolean initialized = false;

                @Override
                public boolean run(@NonNull TelemetryPacket packet) {
                    if (!initialized) {
                        servo.setPosition(targetPos);
                        initialized = true;
                    }
                    return false;
                }
            };
        }

        public Action moveBottomClaw(double position) {
            return setServoPosition(bottomClaw, position);
        }
        public Action moveTopClaw(double position) {
            return setServoPosition(topClaw, position);
        }
        public Action moveRotateArm(double position) {
            return setServoPosition(rotateArm, position);
        }
        public Action moveRotateBClaw(double position) {
            return setServoPosition(rotateBClaw, position);
        }
        public Action moveFlipTClaw(double position) {
            return setServoPosition(flipTClaw, position);
        }
        public Action moveRotateTClaw(double position) {
            return setServoPosition(rotateTClaw, position);
        }
    }


    @Override
    public void runOpMode() {

        Pose2d initialPose = new Pose2d(0, 0, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        Lift lift            = new Lift(hardwareMap);
        SlideIntake slide    = new SlideIntake(hardwareMap);
        RobotServos servos   = new RobotServos(hardwareMap);

        double halfWidth = 7.4375;
        double halfLength = 8.125;

        int liftMotor1StartPosition = lift.liftMotor1.getCurrentPosition();
        int liftMotor1EndPosition = liftMotor1StartPosition + 4650;

        Action liftToHighJunction = lift.moveLiftAction(liftMotor1EndPosition, 0.8);
        Action liftToLowPosition  = lift.moveLiftAction( 100, 0.6);
        Action liftDown = lift.moveLiftAction(liftMotor1StartPosition, 0.8);

        Action slideIn  = slide.slideMoveAction( 109,  0.7);
        Action slideOut = slide.slideMoveAction(-1250, 0.7);

        Action openBottomClaw  = servos.moveBottomClaw(0.0);
        Action closeBottomClaw = servos.moveBottomClaw(1.0);
        Action rotateArmOut    = servos.moveRotateArm(1.0);

        /*TrajectoryActionBuilder drive1 = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-12 + halfWidth, -39.5 + halfLength))
                .waitSeconds(1)
                .lineToY(-42.5 + halfLength, null, new ProfileAccelConstraint(-80, 80))
                .strafeTo(new Vector2d(47 - halfWidth, -45.5 + halfLength), null, new ProfileAccelConstraint(-80, 80))
                .setTangent(Math.toRadians(90))
                .lineToY(-22 + halfLength, null, new ProfileAccelConstraint(-80, 80))
                .strafeTo(new Vector2d(53, -22 + halfLength),null, new ProfileAccelConstraint(-80, 80))
                .setTangent(Math.toRadians(90))
                .lineToY(-52, null, new ProfileAccelConstraint(-80, 80))
                .lineToY(-22 + halfLength, null, new ProfileAccelConstraint(-80, 80))
                .strafeTo(new Vector2d(63, -22 + halfLength), null, new ProfileAccelConstraint(-80, 80))
                .setTangent(Math.toRadians(90))
                .lineToY(-52)
                .lineToY(-22 + halfLength)
                .waitSeconds(0.01)
                .strafeTo(new Vector2d(71.5, -22 + halfLength))
                .setTangent(Math.toRadians(90))
                .lineToY(-52)
                .strafeTo(new Vector2d(64,-42), null, new ProfileAccelConstraint(-80, 80))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(52, -76 + halfLength, Math.toRadians(250)),Math.toRadians(250), null, new ProfileAccelConstraint(-80, 80))
                .waitSeconds(1)
                .strafeTo(new Vector2d(42, -65 + halfLength), null, new ProfileAccelConstraint(-80, 80))
                .splineToLinearHeading(new Pose2d(-6, -39.5 + halfLength, Math.toRadians(90)),Math.toRadians(90), null, new ProfileAccelConstraint(-80, 80))
                .waitSeconds(1)
                .strafeTo(new Vector2d(5, -45 + halfLength), null, new ProfileAccelConstraint(-80, 80))
                .splineToLinearHeading(new Pose2d(45, -76 + halfLength, Math.toRadians(250)),Math.toRadians(250), null, new ProfileAccelConstraint(-80, 80))
                .waitSeconds(1)
                .strafeTo(new Vector2d(35, -65 + halfLength), null, new ProfileAccelConstraint(-80, 80))
                .splineToLinearHeading(new Pose2d(-6, -39.5 + halfLength, Math.toRadians(90)),Math.toRadians(90), null, new ProfileAccelConstraint(-80, 80));
                */

        /*TrajectoryActionBuilder drive1 = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-12, -39.5))
                .waitSeconds(1);

        TrajectoryActionBuilder drive2 = drive.actionBuilder(initialPose)
                .lineToY(-42.5, null, new ProfileAccelConstraint(-80, 80))
                .strafeTo(new Vector2d(47, -45.5), null, new ProfileAccelConstraint(-80, 80))
                .setTangent(Math.toRadians(90))
                .lineToY(-22, null, new ProfileAccelConstraint(-80, 80))
                .strafeTo(new Vector2d(53, -22), null, new ProfileAccelConstraint(-80, 80))
                .setTangent(Math.toRadians(90))
                .lineToY(-52, null, new ProfileAccelConstraint(-80, 80))
                .lineToY(-22, null, new ProfileAccelConstraint(-80, 80))
                .strafeTo(new Vector2d(63, -22), null, new ProfileAccelConstraint(-80, 80))
                .setTangent(Math.toRadians(90))
                .lineToY(-52)
                .strafeTo(new Vector2d(64, -42), null, new ProfileAccelConstraint(-80, 80))
                .setTangent(Math.toRadians(90));
        TrajectoryActionBuilder drive3 = drive.actionBuilder(initialPose)
                .splineToLinearHeading(new Pose2d(52, -76, Math.toRadians(250)), Math.toRadians(250),
                        null, new ProfileAccelConstraint(-80, 80))
                .waitSeconds(1);
        TrajectoryActionBuilder drive4 = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(42, -65), null, new ProfileAccelConstraint(-80, 80))
                .splineToLinearHeading(new Pose2d(-6, -39.5, Math.toRadians(90)), Math.toRadians(90),
                        null, new ProfileAccelConstraint(-80, 80))
                .waitSeconds(1);
        TrajectoryActionBuilder drive5 = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(5, -45), null, new ProfileAccelConstraint(-80, 80))
                .splineToLinearHeading(new Pose2d(45, -76, Math.toRadians(250)), Math.toRadians(250),
                        null, new ProfileAccelConstraint(-80, 80))
                .waitSeconds(1);
        TrajectoryActionBuilder drive6 = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(35, -65), null, new ProfileAccelConstraint(-80, 80))
                .splineToLinearHeading(new Pose2d(-6, -39.5, Math.toRadians(90)), Math.toRadians(90),
                        null, new ProfileAccelConstraint(-80, 80));

        Action trajectoryAction1 = drive1.build();
        Action trajectoryAction2 = drive2.build();
        Action trajectoryAction3 = drive3.build();
        Action trajectoryAction4 = drive4.build();
        Action trajectoryAction5 = drive5.build();
        Action trajectoryAction6 = drive6.build();

         */



        waitForStart();
        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction (

                        liftToHighJunction,
                        liftDown
                        //slideOut,
                        //closeBottomClaw,
                        //trajectoryAction1
                )
        );

        /*Actions.runBlocking(
                new SequentialAction(
                        slideIn,
                        openBottomClaw,
                        liftToLowPosition
                )
        ); */

        telemetry.addData("Autonomous", "Complete");
        telemetry.update();
    }
}