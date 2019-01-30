/**
 * This is based on code from https://github.com/charmedlabs/pixy2/blob/master/src/host/arduino/libraries/Pixy2/TPixy2.h
 * This uses the packet reference at https://docs.pixycam.com/wiki/doku.php?id=wiki:v2:protocol_reference&s[]=packet
 */



import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;
//import java.nio.ByteBuffer;
import edu.wpi.first.wpilibj.Timer;

final bool PIXY_DEBUG = true;

final int PIXY_DEFAULT_ARGVAL                 = 0x80000000;
final int PIXY_BUFFERSIZE                     = 0x104;
final int PIXY_CHECKSUM_SYNC                  = 0xc1af;
final int PIXY_NO_CHECKSUM_SYNC               = 0xc1ae;
final int PIXY_SEND_HEADER_SIZE               = 4;
final int PIXY_MAX_PROGNAME                   = 33;

final int PIXY_TYPE_REQUEST_CHANGE_PROG       = 0x02;
final int PIXY_TYPE_REQUEST_RESOLUTION        = 0x0c;
final int PIXY_TYPE_RESPONSE_RESOLUTION       = 0x0d;
final int PIXY_TYPE_REQUEST_VERSION           = 0x0e;
final int PIXY_TYPE_RESPONSE_VERSION          = 0x0f;
final int PIXY_TYPE_RESPONSE_RESULT           = 0x01;
final int PIXY_TYPE_RESPONSE_ERROR            = 0x03;
final int PIXY_TYPE_REQUEST_BRIGHTNESS        = 0x10;
final int PIXY_TYPE_REQUEST_SERVO             = 0x12;
final int PIXY_TYPE_REQUEST_LED               = 0x14;
final int PIXY_TYPE_REQUEST_LAMP              = 0x16;
final int PIXY_TYPE_REQUEST_FPS               = 0x18;

final int PIXY_RESULT_OK                      = 0;
final int PIXY_RESULT_ERROR                   = -1;
final int PIXY_RESULT_BUSY                    = -2;
final int PIXY_RESULT_CHECKSUM_ERROR          = -3;
final int PIXY_RESULT_TIMEOUT                 = -4;
final int PIXY_RESULT_BUTTON_OVERRIDE         = -5;
final int PIXY_RESULT_PROG_CHANGING           = -6;

// RC-servo values
final int PIXY_RCS_MIN_POS                    = 0;
final int PIXY_RCS_MAX_POS                    = 1000L;
final int PIXY_RCS_CENTER_POS                 = ((PIXY_RCS_MAX_POS-PIXY_RCS_MIN_POS)/2);

/*
#include "Pixy2CCC.h"
#include "Pixy2Line.h"
#include "Pixy2Video.h"
*/

//import packagename.Pixy2CCC;    Might not need this
import packagename.Pixy2Line;
//import packagename.Pixy2Video;  Might not need this

import packagename.Version;
import packageneme.Vector;

public class Pixy2 {
    public Pixy2(SPI.Port port) {
        spi = new SPI(port);
        spi.setClockRate(2000000);
        spi.setClockActiveHigh();
        spi.setChipSelectActiveLow();
        spi.setMSBFirst();
        spi.setClockActiveLow();
        spi.setSampleDataOnTrailingEdge();
        //Is it necessary to validate part ID? (check if there is a Pixy2 at this port?)
        //I do not know what we need to do (implement LiveWindowSendable?) to use LiveWindow, so I will ignore that
    }
    //No calibration needed?
    //?
    //Implement setmode, setdefaultturn, setvector, 
    //Implement parseMainFeatures
    public Pixy2() /*: ccc(this), line(this), video(this)*/ //I don't know what these functions were for
    {
      // allocate buffer space for send/receive
      m_buf = new ByteBuffer[PIXY_SEND_HEADER_SIZE + PIXY_BUFFERSIZE];
      
      // shifted buffer is used for sending, so we have space to write header information
      //m_bufPayload = m_buf + PIXY_SEND_HEADER_SIZE; //Not using this variable
      frameWidth = frameHeight = 0;
      version = null;
    }
    public byte init(long arg=PIXY_DEFAULT_ARGVAL)
    {
      long t0;
      byte res;
      
      res = spi.open(arg);
      if (res<0)
        return res;
      
      // wait for pixy to be ready -- that is, Pixy takes a second or 2 boot up
      // getVersion is an effective "ping".  We timeout after 5s.
      Timer t;
      t.start();
      while (t.get() < 5)
      {
        if ( getVersion() >= 0 ) // successful version get -> pixy is ready
        {
          getResolution(); // get resolution so we have it
          return PIXY_RESULT_OK;
        }	  
        delay(0.005); // delay for sync
      }
      // timeout
      return PIXY_RESULT_TIMEOUT;
    }

