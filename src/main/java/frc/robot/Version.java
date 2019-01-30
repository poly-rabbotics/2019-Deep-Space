/**
 * This is translated from a struct in C++. That is why it is weird and public
 */
class Version {
    public Version() {
    }
    public Version(byte[] v) {
      hardware = v[6] | (v[7] << 8);
      firmwareMajor = v[8];
      firmwareMinor = v[9];
      firmwareBuild = v[10] | (v[11] << 8);
      for (int i = 0; i < v.length; i++) {
        firmwareType[i] = v[11+i];
      }
    }
    void print()
    {
      char buf[64];
      sprintf(buf, "hardware ver: " + hardware + "firmware ver:" + firmwareMajor + "." + firmwareMinor + "." + firmwareBuild + " " + firmwareType);
      System.out.println(buf);
    }
    public int hardware;
    public int firmwareMajor;
    public int firmwareMinor;
    public int firmwareBuild;
    public char[] firmwareType = new char[10];  
}