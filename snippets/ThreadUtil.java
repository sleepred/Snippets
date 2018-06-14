package snippets;


import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadUtil {
	public static void sleep(long tm) {
		try {
			Thread.sleep(tm);
		} catch (InterruptedException e) {
			// Ignore e
		}
	}

	public static void qWait() {
		sleep(100);
	}

	public static void wait(Object o) {
		try {
			o.wait();
		} catch (InterruptedException e) {
			// Ignore e
		}
	}

	public static void wait(Object o, long time) {
		try {
			o.wait(time);
		} catch (InterruptedException e) {
			// Ignore e
		}
	}

	public static String getName(Thread t) {
		return getName(t.getClass());
	}

	public static String getThreadStack(long id){
		ThreadMXBean tmb = ManagementFactory.getThreadMXBean();
		ThreadInfo f = tmb.getThreadInfo(id, 500);
		if (f == null)
			return null;
		return getStackTrace(f.getStackTrace()).toString();
	}
	public static String getStackTrace(StackTraceElement[] se) {
		String CRLF = System.getProperty("line.separator");
		StringBuffer sb = new StringBuffer();
		if (se != null) {
			for (int i = 0; i < se.length; i++) {
				if (se[i] != null)
					sb.append(se[i].toString()).append(CRLF);
			}
		}

		return sb.toString();
	}

	public static String getStackTrace(Throwable t) {
        String CRLF = System.getProperty("line.separator");
        StringBuffer sb = new StringBuffer();
        sb.append(t.toString()).append(CRLF);
        StackTraceElement[] se = t.getStackTrace();
        if (se != null) {
            for (int i = 0; i < se.length; i++) {
                if (se[i] != null) {
                    sb.append("\t").append(se[i].toString());
                    if (i != se.length -1) {
                    	sb.append(CRLF);
                    }          
                }
            }
        }
        
        return sb.toString();    	
    }
	public static void getStackTrace(StringBuffer sb , Throwable t, int maxCnt) {
		int max = maxCnt;
		if(t==null)
			return ;
		if(max<=0){
			max=10000;
		}
        String CRLF = System.getProperty("line.separator");
        sb.append(t);
        StackTraceElement[] se = t.getStackTrace();
        if (se != null && se.length>0) {
            for (int i = 0; i < se.length && i <max; i++) {
                if (se[i] != null) {
                    sb.append(CRLF);
                	sb.append("\t").append(se[i]);          
                }
            }
            if(max < se.length){
            	sb.append(CRLF).append("\t...more lines ").append(String.valueOf(se.length-max)); 
            }
        }else{
        	sb.append(CRLF).append("\tno stack info ");
        }
        
      
    }

}