    public byte getVersion()
    {
      m_length = 0;
      m_type = PIXY_TYPE_REQUEST_VERSION;
      sendPacket();
      if ( recvPacket() == 0 )
      {   
        if ( m_type == PIXY_TYPE_RESPONSE_VERSION )
        {
          //version = (Version)m_buf; //This might not work at all, because we cannot see changes to m_buf in version
          version = new Version(m_buf);
          return m_length;
        }
        else if (m_type == PIXY_TYPE_RESPONSE_ERROR)
          return PIXY_RESULT_BUSY;
      }
      return PIXY_RESULT_ERROR;  // some kind of bitstream error
    }
    /*public byte changeProg(final char prog) //No idea what this does
    {
      long res;
      
      // poll for program to change
      while(1)
      {
        prog 
        m_length = PIXY_MAX_PROGNAME;
        m_type = PIXY_TYPE_REQUEST_CHANGE_PROG;
        sendPacket();
        if (recvPacket()==0)
        {
          res = *(uint32_t *)m_buf;
          if (res>0) 
        {
            getResolution();  // get resolution so we have it
            return PIXY_RESULT_OK; // success     
          }		
        }
        else
          return PIXY_RESULT_ERROR;  // some kind of bitstream error
        delayMicroseconds(1000); 
      }
    }*/
    public byte setServos(int s0, int s1)
    {
      long res;
      
      m_buf.put(PIXY_SEND_HEADER_SIZE, s0);
      *(int16_t *)(m_bufPayload + 2) = s1;
      m_length = 4;
      m_type = PIXY_TYPE_REQUEST_SERVO;
      sendPacket();
      if (recvPacket()==0 && m_type==PIXY_TYPE_RESPONSE_RESULT && m_length==4)
      {
        res = *(uint32_t *)m_buf;
        return (int8_t)res;	
      }
      else
          return PIXY_RESULT_ERROR;  // some kind of bitstream error	  
    }
    public byte setCameraBrightness(byte brightness)
    {
      long res;
      
      m_buf.put(PIXY_SEND_HEADER_SIZE, brightness);
      m_length = 1;
      m_type = PIXY_TYPE_REQUEST_BRIGHTNESS;
      sendPacket();
      if (recvPacket()==0) // && m_type==PIXY_TYPE_RESPONSE_RESULT && m_length==4)
      {
        res = (long)m_buf.get(0); //Not sure if this is right
        return (byte)res;
      }
      else
          return PIXY_RESULT_ERROR;  // some kind of bitstream error
    }
    public byte setLED(byte r, byte g, byte b)
    {
      long res;
      
      m_buf.put(PIXY_SEND_HEADER_SIZE, r);
      m_buf.put(PIXY_SEND_HEADER_SIZE + 1, g);
      m_buf.put(PIXY_SEND_HEADER_SIZE + 2, b);
      m_length = 3;
      m_type = PIXY_TYPE_REQUEST_LED;
      sendPacket();
      if (recvPacket()==0 && m_type==PIXY_TYPE_RESPONSE_RESULT && m_length==4)
      {
        res = (long)m_buf.get(0);  //Not sure if this is right
        return (byte)res;	
      }
      else
          return PIXY_RESULT_ERROR;  // some kind of bitstream error
    }
    public byte setLamp(byte upper, byte lower)
    {
      long res;
      
      m_buf.put(PIXY_SEND_HEADER_SIZE, upper);
      m_buf.put(PIXY_SEND_HEADER_SIZE + 1, lower);
      m_length = 2;
      m_type = PIXY_TYPE_REQUEST_LAMP;
      sendPacket();
      if (recvPacket()==0 && m_type==PIXY_TYPE_RESPONSE_RESULT && m_length==4)
      {
        res = (long)m_buf;
        return (byte)res;   //Not sure if this is right
      }
      else
          return PIXY_RESULT_ERROR;  // some kind of bitstream error	
    }
    public byte getResolution()
    {
      m_length = 1;
      m_buf.put(PIXY_SEND_HEADER_SIZE, 0); // for future types of queries
      m_type = PIXY_TYPE_REQUEST_RESOLUTION;
      sendPacket();
      if (recvPacket()==0)
      {
        if (m_type==PIXY_TYPE_RESPONSE_RESOLUTION)
        {
          frameWidth = (int)m_buf.get(0);     //Not sure if this is right
          frameHeight = (int)(m_buf.get(1));
          return PIXY_RESULT_OK; // success
        }
        else 
          return PIXY_RESULT_ERROR;
      }
      else
        return PIXY_RESULT_ERROR;  // some kind of bitstream error
    }
    public byte getFPS()
    {
      long res;
      
      m_length = 0; // no args
      m_type = PIXY_TYPE_REQUEST_FPS;
      sendPacket();
      if (recvPacket()==0 && m_type==PIXY_TYPE_RESPONSE_RESULT && m_length==4)
      {
        res = (long)m_buf.get(0); //Not sure if this is right
        return (byte)res;	
      }
      else
          return PIXY_RESULT_ERROR;  // some kind of bitstream error	
    }
    
