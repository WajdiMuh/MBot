//*******************************************************************
/*!
\file   MainActivity.java
\author Thomas Breuer
\date   07.09.2021
\brief
*/

//*******************************************************************
package com.hbrs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

//*************************************************************************************************
public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MBot";

    private MBot mbot;

    private final static int REQUEST_BLUETOOTH_ENABLE = 1;
    private final static int REQUEST_BLUETOOTH_GET_ADDR = 2;

    //-----------------------------------------------------------------
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        switch(requestCode)
        {
            case REQUEST_BLUETOOTH_ENABLE:
                break;
            case REQUEST_BLUETOOTH_GET_ADDR:
                if (resultCode == Activity.RESULT_OK)
                {
                    mbot.connect( this, BT_DeviceListActivity.getAddr( data ) );
                }
                break;
        }
    }

    //-----------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mbot = new MBot();
    }

    //-----------------------------------------------------------------
    public void onBtnConnect(View view)
    {
        Log.i(TAG, "Connect...");

        BT_DeviceListActivity.connect( this, REQUEST_BLUETOOTH_ENABLE, REQUEST_BLUETOOTH_GET_ADDR );
    }

    //*********************************************************************************************
    // Test
    //-----------------------------------------------------------------
    int ledId = 12;

    //-----------------------------------------------------------------
    public void onBtnLED(View view) 
    {
	   mbot.setLight( 0, 0, 0, 0 );
       mbot.setLight( ledId, 20, 0, 0 );
       ledId = (ledId%12)+1;
    }
    //*********************************************************************************************
}
