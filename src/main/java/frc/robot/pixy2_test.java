import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;

SPI spi = new SPI(SPI.Port.kOnboardCS0);
spi.setClockRate(2000000);
spi.setClockActiveHigh();
spi.setChipSelectActiveLow();
spi.setMSBFirst();
spi.setClockActiveLow();
spi.setSampleDataOnTrailingEdge();

byte i, lenReceived, recvBuf[32];
byte versionRequest[] =
{
  0xae,  // first byte of no_checksum_sync (little endian -> least-significant byte first)
  0xc1,  // second byte of no_checksum_sync
  0x0e,  // this is the version request type
  0x00   // data_length is 0
};

// clear out any stale data
while(spi.read(false, recvBuf, 1));

spi.write(versionRequest, 4);   
//delayMillisecond(1); // delay a little so we don't receive too fast (may not be necessary.)  
lenReceived = spi.read(false, recvBuf, 6 + 16); // 6 bytes of header and checksum and 16 bytes of version data

// print result
System.out.println("Received " + lenReceived + " bytes.");
for (i=0; i<lenReceived; i++)
  System.out.println(i + ": " + recvBuf[i]);