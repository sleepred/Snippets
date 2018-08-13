package com.lgcns.exercise.accesslog;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AccessLogParsing {

    private int success;
    private int images;

    public int getSuccess() {
        return success;
    }

    public int getImages() {
        return images;
    }

    // --------------------------------------
    // �Ʒ� �޼ҵ忡 ������ �����Ͻÿ�.
    public void parse( String[] parseLog ) {
    	for(String log : parseLog){
    		String datas[] = log.split(" ");
    		String resultCode = datas[8];
    		
    		if("200".equals(resultCode)){
    			success++;
    		}
    		
    		String uri = datas[6];
    		
    		if(uri.lastIndexOf("?") >= 0){
    			uri = uri.substring(0, uri.lastIndexOf("?"));
    		}
    		
    		if(uri.lastIndexOf(".") < 0){
    			continue;
    		}
    		
			String ext = uri.substring(uri.lastIndexOf(".") + 1, uri.length());
			
			if(ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("gif") || ext.equalsIgnoreCase("png")){
				images++;
			}
    	}
    }

}
