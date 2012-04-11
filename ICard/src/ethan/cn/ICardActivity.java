package ethan.cn;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
public class ICardActivity extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.welcome);
        Start();
        
    }
    public void Start() {
        new Thread() {
                public void run() {
                        try {
                                Thread.sleep(2500);
                        } catch (InterruptedException e) {
                                e.printStackTrace();
                        }

                        Intent i = new Intent(getApplicationContext(),
                				ScanActivity.class);
                		startActivity(i); 
                		//dialog();
                		finish();
                	    
                }
        }.start();
    }

}