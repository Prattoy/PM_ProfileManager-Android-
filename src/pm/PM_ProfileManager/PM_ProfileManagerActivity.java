package pm.PM_ProfileManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PM_ProfileManagerActivity extends Activity {
    /** Called when the activity is first created. */
	Button btn_start,btn_stop;
	Intent in;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        
        in = new Intent (this, PM_Service.class);
    }
    
    public void startClicked(View v)
    {
    	startService(in);
    }
    
    public void stopClicked(View v)
    {
    	stopService(in);
    }
}