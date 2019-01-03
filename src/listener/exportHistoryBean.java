package listener;

import java.util.Calendar;
import java.util.TimerTask;
import javax.servlet.ServletContext;

public class exportHistoryBean extends TimerTask {  
    private ServletContext context = null;  
  
    public exportHistoryBean(ServletContext context) {  
        this.context = context;  
    }  
  
    public void run() {  
    		System.out.println("listener>"+this.getClass().getName());
        int time = Calendar.getInstance().get(Calendar.DATE);
        context.log(time+"：开始执行指定任务");  
        // 这里调用自己的方法  
        FriendRecommend.setFriendRecommend();
        context.log("指定任务执行结束");
    }  
}  