package ethan.cn;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DataShowActivity extends Activity{
	private ImageView best;
	private ImageView goon;
	private TextView count;
	private TextView scancount;
	private TextView finishcount;
	private ProgressBar progressBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.data);
        best = (ImageView)findViewById(R.id.best);
        goon = (ImageView)findViewById(R.id.goon);
        count = (TextView)findViewById(R.id.TextCount);
        scancount = (TextView)findViewById(R.id.TextScancount);
        finishcount = (TextView)findViewById(R.id.TextfinishCount);
        progressBar = (ProgressBar)findViewById(R.id.progressBartarget);
       
        updata();
	}
	
	@Override
	public void onBackPressed() {
	super.onBackPressed();
	overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out); 
	}
	public void updata()
	{
		int wordcounter = SettingActivity.listVC.size();
		int reciteCounter = 0;
		int scancounter = 0;
		int progress = 0;
		count.setText(String.valueOf(wordcounter));
		for(VocabularyCard v:SettingActivity.listVC)
		{
			scancounter += v.getTimes();
			if(v.getProgress() >= v.getTarget())
				reciteCounter++;
			
		}
		if(wordcounter != 0)
			progress = Integer.valueOf((int) (Double.valueOf(reciteCounter)/Double.valueOf(wordcounter)*100));
		progressBar.setProgress(progress);
		scancount.setText(String.valueOf(scancounter));
		finishcount.setText(String.valueOf(reciteCounter));
		count.setText(String.valueOf(wordcounter));
		if(progress >=60)
		{
			best.setVisibility(0x00000000);
			goon.setVisibility(0x00000004);
		}
		else 
		{
			best.setVisibility(0x00000004);
			goon.setVisibility(0x00000000);
		}
	}
}
