package listener;

import java.util.Timer;
import javax.servlet.ServletContextEvent;//服务器后台事件
import javax.servlet.ServletContextListener;//服务器后台监听
import javax.servlet.annotation.WebListener;

//声明一个AutoRun类，使用服务器后台监听接口。
@WebListener
public class AutoRun implements ServletContextListener{
	private Timer timer = null;  
    public void contextInitialized(ServletContextEvent event) {  
    		System.out.println("listener>"+this.getClass().getName());
        // 在这里初始化监听器，在tomcat启动的时候监听器启动，可以在这里实现定时器功能  
        timer = new Timer(true);   
        // 添加日志，可在tomcat日志中查看到  
        event.getServletContext().log("定时器启动成功");  
        // 调用exportHistoryBean，0表示任务无延迟，4*60*60*1000表示一天执行一次。  
        timer.schedule(new exportHistoryBean(event.getServletContext()), 0, 24*60*60*1000);  
        event.getServletContext().log("添加任务成功");  
    }  
  
    // 在这里关闭监听器，所以在这里销毁定时器。  
    public void contextDestroyed(ServletContextEvent event) {  
        timer.cancel();  
        event.getServletContext().log("定时器已销毁");  
    }  
  
}  