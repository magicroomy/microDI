package de.microdi.helper;

import java.text.FieldPosition;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;


/**
 * %le LEVEL
 * %ln LOGGERNAME
 * %me MESSAGE
 * %sc SOURCE CLASS NAME
 * %sm SOURCE METHOD NAME
 * %ts Timestamp
 * %th Thread ID
 * 
 * @author vraum
 *
 */

public class PatternFormatter extends Formatter
{
   MessageFormat mf ;
   SimpleDateFormat sdf ;
   StringBuffer bf = new StringBuffer(1000) ;
   String messagePattern ;
   
   String empty= "                                                                                                " ;
   
   public PatternFormatter(String pattern, String datePattern)
   {
      super();
      pattern = pattern.replace("%le", "{0}") ;
      pattern = pattern.replace("%ln", "{1}") ;
      pattern = pattern.replace("%me", "{2}") ;
      pattern = pattern.replace("%sc", "{3}") ;
      pattern = pattern.replace("%sm", "{4}") ;
      pattern = pattern.replace("%ts", "{5}") ;
      pattern = pattern.replace("%th", "{6}") ;
      
      mf = new MessageFormat(pattern) ;
      messagePattern = pattern ;
      sdf = new SimpleDateFormat(datePattern) ;
   }

   public String format(LogRecord record)
   {
      bf.setLength(0) ;
      Object[] data = new Object[7] ;
      data[0] = record.getLevel().toString();
      data[1] = record.getLoggerName();
      data[2] = record.getMessage();
      data[3] = record.getSourceClassName();
      data[4] = record.getSourceMethodName();
      data[5] = sdf.format( record.getMillis() );
      data[6] = record.getThreadID();

      mf.format(data, bf, new FieldPosition(0));
      bf.append("\n") ;
      
      if ( record.getThrown() != null )
      {
         listThrowableLines( record.getThrown(), 0, bf ) ;
      }
      
      return bf.toString() ;
      
   }
   
   private void listThrowableLines( Throwable throwable, int indent, StringBuffer bf )
   {
      
      bf.append(empty.subSequence(0,  indent)) ;
      bf.append( throwable.getClass().getName()).append(": ").append( throwable.getMessage() ).append("\n") ;
      for ( StackTraceElement currElement: throwable.getStackTrace())
      {
         bf.append(empty.subSequence(0,  indent)).append("\tat ").append(currElement.getClassName()).append(".").append(currElement.getMethodName()).append("(").append(currElement.getFileName()).append(":").append( currElement.getLineNumber()).append(")\n") ;
      }
      if ( throwable.getCause() != null )
      {
         listThrowableLines(throwable.getCause(), indent, bf) ;
      }
   }
   
}