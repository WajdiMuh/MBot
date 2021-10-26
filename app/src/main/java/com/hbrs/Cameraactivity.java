package com.hbrs;

import android.os.Bundle;
import android.util.Log;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import androidx.appcompat.app.AppCompatActivity;

import com.hbrs.R;

//*************************************************************************************************
public class Cameraactivity extends AppCompatActivity
        implements CameraBridgeViewBase.CvCameraViewListener2
{
    //-----------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity);

        // Initialize OpenCV
        JavaCameraView mCamView = (JavaCameraView)findViewById( R.id.camera_view);
        mCamView.setCameraIndex(mCamView.CAMERA_ID_ANY);
        mCamView.setCvCameraViewListener(this);
        mCamView.enableView();

        OpenCVLoader.initDebug();
    }

    //-----------------------------------------------------------------
    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame)
    {
        // Get frame
        Mat mImg = inputFrame.rgba();
        Mat hsv = new Mat((int) mImg.size().height, (int) mImg.size().width, CvType.CV_8UC3);

        Imgproc.cvtColor(mImg, hsv, Imgproc.COLOR_RGB2HSV, 3);

        Mat mask1 = new Mat(hsv.rows(), hsv.cols(), CvType.CV_8U, new Scalar(0));
        Mat mask2 = new Mat(hsv.rows(), hsv.cols(), CvType.CV_8U, new Scalar(0));
        Mat combinedmask = new Mat(hsv.rows(), hsv.cols(), CvType.CV_8U, new Scalar(0));

        Scalar lowerlowerredhue = new Scalar(0, 100, 100);
        Scalar upperlowerredhue = new Scalar(10, 255, 255);

        Scalar lowerupperredhue = new Scalar(170, 100, 100);
        Scalar upperupperredhue = new Scalar(180, 255, 255);

        Core.inRange(hsv, lowerlowerredhue, upperlowerredhue, mask1);
        Core.inRange(hsv, lowerupperredhue, upperupperredhue, mask2);

        Core.bitwise_not( mask1, mask1);
        Core.bitwise_not( mask2, mask2);
        Core.bitwise_and( mask1, mask2,combinedmask);
        hsv.setTo(new Scalar(0,0,0), combinedmask);

        Imgproc.cvtColor(hsv, mImg, Imgproc.COLOR_HSV2RGB, 4);
        return  mImg;
    }

    //-----------------------------------------------------------------
    @Override
    public void onCameraViewStarted(int width, int height)
    {
    }

    //-----------------------------------------------------------------
    @Override
    public void onCameraViewStopped()
    {
    }
}