    public Version version; //Originally a pointer
    public int frameWidth;
    public int frameHeight; 
  
  //PIXY2CCC, PIXY2LINE, AND PIXY2VIDEO WERE FRIEND CLASSES.
  //THEY HAD ACCESS TO PRIVATE MEMBERS.
  // Color connected components, color codes
  //Pixy2CCC ccc;

  // Line following
  public Pixy2Line line;

  // Video
  //Pixy2Video video;
  
  public SPI spi;
  
  private int recv(ByteBuffer buf, int length)
  {
    byte i;
    //digitalWrite(ssPin, LOW); //Shouldn't slave select be part of the transfer method?
    ByteBuffer send;
    send.put(0x00);
    buf.clear();
    spi.transaction(send, buf, 1);//This is probably wrong. Is the endianness wrong? I think it is little endian
    //digitalWrite(ssPin, HIGH);
    return len;
  }

  private int getSync()
  {
    byte i, j, c, cprev;
    int res;
    int start;
    Timer t;
    t.start()
    
    // parse bytes until we find sync
    for(i=j=0, cprev=0; true; i++)
    {
      ByteBuffer response = ByteBuffer.allocate(1);
      response.put(c);
      res = recv(response, 1); //what does this method do?
      c = response.get(0); //This is ugly, sorry, you can change it, it's because this is derived from code that has pointers
      if (res >= PIXY_RESULT_OK)
      {
        //So start is an int, but we are only using 2 bytes of it because java has not uint_16
        //We put cprev in the right byte and c in the left byte
        // since we're using little endian, previous byte is least significant byte
        start = cprev;
        // current byte is most significant byte
        start |= c << 8;
        cprev = c;
        //The result is that as we keep taking more input start will keep storing two bytes of it (current, c, and previous, cprev)
        //Everything from before that is forgotten
        if (start==PIXY_CHECKSUM_SYNC)
        {
          m_cs = true;
          return PIXY_RESULT_OK;
          //The Pixy2 has started sending us useful information
        }
        if (start==PIXY_NO_CHECKSUM_SYNC)
        {
          m_cs = false;
          return PIXY_RESULT_OK;
          //The Pixy2 has started sending us useful information
        }
      }
      // If we've read some bytes and no sync, then wait and try again.
      // And do that several more times before we give up.  
      // Pixy guarantees to respond within 100us.
      if (i>=4)
      {
        if (j>=4)
        {
          if(PIXY_DEBUG)
            Serial.println("error: no response");	  
          return PIXY_RESULT_ERROR;
        }
        t.delay(0.000025); //Is such a small delay allowed in FRC using Timer?
        j++;
        i = 0;
      }
    }
  }
  private int recvPacket()
  {
    int csSerial;
    int res;
    
    res = getSync();
    if (res<0)
      return res;       //Stop the method if getSync was unsuccessful

    if (m_cs)    //I gave up trying to get the checksum functionality to work
    {
      res = recv(m_buf, 4);   //Store data from the pixy in the member var m_buf
      if (res<0)        //Stop the method if recv was unsuccessful
        return res;
  
      m_type = m_buf.get(0);
      m_length = m_buf.get(1);
  
      //csSerial = *(uint16_t *)&m_buf[2]; //This the the original code. Why on earth would you
      //choose to get a pointer, cast, and dereference when you could just cast?
      csSerial = (int) m_buf.get(2);
      res = m_link.recv(m_buf, m_length); // :( This is done stupidly because you can't have pointers in Java.
      if (res<0)
        return res;
  
      if (csSerial != m_buf.position() + 1)
      {
        if (PIXY_DEBUG)
          Serial.println("error: checksum");
        return PIXY_RESULT_CHECKSUM_ERROR;
      }
    }
    else
    {   
      res = recv(m_buf, 2);
      if (res<0)
        return res;
  
      m_type = m_buf[0];
      m_length = m_buf[1];
  
      res = recv(m_buf, m_length);
      if (res<0)
        return res;
    }
    return PIXY_RESULT_OK;
  }
  
  private int sendPacket()
  {
    // write header info at beginnig of buffer
    m_buf.put(0, PIXY_NO_CHECKSUM_SYNC&0xff);
    m_buf.put(1, PIXY_NO_CHECKSUM_SYNC>>8);
    m_buf.put(2, m_type);
    m_buf.put(3, m_length);
    // send whole thing -- header and data in one call
    return m_link.send(m_buf, m_length+PIXY_SEND_HEADER_SIZE);
  }

  private ByteBuffer m_buf;       //originally a pointer, because it was an array of bytes.
  //NOTE THAT THE PAYLOAD STARTS AT m_buf[PIXY_SEND_HEADER_SIZE]
  //private byte[] m_bufPayload;//originally a pointer, because it was an array of bytes.
  //This array of bytes was being used as an array of chars -- a simple string.
  //I will make it an array of bytes of max length
  private byte m_type;
  private byte m_length;
  private bool m_cs;
}

//No destructor in java