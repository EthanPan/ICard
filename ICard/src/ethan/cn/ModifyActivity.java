package ethan.cn;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class ModifyActivity extends Activity {
	private ImageView imaDelete;
	private TextView  word;
	private TextView  trans;
	private EditText  eTarget;
	private TextView  curTarTimes;
	private TextView  rightRate;
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.modify);
        
        VocabularyCard vc = new VocabularyCard();
        vc = SettingActivity.listVC.get(ScanActivity.currentPage);
        imaDelete = (ImageView)findViewById(R.id.imadelete);
        trans = (TextView)findViewById(R.id.textTransdD);
        word = (TextView)findViewById(R.id.textWordD );
        word.setText(vc.getWord());
        trans.setText(vc.getTrans());
        eTarget = (EditText)findViewById(R.id.editTextTtimes);
        curTarTimes = (TextView)findViewById(R.id.textcurTimes);
        rightRate = (TextView)findViewById(R.id.textRightRate);
        int intrightRate = 0;
        if(vc.getTimes() != 0)
         intrightRate = (Integer.valueOf(vc.getProgress()*100)/Integer.valueOf(vc.getTimes()));
        rightRate.setText(rightRate.getText()+ String.valueOf(Integer.valueOf(intrightRate))+"%");
        curTarTimes.setText(curTarTimes.getText()+String.valueOf(vc.getTarget()));
        //eTarget.setText(SettingActivity.listVC.get(ScanActivity.currentPage).getTarget());
        eTarget.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				if(!v.getText().equals(""))
				{
					SettingActivity.listVC.get(ScanActivity.currentPage).setTarget
					(Integer.valueOf(v.getText().toString()));
				}
				return false;
			}
		});
        imaDelete.setOnClickListener(new OnClickListener() {
        
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog();
			}
		});
	}
	@Override
	public void onBackPressed() {
	super.onBackPressed();
	overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out); 
	}
	 protected void dialog() 
	 {
		 new AlertDialog.Builder(this).setTitle("是否删除").setIcon(
			     android.R.drawable.ic_dialog_info).setView(
			     null).setPositiveButton("确认", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						SettingActivity.listVC.remove(ScanActivity.currentPage);
						Toast.makeText(ModifyActivity.this, "已删除", Toast.LENGTH_SHORT).show(); 
						  dialog.dismiss();
						  ModifyActivity.this.finish();
					}
				})
			     .setNegativeButton("取消", null).show();
	}
}
