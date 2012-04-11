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
        //显示参数
        static private boolean firstlauch = true;
        static public int currentPage = 0;
        @Override
        public void onCreate(Bundle savedInstanceState) {

                super.onCreate(savedInstanceState);
                setContentView(R.layout.scanview);
                mFlipper = (ViewFlipper) findViewById(R.id.viewFlipper1);
                //注册一个用于手势识别的类
                mGestureDetector = new GestureDetector(this);
                //给mFlipper设置一个listener
                mFlipper.setOnTouchListener(this);
                mCurrentLayoutState = 0;
                //允许长按住ViewFlipper,这样才能识别拖动等手势
                mFlipper.setLongClickable(true); 
                //获得句柄

                setting = (ImageView)findViewById(R.id.imaset);         //“设置”图标
                dataShow = (ImageView)findViewById(R.id.imadata);       //“数据”图标
                tmodify1 = (TextView)findViewById(R.id.textmodify1);	//“详细”文本1
                word1 = (TextView)findViewById(R.id.textword1);			//“单词”文本1
                wdetail1 = (TextView)findViewById(R.id.textwordDetail1);//“词意”文本1
                pb1 = (ProgressBar)findViewById(R.id.progressBar1);		//“进度”进度条1
                counter1 = (TextView)findViewById(R.id.textCounter1);   //“背诵次数”文本1
                iUpdata = (ImageView)findViewById(R.id.imaupdate);		//"更新"图标
                wrong = (ImageView)findViewById(R.id.imawrong);         //“错误”图标
                right = (ImageView)findViewById(R.id.imaright);			//“正确”图标
                iforward = (ImageView)findViewById(R.id.imarightdir);	//“向前”图标
                iback = (ImageView)findViewById(R.id.imaleftdir);       //“向后”图标
                ishow1 = (ImageView)findViewById(R.id.imashow1);		//“显示/隐藏 词意”图标
                scrollerbar = (ImageView)findViewById(R.id.imascorller);//“滚动条”图
                ihelp = (ImageView)findViewById(R.id.imahelp);			//"帮助"图标
                
                wdetail1.setVisibility(SettingActivity.transShow);		
                scrollerbar.scrollTo(115, 0);
                /*
                 * 监听“显示/隐藏 词意”图标 按下事件
                 * 改变词意textview的visibility,如果单词列表为空且不可见则不改变
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
                //如果是第一次启动，则从系统默认单词表装载数据
                if(firstlauch == true)
                	{
                		loadVC();
                		firstlauch = false;
                	}
                //刷新界面的文字显示
        		handlerUpata.postDelayed(vc,100);
        		 /*
                 * 监“详细”（Textview）点击事件
                 * 点击则打开详细（修改）界面
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
                 * 监听设置(ImageView)按钮
                 * 点击则打开设置按钮，并设置动画
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
                 * 监听“数据”图标 点击事件
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
                 * 监听“帮助”图标 点击事件
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
                 * 监听“更新”图标 点击事件
                 */
                iUpdata.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						handlerUpata.postDelayed(vc,100);
					}
				});
                /*
                 * 监听“向前”图标 点击事件
                 * 将当前页面加1，判断是否超过总量，若不超过，则调用switchLayoutStateTo(当前页面)
                 * 若超过则置当前页面为单词总数，返回
                 */
                iforward.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						currentPage++;
			        	if(currentPage >= SettingActivity.listVC.size()&&currentPage != 0)
						{
			    			Toast.makeText(ScanActivity.this, "已到最后一页!", Toast.LENGTH_SHORT).show();
			    			if(currentPage != 0)
			    				currentPage = SettingActivity.listVC.size() - 1;
			    			return;
						}
			        	updataScrollerBar(-1);
						switchLayoutStateTo(currentPage);

					}
				});
                /*
                 * 监听“向后”图标 点击事件
                 * 将当前页面减1，若不小于0，则调用switchLayoutStateTo(当前页面)
                 * 若小于0，则置0，返回
                 */
                iback.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						currentPage--;
			        	if(currentPage < 0)
						{
			    			Toast.makeText(ScanActivity.this, "已到第一页!", Toast.LENGTH_SHORT).show();
			    			currentPage = 0;
			    			return;
						}
			        	updataScrollerBar(1);
						switchLayoutStateTo(currentPage);
					}
				});
                /*
                 * 监听“错误”图标 点击事件
                 * 当前单词的背诵次数+1，以及背诵成功次数-1
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
                 * 监听“正确”图标 点击事件
                 * 设置当前单词的背诵次数+1，以及背诵成功次数+1
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
         * 监听退出
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
    		t.setText("  退出单词卡片将自动保存");
    		 new AlertDialog.Builder(this).setTitle("是否退出").setIcon(
    			     android.R.drawable.ic_dialog_info).setView(
    			     t ).setPositiveButton("确认", new DialogInterface.OnClickListener() {
    					
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
    			     .setNegativeButton("取消", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							return;
						}
					}).show();
    		
    		
    		}
        /**
         * 
         * 此方法用于更新词汇显示
         * @param switchTo
         */
        Handler handlerUpata = new Handler();
        Runnable vc = new Runnable() {
         @Override
         public void run() {
          System.out.println("5-->Runnable");
        //更新单词及词意
      	VocabularyCard v = new VocabularyCard();
  		if(SettingActivity.listVC.size()!=0)
  			v = SettingActivity.listVC.get(currentPage);
      	if(SettingActivity.listVC.size()==0)
      	{
      		if(word1.isShown())
      			{
      				word1.setText("欢迎使用ICARD");
      				wdetail1.setText("目前单词库为空，请点击右上方设置按钮，进入设置界面，导入从有道词典导出的单词本。");
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
         * 此方法用于更新滑条
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
         * 此方法可以指定跳转到某个页面
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
         * 定义从右侧进入的动画效果
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
         * 定义从左侧退出的动画效果
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
         * 定义从左侧进入的动画效果
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
         * 定义从右侧退出时的动画效果
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
         * 用户按下触摸屏、快速移动后松开即触发这个事件
         * e1：第1个ACTION_DOWN MotionEvent
         * e2：最后一个ACTION_MOVE MotionEvent
         * velocityX：X轴上的移动速度，像素/秒
         * velocityY：Y轴上的移动速度，像素/秒
         * 触发条件 ：
         * X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒
         */
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                        float velocityY) {
                if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
                    && Math.abs(velocityX) > FLING_MIN_VELOCITY) {

					currentPage++;
					if(currentPage >= SettingActivity.listVC.size())
					{
            			Toast.makeText(this, "已到最后一页!", Toast.LENGTH_SHORT).show(); 
            			if(currentPage != 0)
            				currentPage = SettingActivity.listVC.size()-1;
            			return false;
					}
					// 当像左侧滑动的时候
                        //设置View进入屏幕时候使用的动画
                        mFlipper.setInAnimation(inFromRightAnimation());
                        //设置View退出屏幕时候使用的动画
                        mFlipper.setOutAnimation(outToLeftAnimation());
                        mFlipper.showNext();
                        updataScrollerBar(-1);
            } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
                    && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
            	
            		currentPage--;
            		if(currentPage < 0)
					{
            			Toast.makeText(this, "已到第一页!", Toast.LENGTH_SHORT).show();
            			currentPage = 0;
            			return false;
					}
                    // 当像右侧滑动的时候
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
                //更新显示词汇
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
                // 一定要将触屏事件交给手势识别类去处理（自己处理会很麻烦的）
                return mGestureDetector.onTouchEvent(event);
        }
        /**
         * 转载从默认的系统单词表装载数据
         * 当前固定为my.xml
         */
        public void loadVC()
        {
        	SettingActivity.listVC = parse("my.xml");
        }
      
        /**
         * 传入位于data/<package name>/data 下的文件路径
         * 返回从XML读出的单词列表
         */
    	public List<VocabularyCard> parse(String xmlPath){
            List<VocabularyCard> VCS=new ArrayList<VocabularyCard>();
            VocabularyCard VC=null;
            InputStream inputStream=null;    
            //获得XmlPullParser解析器
            XmlPullParser xmlParser = Xml.newPullParser();   

            try {
                //得到文件流，并设置编码方式
                inputStream=openFileInput(xmlPath);
                xmlParser.setInput(inputStream, "utf-8");
                //获得解析到的事件类别，这里有开始文档，结束文档，开始标签，结束标签，文本等等事件。
                int evtType=xmlParser.getEventType();
             //一直循环，直到文档结束    
             while(evtType!=XmlPullParser.END_DOCUMENT){ 
                switch(evtType){ 
                case XmlPullParser.START_DOCUMENT:
                	break;
                case XmlPullParser.START_TAG:
                    String tag = xmlParser.getName(); 
                    //如果是item标签开始，则说明需要实例化对象了
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
                	 //取出item标签中的一些属性值
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
                 //如果遇到item标签结束，则把item对象添加进集合中
                   if (xmlParser.getName().equalsIgnoreCase("item") && VC != null) { 
                       VCS.add(VC); 
                       VC = null; 
                   }
                    break; 
                    default:break;
                }
                //如果xml没有结束，则导航到下一个v节点
                evtType=xmlParser.next();
             }
             Toast.makeText(ScanActivity.this, "装载默认文件"+xmlPath+"成功", Toast.LENGTH_SHORT).show(); 
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                Toast.makeText(ScanActivity.this, "默认文件"+xmlPath+"不存在", Toast.LENGTH_SHORT).show(); 
            } 
            return VCS; 
        } 
    /**
     * 将当前显示的单词列表存入系统默认单词xml文件中-"my.xml"
     */
   	 public void saveVC()
	    {
   		 if(SettingActivity.listVC.size() == 0)
   			 return;
   		 StringWriter stringWriter = new StringWriter();  
	        try {  
	            // 获取XmlSerializer对象  
	            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();  
	            XmlSerializer xmlSerializer = factory.newSerializer();  
	            // 设置输出流对象  
	            xmlSerializer.setOutput(stringWriter);  
	            /* 
	             * startDocument(String encoding, Boolean standalone)encoding代表编码方式 
	             * standalone  用来表示该文件是否呼叫其它外部的文件。 
	             * 若值是 ”yes” 表示没有呼叫外部规则文件，若值是 ”no” 则表示有呼叫外部规则文件。默认值是 “yes”。 
	             */  
	            xmlSerializer.startDocument("utf-8", true);  
	            xmlSerializer.startTag(null, "mywordbook");  
	            for(VocabularyCard vc:SettingActivity.listVC){  
	            	//新建节点
	            	xmlSerializer.startTag(null, "item");
	            		//新建子节点
	            		xmlSerializer.startTag(null, "word");  
	            		xmlSerializer.text(vc.getWord());  
	            		xmlSerializer.endTag(null, "word");   
	            		//新建子节点
	            		xmlSerializer.startTag(null, "trans");  
	            		xmlSerializer.text(vc.getTrans());  
	            		xmlSerializer.endTag(null, "trans");  
	            		//新建子节点
	            		xmlSerializer.startTag(null, "phonetic");  
	            		xmlSerializer.text(vc.getPhonetic());  
	            		xmlSerializer.endTag(null, "phonetic");  
	            		//新建子节点
	            		xmlSerializer.startTag(null, "target");  
	            		xmlSerializer.text(String.valueOf(vc.getTarget()));  
	            		xmlSerializer.endTag(null, "target"); 
	            		//新建子节点
	            		xmlSerializer.startTag(null, "times");  
	            		xmlSerializer.text(String.valueOf(vc.getTimes()));  
	            		xmlSerializer.endTag(null, "times");  
	            		//新建子节点
	            		xmlSerializer.startTag(null, "progress");  
	            		xmlSerializer.text(String.valueOf(vc.getProgress()));  
	            		xmlSerializer.endTag(null, "progress");  
	            	//结束该节点的构造	
	                xmlSerializer.endTag(null, "item"); 	                
	            }
	            xmlSerializer.startTag(null, "Targets");  
        		xmlSerializer.text(String.valueOf(SettingActivity.defaultTarget));  
        		xmlSerializer.endTag(null, "Targets");  
	            xmlSerializer.endTag(null, "mywordbook");  
	            xmlSerializer.endDocument();  
	            //存入文件中
	            FileOutputStream outStream=this.openFileOutput("my.xml",Context.MODE_WORLD_READABLE);
	            outStream.write(stringWriter.toString().getBytes());
	            outStream.close();

	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        Toast.makeText(ScanActivity.this,"单词卡片已保存",Toast.LENGTH_LONG).show();
	        return ;
	    }
}
