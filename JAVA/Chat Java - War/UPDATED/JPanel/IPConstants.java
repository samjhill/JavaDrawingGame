/**
    IPConstants - our servers, and ports 
    
    Depending on how we write it, we may have to have two server programs running, one for images and one for the chat. 
*/
public interface IPConstants
{
    int CHAT_PORT_NUMBER     = 1200;
    int IMAGE_PORT_NUMBER    = 1201;
    int APPLET_WIDTH          = 1000; //800 for the canvas, 200 for the chat
    int APPLET_HEIGHT         = 600;
    String HTTP_HOST    = "http://www.doodlethings.com";
    String JAVA_HOST    = "http://doodlethings.student.rit.edu";
}