package ethan.cn;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Xml;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class ScanActivity extends Activity implements OnGestureListener,OnTouchListener{
        private ViewFlipper mFlipper;
        GestureDetector mGestureDetector;
        private int mCurrentLayoutState;
        private static final int FLING_MIN_DISTANCE = 100;
        private static final int FLING_MIN_VELOCITY = 200;
        private static final int VISIBLE = 0x00000000;
        private static final int INVISIBLE = 0x00000004;
        private ImageView setting;
        private ImageView dataShow;
        private ImageView scrollerbar;
        private TextView tmodify1; 
        private TextView tmodify2;
        private TextView wdetail1;
        private TextView wdetail2;
        private TextView word1;
        private TextView word2;
        private TextView counter1;
        private TextView counter2;
        private ImageView ishow1;
        private ImageView ishow2;
        private ImageView wrong;
        private ImageView right;
        private ImageView iforward;
        private ImageView iback;
        private ImageView iUpdata;
        private ImageView ihelp;
        private ProgressBar pb1;
        private ProgressBar pb2;
        //��ʾ����
        static private boolean firstlauch = true;
        static public int currentPage = 0;
        @Override
        public void onCreate(Bundle savedInstanceState) {

                super.onCreate(savedInstanceState);
                setContentView(R.layout.scanview);
                mFlipper = (ViewFlipper) findViewById(R.id.viewFlipper1);
                //ע��һ����������ʶ�����
                mGestureDetector = new GestureDetector(this);
                //��mFlipper����һ��listener
                mFlipper.setOnTouchListener(this);
                mCurrentLayoutState = 0;
                //������סViewFlipper,��������ʶ���϶�������
                mFlipper.setLongClickable(true); 
                //��þ��

                setting = (ImageView)findViewById(R.id.imaset);         //�����á�ͼ��
                dataShow = (ImageView)findViewById(R.id.imadata);       //�����ݡ�ͼ��
                tmodify1 = (TextView)findViewById(R.id.textmodify1);	//����ϸ���ı�1
                word1 = (TextView)findViewById(R.id.textword1);			//�����ʡ��ı�1
                wdetail1 = (TextView)findViewById(R.id.textwordDetail1);//�����⡱�ı�1
                pb1 = (ProgressBar)findViewById(R.id.progressBar1);		//�����ȡ�������1
                counter1 = (TextView)findViewById(R.id.textCounter1);   //�����д������ı�1
                iUpdata = (ImageView)findViewById(R.id.imaupdate);		//"����"ͼ��
                wrong = (ImageView)findViewById(R.id.imawrong);         //������ͼ��
                right = (ImageView)findViewById(R.id.imaright);			//����ȷ��ͼ��
                iforward = (ImageView)findViewById(R.id.imarightdir);	//����ǰ��ͼ��
                iback = (ImageView)findViewById(R.id.imaleftdir);       //�����ͼ��
                ishow1 = (ImageView)findViewById(R.id.imashow1);		//����ʾ/���� ���⡱ͼ��
                scrollerbar = (ImageView)findViewById(R.id.imascorller);//����������ͼ
                ihelp = (ImageView)findViewById(R.id.imahelp);			//"����"ͼ��
                
                wdetail1.setVisibility(SettingActivity.transShow);		
                scrollerbar.scrollTo(115, 0);
                /*
                 * ��������ʾ/���� ���⡱ͼ�� �����¼�
                 * �ı����textview��visibility,��������б�Ϊ���Ҳ��ɼ��򲻸ı�
                 */
                ishow1.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						wdetail1 = (TextView) findViewById(R.id.textwordDetail1);
						if(wdetail1.getVisibility()== VISIBLE)
							{
								wdetail1.setVisibility(INVISIBLE);
							}
						else if(wdetail1.getVisibility()==INVISIBLE&&SettingActivity.listVC.size()!=0)
							{
								wdetail1.setVisibility(VISIBLE);
								wrong.setVisibility(VISIBLE);
								right.setVisibility(VISIBLE);
							}
					}
				});
                //����ǵ�һ�����������ϵͳĬ�ϵ��ʱ�װ������
                if(firstlauch == true)
                	{
                		loadVC();
                		firstlauch = false;
                	}
                //ˢ�½����������ʾ
        		handlerUpata.postDelayed(vc,100);
        		 /*
                 * �ࡰ��ϸ����Textview������¼�
                 * ��������ϸ���޸ģ�����
                 */
                tmodify1.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {

						// TODO Auto-generated method stub
						Intent i = new Intent(ScanActivity.this,
                				ModifyActivity.class);				 
                		startActivity(i);
                		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);  
                        
					}
				});
                /*
                 * ��������(ImageView)��ť
                 * ���������ð�ť�������ö���
                 */
                setting.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(ScanActivity.this,
                				SettingActivity.class);				 
                		startActivity(i);
                		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);   
                		
					}
				});
                /*
                 * ���������ݡ�ͼ�� ����¼�
                 */
                dataShow.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(ScanActivity.this,
                				DataShowActivity.class);			
                		startActivity(i);
                		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out); 
					}
				});
                /*
                 * ������������ͼ�� ����¼�
                 */
                ihelp.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(ScanActivity.this,
                				HelpActivity.class);			
                		startActivity(i);
                		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out); 
					}
				});
                /*
                 * ���������¡�ͼ�� ����¼�
                 */
                iUpdata.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						handlerUpata.postDelayed(vc,100);
					}
				});
                /*
                 * ��������ǰ��ͼ�� ����¼�
                 * ����ǰҳ���1���ж��Ƿ񳬹����������������������switchLayoutStateTo(��ǰҳ��)
                 * ���������õ�ǰҳ��Ϊ��������������
                 */
                iforward.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						currentPage++;
			        	if(currentPage >= SettingActivity.listVC.size()&&currentPage != 0)
						{
			    			Toast.makeText(ScanActivity.this, "�ѵ����һҳ!", Toast.LENGTH_SHORT).show();
			    			if(currentPage != 0)
			    				currentPage = SettingActivity.listVC.size() - 1;
			    			return;
						}
			        	updataScrollerBar(-1);
						switchLayoutStateTo(currentPage);

					}
				});
                /*
                 * ���������ͼ�� ����¼�
                 * ����ǰҳ���1������С��0�������switchLayoutStateTo(��ǰҳ��)
                 * ��С��0������0������
                 */
                iback.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						currentPage--;
			        	if(currentPage < 0)
						{
			    			Toast.makeText(ScanActivity.this, "�ѵ���һҳ!", Toast.LENGTH_SHORT).show();
			    			currentPage = 0;
			    			return;
						}
			        	updataScrollerBar(1);
						switchLayoutStateTo(currentPage);
					}
				});
                /*
                 * ����������ͼ�� ����¼�
                 * ��ǰ���ʵı��д���+1���Լ����гɹ�����-1
                 */
                wrong.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						SettingActivity.listVC.get(currentPage).addTimes();
						SettingActivity.listVC.get(currentPage).subProgress();
						handlerUpata.postDelayed(vc,100);
					}
				});
                /*
                 * ��������ȷ��ͼ�� ����¼�
                 * ���õ�ǰ���ʵı��д���+1���Լ����гɹ�����+1
                 */
                right.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						SettingActivity.listVC.get(currentPage).addTimes();
						SettingActivity.listVC.get(currentPage).addProgress();
						handlerUpata.postDelayed(vc,100);
					}
				});
        }
        /**
         * �����˳�
         */
        public boolean onKeyDown(int keyCode, KeyEvent event) {
        	  if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
        	   dialog();
        	  }
        	  return false;
        	 }
    	public void dialog() {
    		//super.onBackPressed();
    		//overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out); 
    		TextView t = new TextView(this);
    		t.setText("  �˳����ʿ�Ƭ���Զ�����");
    		 new AlertDialog.Builder(this).setTitle("�Ƿ��˳�").setIcon(
    			     android.R.drawable.ic_dialog_info).setView(
    			     t ).setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
    					
    					@Override
    					public void onClick(DialogInterface dialog, int which) {
    						// TODO Auto-generated method stub 						
    						  saveVC();
    						  dialog.dismiss();
    						  currentPage = 0;
    						  firstlauch = true;
    						  ScanActivity.this.finish();
    					}
    				})
    			     .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							return;
						}
					}).show();
    		
    		
    		}
        /**
         * 
         * �˷������ڸ��´ʻ���ʾ
         * @param switchTo
         */
        Handler handlerUpata = new Handler();
        Runnable vc = new Runnable() {
         @Override
         public void run() {
          System.out.println("5-->Runnable");
        //���µ��ʼ�����
      	VocabularyCard v = new VocabularyCard();
  		if(SettingActivity.listVC.size()!=0)
  			v = SettingActivity.listVC.get(currentPage);
      	if(SettingActivity.listVC.size()==0)
      	{
      		if(word1.isShown())
      			{
      				word1.setText("��ӭʹ��ICARD");
      				wdetail1.setText("Ŀǰ���ʿ�Ϊ�գ��������Ϸ����ð�ť���������ý��棬������е��ʵ䵼���ĵ��ʱ���");
      				pb1.setProgress(0);
                    wdetail1.setVisibility(VISIBLE);
                    tmodify1.setVisibility(INVISIBLE);
      			}		
   			wrong.setVisibility(INVISIBLE);
   			right.setVisibility(INVISIBLE);
   			
      	}
      	else
      	{
      		wrong.setVisibility(VISIBLE);
   			right.setVisibility(VISIBLE);
   			
   			
      		if(word1 != null &&word1.isShown())
  			{
      			
      			word1.setText(v.getWord()+"\r\n"+v.getPhonetic());
      			wdetail1.setText(v.getTrans());
      			counter1.setText(String.valueOf(v.getTimes()));
      			pb1.setProgress(Integer.valueOf((int) ((Double.valueOf(v.getProgress())/Double.valueOf(v.getTarget()))*100)));
      			wdetail1.setVisibility(SettingActivity.transShow);
      			tmodify1.setVisibility(VISIBLE);
  			}
      		else if(word2 != null &&word2.isShown())
      		{
      			
          		word2.setText(v.getWord()+"\r\n"+v.getPhonetic());
          		wdetail2.setText(v.getTrans());
          		counter2.setText(String.valueOf(v.getTimes()));
          		pb2.setProgress(Integer.valueOf((int) ((Double.valueOf(v.getProgress())/Double.valueOf(v.getTarget()))*100)));
          		wdetail2.setVisibility(SettingActivity.transShow);
          		tmodify2.setVisibility(VISIBLE);
      		}
      	}         }
        };


        /**
         * 
         * �˷������ڸ��»���
         * @param switchTo
         */
        public void updataScrollerBar(int dirtection)
        {   
        	if(dirtection == 1)
        		scrollerbar.scrollBy(250/SettingActivity.listVC.size(), 0);
        	else if(dirtection == -1)
        		scrollerbar.scrollBy(-(250/SettingActivity.listVC.size()), 0);
        }
        /**
         * 
         * �˷�������ָ����ת��ĳ��ҳ��
         * @param switchTo
         */
        public void switchLayoutStateTo(int switchTo) {

                while (mCurrentLayoutState != switchTo) {
                        if (mCurrentLayoutState > switchTo) {
                                mCurrentLayoutState--;
                                mFlipper.setInAnimation(inFromLeftAnimation());
                                mFlipper.setOutAnimation(outToRightAnimation());
                                mFlipper.showPrevious();
                               
                        } else {
                                mCurrentLayoutState++;
                                mFlipper.setInAnimation(inFromRightAnimation());
                                mFlipper.setOutAnimation(outToLeftAnimation());
                                mFlipper.showNext();
                                
                        }
                        handlerUpata.postDelayed(vc,100);
                }
                ;
                
        }
 
        /**
         * ������Ҳ����Ķ���Ч��
         * @return
         */
        protected Animation inFromRightAnimation() {
                Animation inFromRight = new TranslateAnimation(
                                Animation.RELATIVE_TO_PARENT, +0.685f,
                                Animation.RELATIVE_TO_PARENT, 0.0f,
                                Animation.RELATIVE_TO_PARENT, 0.0f,
                                Animation.RELATIVE_TO_PARENT, 0.0f);
                inFromRight.setDuration(500);
                inFromRight.setInterpolator(new AccelerateInterpolator());
                
                return inFromRight;
        }
 
        /**
         * ���������˳��Ķ���Ч��
         * @return
         */
        protected Animation outToLeftAnimation() {
                Animation outtoLeft = new TranslateAnimation(
                                Animation.RELATIVE_TO_PARENT, 0.0f,
                                Animation.RELATIVE_TO_PARENT, -0.685f,
                                Animation.RELATIVE_TO_PARENT, 0.0f,
                                Animation.RELATIVE_TO_PARENT, 0.0f);
                outtoLeft.setDuration(500);
                outtoLeft.setInterpolator(new AccelerateInterpolator());
                return outtoLeft;
        }
 
        /**
         * �����������Ķ���Ч��
         * @return
         */
        protected Animation inFromLeftAnimation() {
                Animation inFromLeft = new TranslateAnimation(
                                Animation.RELATIVE_TO_PARENT, -0.685f,
                                Animation.RELATIVE_TO_PARENT, 0.0f,
                                Animation.RELATIVE_TO_PARENT, 0.0f,
                                Animation.RELATIVE_TO_PARENT, 0.0f);
                inFromLeft.setDuration(500);
                inFromLeft.setInterpolator(new AccelerateInterpolator());
                return inFromLeft;
        }
 
        /**
         * ������Ҳ��˳�ʱ�Ķ���Ч��
         * @return
         */
        protected Animation outToRightAnimation() {
                Animation outtoRight = new TranslateAnimation(
                                Animation.RELATIVE_TO_PARENT, 0.0f,
                                Animation.RELATIVE_TO_PARENT, 0.685f,
                                Animation.RELATIVE_TO_PARENT, 0.0f,
                                Animation.RELATIVE_TO_PARENT, 0.0f);
                outtoRight.setDuration(500);
                outtoRight.setInterpolator(new AccelerateInterpolator());
                return outtoRight;
        }
 
        public boolean onDown(MotionEvent e) {
                // TODO Auto-generated method stub
                return false;
        }
 
        /*
         * �û����´������������ƶ����ɿ�����������¼�
         * e1����1��ACTION_DOWN MotionEvent
         * e2�����һ��ACTION_MOVE MotionEvent
         * velocityX��X���ϵ��ƶ��ٶȣ�����/��
         * velocityY��Y���ϵ��ƶ��ٶȣ�����/��
         * �������� ��
         * X�������λ�ƴ���FLING_MIN_DISTANCE�����ƶ��ٶȴ���FLING_MIN_VELOCITY������/��
         */
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                        float velocityY) {
                if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
                    && Math.abs(velocityX) > FLING_MIN_VELOCITY) {

					currentPage++;
					if(currentPage >= SettingActivity.listVC.size())
					{
            			Toast.makeText(this, "�ѵ����һҳ!", Toast.LENGTH_SHORT).show(); 
            			if(currentPage != 0)
            				currentPage = SettingActivity.listVC.size()-1;
            			return false;
					}
					// ������໬����ʱ��
                        //����View������Ļʱ��ʹ�õĶ���
                        mFlipper.setInAnimation(inFromRightAnimation());
                        //����View�˳���Ļʱ��ʹ�õĶ���
                        mFlipper.setOutAnimation(outToLeftAnimation());
                        mFlipper.showNext();
                        updataScrollerBar(-1);
            } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
                    && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
            	
            		currentPage--;
            		if(currentPage < 0)
					{
            			Toast.makeText(this, "�ѵ���һҳ!", Toast.LENGTH_SHORT).show();
            			currentPage = 0;
            			return false;
					}
                    // �����Ҳ໬����ʱ��
                        mFlipper.setInAnimation(inFromLeftAnimation());
                        mFlipper.setOutAnimation(outToRightAnimation());
                        mFlipper.showPrevious();
                        updataScrollerBar(1);
            }
                word2 = (TextView)findViewById(R.id.textword2);
                wdetail2 = (TextView)findViewById(R.id.textwordDetail2);
                tmodify2 = (TextView)findViewById(R.id.textmodify2);               
                pb2 = (ProgressBar)findViewById(R.id.ProgressBar2);
                counter2 = (TextView)findViewById(R.id.textCounter2);
                tmodify2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(ScanActivity.this,
                				ModifyActivity.class);				 
                		startActivity(i);
                		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);  
                        
					}
				});
                ishow2 = (ImageView)findViewById(R.id.imashow2);
                ishow2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						wdetail2 = (TextView) findViewById(R.id.textwordDetail2);
						if(wdetail2.getVisibility()==0x00000000)
							wdetail2.setVisibility(0x00000004);
						else
							{
								wdetail2.setVisibility(0x00000000);
							}
						
					}
				});
                //������ʾ�ʻ�
                handlerUpata.postDelayed(vc,100);
                return false;
        }
 
        public void onLongPress(MotionEvent e) {
                // TODO Auto-generated method stub
 
        }
 
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                        float distanceY) {
                return false;
        }
 
        public void onShowPress(MotionEvent e) {
                // TODO Auto-generated method stub
 
        }
 
        public boolean onSingleTapUp(MotionEvent e) {
                // TODO Auto-generated method stub
                return false;
        }
        public boolean onTouch(View v, MotionEvent event) {
                // һ��Ҫ�������¼���������ʶ����ȥ�����Լ��������鷳�ģ�
                return mGestureDetector.onTouchEvent(event);
        }
        /**
         * ת�ش�Ĭ�ϵ�ϵͳ���ʱ�װ������
         * ��ǰ�̶�Ϊmy.xml
         */
        public void loadVC()
        {
        	SettingActivity.listVC = parse("my.xml");
        }
      
        /**
         * ����λ��data/<package name>/data �µ��ļ�·��
         * ���ش�XML�����ĵ����б�
         */
    	public List<VocabularyCard> parse(String xmlPath){
            List<VocabularyCard> VCS=new ArrayList<VocabularyCard>();
            VocabularyCard VC=null;
            InputStream inputStream=null;    
            //���XmlPullParser������
            XmlPullParser xmlParser = Xml.newPullParser();   

            try {
                //�õ��ļ����������ñ��뷽ʽ
                inputStream=openFileInput(xmlPath);
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
        	   				SettingActivity.defaultTarget = Integer.valueOf(temp);
                    }
                   if(VC != null)
                   { 	
                	 //ȡ��item��ǩ�е�һЩ����ֵ
                	   	if(tag.equalsIgnoreCase("word"))
                	   		{
                	   			VC.setWord(xmlParser.nextText());           	   			
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
             Toast.makeText(ScanActivity.this, "װ��Ĭ���ļ�"+xmlPath+"�ɹ�", Toast.LENGTH_SHORT).show(); 
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                Toast.makeText(ScanActivity.this, "Ĭ���ļ�"+xmlPath+"������", Toast.LENGTH_SHORT).show(); 
            } 
            return VCS; 
        } 
    /**
     * ����ǰ��ʾ�ĵ����б����ϵͳĬ�ϵ���xml�ļ���-"my.xml"
     */
   	 public void saveVC()
	    {
   		 if(SettingActivity.listVC.size() == 0)
   			 return;
   		 StringWriter stringWriter = new StringWriter();  
	        try {  
	            // ��ȡXmlSerializer����  
	            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();  
	            XmlSerializer xmlSerializer = factory.newSerializer();  
	            // �������������  
	            xmlSerializer.setOutput(stringWriter);  
	            /* 
	             * startDocument(String encoding, Boolean standalone)encoding������뷽ʽ 
	             * standalone  ������ʾ���ļ��Ƿ���������ⲿ���ļ��� 
	             * ��ֵ�� ��yes�� ��ʾû�к����ⲿ�����ļ�����ֵ�� ��no�� ���ʾ�к����ⲿ�����ļ���Ĭ��ֵ�� ��yes���� 
	             */  
	            xmlSerializer.startDocument("utf-8", true);  
	            xmlSerializer.startTag(null, "mywordbook");  
	            for(VocabularyCard vc:SettingActivity.listVC){  
	            	//�½��ڵ�
	            	xmlSerializer.startTag(null, "item");
	            		//�½��ӽڵ�
	            		xmlSerializer.startTag(null, "word");  
	            		xmlSerializer.text(vc.getWord());  
	            		xmlSerializer.endTag(null, "word");   
	            		//�½��ӽڵ�
	            		xmlSerializer.startTag(null, "trans");  
	            		xmlSerializer.text(vc.getTrans());  
	            		xmlSerializer.endTag(null, "trans");  
	            		//�½��ӽڵ�
	            		xmlSerializer.startTag(null, "phonetic");  
	            		xmlSerializer.text(vc.getPhonetic());  
	            		xmlSerializer.endTag(null, "phonetic");  
	            		//�½��ӽڵ�
	            		xmlSerializer.startTag(null, "target");  
	            		xmlSerializer.text(String.valueOf(vc.getTarget()));  
	            		xmlSerializer.endTag(null, "target"); 
	            		//�½��ӽڵ�
	            		xmlSerializer.startTag(null, "times");  
	            		xmlSerializer.text(String.valueOf(vc.getTimes()));  
	            		xmlSerializer.endTag(null, "times");  
	            		//�½��ӽڵ�
	            		xmlSerializer.startTag(null, "progress");  
	            		xmlSerializer.text(String.valueOf(vc.getProgress()));  
	            		xmlSerializer.endTag(null, "progress");  
	            	//�����ýڵ�Ĺ���	
	                xmlSerializer.endTag(null, "item"); 	                
	            }
	            xmlSerializer.startTag(null, "Targets");  
        		xmlSerializer.text(String.valueOf(SettingActivity.defaultTarget));  
        		xmlSerializer.endTag(null, "Targets");  
	            xmlSerializer.endTag(null, "mywordbook");  
	            xmlSerializer.endDocument();  
	            //�����ļ���
	            FileOutputStream outStream=this.openFileOutput("my.xml",Context.MODE_WORLD_READABLE);
	            outStream.write(stringWriter.toString().getBytes());
	            outStream.close();

	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        Toast.makeText(ScanActivity.this,"���ʿ�Ƭ�ѱ���",Toast.LENGTH_LONG).show();
	        return ;
	    }
}
