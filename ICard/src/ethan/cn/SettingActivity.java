package ethan.cn;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Xml;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.content.Context;

public class SettingActivity extends Activity implements OnItemClickListener{
	private ListView listMenu; 
	static public int defaultTarget = 5;
	private Context context;
	static public int transShow = 0x00000004;
	private ToggleButton tb;
	private TextView dText;
	private EditText editTarget;
	private TextView curTarTimes;
	private ProgressBar loadingbar;
	static public List<VocabularyCard> listVC = new ArrayList<VocabularyCard>();
	static public List<VocabularyCard> addListVC = new ArrayList<VocabularyCard>();
	public SettingActivity(){};
	@Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.setting);
        listMenu = (ListView)findViewById(R.id.listMain);
		String[] menuStr  = {"��������","���뵥�ʱ�","�������ʱ�"};  
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.liststyle,
				menuStr);
		listMenu.setAdapter(adapter);
		listMenu.setOnItemClickListener(this);
		context=this.getApplicationContext();
		tb = (ToggleButton)findViewById(R.id.tb1);
		dText = (TextView)findViewById(R.id.dTextView);
		curTarTimes = (TextView)findViewById(R.id.textScurTimes);
		editTarget = (EditText)findViewById(R.id.editTarget);
		loadingbar = (ProgressBar)findViewById(R.id.p1);
		if(listVC.size()!=0)
		{			
			curTarTimes.setText(curTarTimes.getText()+ String.valueOf(defaultTarget));		
		}
		
		editTarget.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				if(!v.getText().equals("")&&listVC.size()!= 0)
				{
					defaultTarget = Integer.valueOf(String.valueOf(v.getText()));
					for(VocabularyCard vc:listVC)
					{
						vc.setTarget(defaultTarget);
					}
				}
				return false;
			}
		});
		if(transShow == 0x00000000)
			tb.setChecked(true);
		else
			tb.setChecked(false);
		tb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(transShow == 0x00000000)
					transShow = 0x00000004;
				else
					transShow = 0x00000000;
				return ;
			}
		});
	}
	@Override
	public void onBackPressed() {
	super.onBackPressed();
		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out); 
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		if(arg2==0)
		{
			
		}
		else if(arg2 == 1)//���뵥�ʱ�
		{
			final EditText et = new EditText(this);		  
            Toast.makeText(SettingActivity.this, "���ļ�������ʱ����ּ������������ĵȴ�", Toast.LENGTH_SHORT).show(); 

			new AlertDialog.Builder(this).setTitle("�������ļ�����������׺��").setIcon(
				     android.R.drawable.ic_dialog_info)
				     .setView(et)
				     .setPositiveButton("ȷ��", new OnClickListener() {	
				    	
						@Override			 
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
							if(et.getText().equals(""))
								Toast.makeText(SettingActivity.this, "�ļ�������Ϊ��", Toast.LENGTH_SHORT).show(); 
							else
							{								
									addListVC = parse(et.getText().toString());
									if(addListVC!=null&&addListVC.size()!=0)
										{
											Toast.makeText(SettingActivity.this, "��ӳɹ�", Toast.LENGTH_SHORT).show();
											listVC.addAll(addListVC);
											
										}
								
								
							}
						}
					})
				     .setNegativeButton("ȡ��", null).show();

		}
		else if(arg2 == 2)
		{
			dText.setVisibility(0x00000000);
			tb.setVisibility(0x00000000);
		}
		else if(arg2 == 3)
		{
			
		}
		else if(arg2 == 4)
		{
			
		}
	}

	public List<VocabularyCard> parse(String xmlPath){
        List<VocabularyCard> VCS=new ArrayList<VocabularyCard>();
        VocabularyCard VC=null;
        InputStream inputStream=null;    
        //���XmlPullParser������
        XmlPullParser xmlParser = Xml.newPullParser();   

        try {
            //�õ��ļ����������ñ��뷽ʽ
            inputStream=this.context.getResources().getAssets().open(xmlPath);
            xmlParser.setInput(inputStream, "utf-8");
            //��ý��������¼���������п�ʼ�ĵ��������ĵ�����ʼ��ǩ��������ǩ���ı��ȵ��¼���
            int evtType=xmlParser.getEventType();
         //һֱѭ����ֱ���ĵ�����    
         while(evtType!=XmlPullParser.END_DOCUMENT){ 
            switch(evtType){ 
            case XmlPullParser.START_DOCUMENT:
            	break;
            case XmlPullParser.START_TAG:
                String tag = xmlParser.getName(); 
                //�����item��ǩ��ʼ����˵����Ҫʵ����������
                if (tag.equalsIgnoreCase("item")) { 
                	VC = new VocabularyCard(); 
                  
                }
                if(tag.equalsIgnoreCase("Targets"))
                {
                	String temp = xmlParser.nextText();
    	   			if(!temp.equals(""))
    	   				defaultTarget = Integer.valueOf(temp);
                }
               if(VC != null)
               { 	
            	 //ȡ��item��ǩ�е�һЩ����ֵ
            	   	if(tag.equalsIgnoreCase("word"))
            	   		{
            	   			VC.setWord(xmlParser.nextText());           	   			
            	   			VC.setTarget(defaultTarget);
            	   		}
            	   	else if(tag.equalsIgnoreCase("trans"))
                		VC.setTrans(xmlParser.nextText());
            	   	else if(tag.equalsIgnoreCase("phonetic"))
            	   		VC.setPhonetic(xmlParser.nextText());
            	   	else if(tag.equalsIgnoreCase("progress"))
            	   		{
            	   			String temp = xmlParser.nextText();
            	   			if(!temp.equals(""))
            	   				VC.setProgress(Integer.valueOf(temp));
            	   			if(VC.getTimes() < VC.getProgress())
        	   					VC.setTimes(VC.getProgress());
            	   		}
            	   		
            	   	else if(tag.equalsIgnoreCase("times"))
	            	   	{
            	   		String temp = xmlParser.nextText();
            	   		if(!temp.equals(""))
            	   			if(Integer.valueOf(temp) > VC.getProgress())
            	   			{
            	   				VC.setTimes(Integer.valueOf(temp));
            	   			}
	            	   	}
            	   	else if(tag.equalsIgnoreCase("target"))
        	   		{
    	   				String temp = xmlParser.nextText();
    	   				if(!temp.equals(""))
    	   					VC.setTarget(Integer.valueOf(temp));
        	   		} 	
            	   		
               }
               
                break;
                
           case XmlPullParser.END_TAG:
             //�������item��ǩ���������item������ӽ�������
               if (xmlParser.getName().equalsIgnoreCase("item") && VC != null) { 
                   VCS.add(VC); 
                   VC = null; 
               }
                break; 
                default:break;
            }
            //���xmlû�н������򵼺�����һ��v�ڵ�
            evtType=xmlParser.next();
         }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            Toast.makeText(SettingActivity.this, xmlPath+"������", Toast.LENGTH_SHORT).show(); 
        } 
        return VCS; 
    }
  
	

}
