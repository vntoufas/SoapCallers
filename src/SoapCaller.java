import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class SoapCaller {

  public final static String OPERATION = "NumberToWords";
  public final static String SERVICE = "https://www.dataaccess.com/webservicesserver/NumberConversion.wso";
  public final static String XMLREQUEST = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + 
  		"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" + 
  		"  <soap:Body>\n" + 
  		"    <NumberToWords xmlns=\"http://www.dataaccess.com/webservicesserver/\">\n" + 
  		"      <ubiNum>9</ubiNum>\n" + 
  		"    </NumberToWords>\n" + 
  		"  </soap:Body>\n" + 
  		"</soap:Envelope>";
  
  public static void main(String[] args) {
  
	  boolean returnFlag = false;
	  
    try {
      URL u = new URL(SERVICE);
      URLConnection uc = u.openConnection();
      HttpURLConnection connection = (HttpURLConnection) uc;
      connection.setDoOutput(true);
      connection.setDoInput(true);
     
      
      connection.setRequestProperty("Content-Length",String.valueOf(XMLREQUEST.length()));
      connection.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
      connection.setRequestMethod("POST");
      connection.setRequestProperty("POST ", OPERATION);

      OutputStream out = connection.getOutputStream();
      Writer wout = new OutputStreamWriter(out);
      wout.write(XMLREQUEST);      
      wout.flush();
      wout.close();
    
      InputStream in = connection.getInputStream();
      String soapMessage = null;
      soapMessage = connection.getResponseMessage();
      System.out.println("Server responded with: "+soapMessage);
     
      int c;
      StringBuilder sb = new StringBuilder();
      while ((c = in.read()) != -1) 
    	  {System.out.write(c);
    	  sb.append((char)c);}
      
      in.close();
      //System.out.println(sb.toString());
      if (sb.toString().contains(" <m:NumberToWordsResult>nine </m:NumberToWordsResult>") && soapMessage.equals("OK")) {
    	  returnFlag = true;
      }
    } 
    catch (IOException e) {
    	System.err.println("The Web service is not available right now..."); 
  
    }
    
    if (returnFlag == true){
    	System.out.println("\nTadaaaah! Server Up and Running. Go on doing whatever you wanted with this WS");}
    	
    	
    }
    
  }
