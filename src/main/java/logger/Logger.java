package logger;

import java.sql.Timestamp;

public class Logger{

    public void sendLog(String str){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("+++ Runtime-Log: Meldung aus Component: "+str+"("+timestamp+")");

    }
}
