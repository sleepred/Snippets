package com.lgcns.exercise.accesslog;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class AccessLogParsingRun {

    public static void main( String[] args ) throws IOException {
        AccessLogParsing accessLog = new AccessLogParsing();
        accessLog.parse( parseLog() );
        print( accessLog );
    }

    private static void print( AccessLogParsing accessLog ) {

        System.out.println( "Result Status 200 count : " + accessLog.getSuccess() );
        System.out.println( "Image file count : " + accessLog.getImages() );
    }
    
    private static String[] parseLog() throws IOException {
        Path filePath = new File( "data/access.log" ).toPath();
        Charset charset = Charset.forName( "UTF-8" );

        List<String> stringList = Files.readAllLines( filePath, charset );
        return stringList.toArray( new String[] {} );
    }

}
