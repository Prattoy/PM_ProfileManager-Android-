package pm.PM_ProfileManager;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class PM_Service extends Service implements SensorEventListener{
	
	SensorManager mSensorManager;
	Sensor mLight, mProximity, mAccelerometer;
	float x, y, z, pr, l;
	AudioManager audio;
	int i, p=0;
	boolean accelerometer, proximity, light;
	
	@Override
	public IBinder onBind(Intent arg0)
	{
		return null;
	}
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    
	    mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
	    mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
	    mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	    
	    audio = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
	    
	    accelerometer = false;
	    proximity = false;
	    light = false;
	    
	    Toast.makeText(this, "Created", Toast.LENGTH_SHORT).show();
		Log.d("a", "Created");
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		Toast.makeText(this, "Destroyed", Toast.LENGTH_SHORT).show();
		Log.d("a", "Destroyed");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{  	
		mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
	    mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
	    mSensorManager.registerListener(this,  mAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
		
		Log.d("a", "Started");
		Toast.makeText(this,"Started", Toast.LENGTH_SHORT).show();
		
		return START_STICKY;
	}
	
	public final void onAccuracyChanged(Sensor sensor, int accuracy) {}
	
	public final void onSensorChanged(SensorEvent sensorEvent) {
		  
		  Sensor sensor = sensorEvent.sensor;
		  if (sensor.getType() == Sensor.TYPE_ACCELEROMETER)
		  {
			  x = Float.valueOf(sensorEvent.values[0]);
			  y = Float.valueOf(sensorEvent.values[1]);
			  z = Float.valueOf(sensorEvent.values[2]);
		  }
		  if (sensor.getType() == Sensor.TYPE_PROXIMITY)
		  {
			  pr = Float.valueOf(sensorEvent.values[0]);
		  }
		  if (sensor.getType() == Sensor.TYPE_LIGHT)
		  {
			  l = Float.valueOf(sensorEvent.values[0]);
		  }
	      
	      if((x>(-1.5f))&&(x<(1.5f))&&(y>(-1.50f))&&(y<(1.50f))&&(((z>(8.0f))&&(z<(10.50f)))||((z<(-8.0f))&&(z>(-10.50f)))))
	      {
	    	  accelerometer = true;
	      }
	      else
	      {
	    	  if((((x>(-2.5f))&&(x<=(-1.5f)))||((x<(2.5f))&&(x>=(1.5f))))||(((y<=(-1.50f))&&(y>(-2.50f)))||((y>=(1.50f))&&(y<(2.50f))))||(((z>(7.0f))&&(z<=(8.0f)))||((z>=(10.50f))&&(z<(11.50f)))))
		      {
		    	  
		      }
	    	  else
	    	  {
	    		  accelerometer = false;
	    	  }    	  
	      }
	      
	      if(pr==0.0f)
	      {
	    	  proximity = true;
	      }
	      else
	      {
	    	  proximity = false;
	      }
	      if(l>40.0f)
	      {
	    	  light = true;
	      }
	      else
	      {
	    	  light = false;
	      }
	      
	      if(accelerometer==false && proximity==false && light==true)
	      {
	    	  audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
	    	  i=1;
	    	  toast();
	      }
	      else if(accelerometer==false && proximity==true && light==false)
	      {
	    	  audio.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
	    	  i=2;
	    	  toast();
	      }
	      else if(accelerometer==true && proximity==false && light==true)
	      {
	    	  audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	    	  i=3;
	    	  toast();
	      }
	      else if(accelerometer==true && proximity==false && light==false)
	      {
	    	  audio.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
	    	  i=2;
	    	  toast();
	      }
	      else
	      {
	    	  audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	    	  i=3;
	    	  toast();
	      }
	
	    
	  }
	
	public void toast()
	{
		if((i==1)&&(p!=1))
		{
			Log.d("a", "Silent");
	    	Toast.makeText(this,"Silent", Toast.LENGTH_SHORT).show();
	    	
	    	p = 1;
		}
		if((i==2)&&(p!=2))
		{
			Log.d("a", "Vibrate");
	    	Toast.makeText(this,"Vibrate", Toast.LENGTH_SHORT).show();
	    	
	    	p = 2;
		}
		if((i==3)&&(p!=3))
		{
			Log.d("a", "Loud");
	    	Toast.makeText(this,"Loud", Toast.LENGTH_SHORT).show();
	    	
	    	p = 3;
		}
	}
}
