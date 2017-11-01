/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package com.voole.ad.model;  
@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class AdInfo extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"AdInfo\",\"namespace\":\"com.voole.ad.model\",\"fields\":[{\"name\":\"amid\",\"type\":[\"long\",\"null\"]},{\"name\":\"ven\",\"type\":[\"long\",\"null\"]},{\"name\":\"ip\",\"type\":[\"string\",\"null\"]},{\"name\":\"mac\",\"type\":[\"string\",\"null\"]},{\"name\":\"ts\",\"type\":[\"long\",\"null\"]},{\"name\":\"size\",\"type\":[\"long\",\"null\"]},{\"name\":\"devid\",\"type\":[\"string\",\"null\"]},{\"name\":\"stamp\",\"type\":[\"long\",\"null\"]},{\"name\":\"d\",\"type\":[\"long\",\"null\"]},{\"name\":\"hid\",\"type\":[\"string\",\"null\"]},{\"name\":\"channelid\",\"type\":\"int\",\"default\":-1},{\"name\":\"programid\",\"type\":\"int\",\"default\":-1},{\"name\":\"pos\",\"type\":[\"string\",\"null\"]},{\"name\":\"te\",\"type\":\"int\",\"default\":0},{\"name\":\"provinceid\",\"type\":\"int\",\"default\":0},{\"name\":\"cityid\",\"type\":\"int\",\"default\":0},{\"name\":\"areatype\",\"type\":\"int\",\"default\":0}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public java.lang.Long amid;
  @Deprecated public java.lang.Long ven;
  @Deprecated public java.lang.CharSequence ip;
  @Deprecated public java.lang.CharSequence mac;
  @Deprecated public java.lang.Long ts;
  @Deprecated public java.lang.Long size;
  @Deprecated public java.lang.CharSequence devid;
  @Deprecated public java.lang.Long stamp;
  @Deprecated public java.lang.Long d;
  @Deprecated public java.lang.CharSequence hid;
  @Deprecated public int channelid;
  @Deprecated public int programid;
  @Deprecated public java.lang.CharSequence pos;
  @Deprecated public int te;
  @Deprecated public int provinceid;
  @Deprecated public int cityid;
  @Deprecated public int areatype;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>. 
   */
  public AdInfo() {}

  /**
   * All-args constructor.
   */
  public AdInfo(java.lang.Long amid, java.lang.Long ven, java.lang.CharSequence ip, java.lang.CharSequence mac, java.lang.Long ts, java.lang.Long size, java.lang.CharSequence devid, java.lang.Long stamp, java.lang.Long d, java.lang.CharSequence hid, java.lang.Integer channelid, java.lang.Integer programid, java.lang.CharSequence pos, java.lang.Integer te, java.lang.Integer provinceid, java.lang.Integer cityid, java.lang.Integer areatype) {
    this.amid = amid;
    this.ven = ven;
    this.ip = ip;
    this.mac = mac;
    this.ts = ts;
    this.size = size;
    this.devid = devid;
    this.stamp = stamp;
    this.d = d;
    this.hid = hid;
    this.channelid = channelid;
    this.programid = programid;
    this.pos = pos;
    this.te = te;
    this.provinceid = provinceid;
    this.cityid = cityid;
    this.areatype = areatype;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return amid;
    case 1: return ven;
    case 2: return ip;
    case 3: return mac;
    case 4: return ts;
    case 5: return size;
    case 6: return devid;
    case 7: return stamp;
    case 8: return d;
    case 9: return hid;
    case 10: return channelid;
    case 11: return programid;
    case 12: return pos;
    case 13: return te;
    case 14: return provinceid;
    case 15: return cityid;
    case 16: return areatype;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: amid = (java.lang.Long)value$; break;
    case 1: ven = (java.lang.Long)value$; break;
    case 2: ip = (java.lang.CharSequence)value$; break;
    case 3: mac = (java.lang.CharSequence)value$; break;
    case 4: ts = (java.lang.Long)value$; break;
    case 5: size = (java.lang.Long)value$; break;
    case 6: devid = (java.lang.CharSequence)value$; break;
    case 7: stamp = (java.lang.Long)value$; break;
    case 8: d = (java.lang.Long)value$; break;
    case 9: hid = (java.lang.CharSequence)value$; break;
    case 10: channelid = (java.lang.Integer)value$; break;
    case 11: programid = (java.lang.Integer)value$; break;
    case 12: pos = (java.lang.CharSequence)value$; break;
    case 13: te = (java.lang.Integer)value$; break;
    case 14: provinceid = (java.lang.Integer)value$; break;
    case 15: cityid = (java.lang.Integer)value$; break;
    case 16: areatype = (java.lang.Integer)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'amid' field.
   */
  public java.lang.Long getAmid() {
    return amid;
  }

  /**
   * Sets the value of the 'amid' field.
   * @param value the value to set.
   */
  public void setAmid(java.lang.Long value) {
    this.amid = value;
  }

  /**
   * Gets the value of the 'ven' field.
   */
  public java.lang.Long getVen() {
    return ven;
  }

  /**
   * Sets the value of the 'ven' field.
   * @param value the value to set.
   */
  public void setVen(java.lang.Long value) {
    this.ven = value;
  }

  /**
   * Gets the value of the 'ip' field.
   */
  public java.lang.CharSequence getIp() {
    return ip;
  }

  /**
   * Sets the value of the 'ip' field.
   * @param value the value to set.
   */
  public void setIp(java.lang.CharSequence value) {
    this.ip = value;
  }

  /**
   * Gets the value of the 'mac' field.
   */
  public java.lang.CharSequence getMac() {
    return mac;
  }

  /**
   * Sets the value of the 'mac' field.
   * @param value the value to set.
   */
  public void setMac(java.lang.CharSequence value) {
    this.mac = value;
  }

  /**
   * Gets the value of the 'ts' field.
   */
  public java.lang.Long getTs() {
    return ts;
  }

  /**
   * Sets the value of the 'ts' field.
   * @param value the value to set.
   */
  public void setTs(java.lang.Long value) {
    this.ts = value;
  }

  /**
   * Gets the value of the 'size' field.
   */
  public java.lang.Long getSize() {
    return size;
  }

  /**
   * Sets the value of the 'size' field.
   * @param value the value to set.
   */
  public void setSize(java.lang.Long value) {
    this.size = value;
  }

  /**
   * Gets the value of the 'devid' field.
   */
  public java.lang.CharSequence getDevid() {
    return devid;
  }

  /**
   * Sets the value of the 'devid' field.
   * @param value the value to set.
   */
  public void setDevid(java.lang.CharSequence value) {
    this.devid = value;
  }

  /**
   * Gets the value of the 'stamp' field.
   */
  public java.lang.Long getStamp() {
    return stamp;
  }

  /**
   * Sets the value of the 'stamp' field.
   * @param value the value to set.
   */
  public void setStamp(java.lang.Long value) {
    this.stamp = value;
  }

  /**
   * Gets the value of the 'd' field.
   */
  public java.lang.Long getD() {
    return d;
  }

  /**
   * Sets the value of the 'd' field.
   * @param value the value to set.
   */
  public void setD(java.lang.Long value) {
    this.d = value;
  }

  /**
   * Gets the value of the 'hid' field.
   */
  public java.lang.CharSequence getHid() {
    return hid;
  }

  /**
   * Sets the value of the 'hid' field.
   * @param value the value to set.
   */
  public void setHid(java.lang.CharSequence value) {
    this.hid = value;
  }

  /**
   * Gets the value of the 'channelid' field.
   */
  public java.lang.Integer getChannelid() {
    return channelid;
  }

  /**
   * Sets the value of the 'channelid' field.
   * @param value the value to set.
   */
  public void setChannelid(java.lang.Integer value) {
    this.channelid = value;
  }

  /**
   * Gets the value of the 'programid' field.
   */
  public java.lang.Integer getProgramid() {
    return programid;
  }

  /**
   * Sets the value of the 'programid' field.
   * @param value the value to set.
   */
  public void setProgramid(java.lang.Integer value) {
    this.programid = value;
  }

  /**
   * Gets the value of the 'pos' field.
   */
  public java.lang.CharSequence getPos() {
    return pos;
  }

  /**
   * Sets the value of the 'pos' field.
   * @param value the value to set.
   */
  public void setPos(java.lang.CharSequence value) {
    this.pos = value;
  }

  /**
   * Gets the value of the 'te' field.
   */
  public java.lang.Integer getTe() {
    return te;
  }

  /**
   * Sets the value of the 'te' field.
   * @param value the value to set.
   */
  public void setTe(java.lang.Integer value) {
    this.te = value;
  }

  /**
   * Gets the value of the 'provinceid' field.
   */
  public java.lang.Integer getProvinceid() {
    return provinceid;
  }

  /**
   * Sets the value of the 'provinceid' field.
   * @param value the value to set.
   */
  public void setProvinceid(java.lang.Integer value) {
    this.provinceid = value;
  }

  /**
   * Gets the value of the 'cityid' field.
   */
  public java.lang.Integer getCityid() {
    return cityid;
  }

  /**
   * Sets the value of the 'cityid' field.
   * @param value the value to set.
   */
  public void setCityid(java.lang.Integer value) {
    this.cityid = value;
  }

  /**
   * Gets the value of the 'areatype' field.
   */
  public java.lang.Integer getAreatype() {
    return areatype;
  }

  /**
   * Sets the value of the 'areatype' field.
   * @param value the value to set.
   */
  public void setAreatype(java.lang.Integer value) {
    this.areatype = value;
  }

  /** Creates a new AdInfo RecordBuilder */
  public static com.voole.ad.model.AdInfo.Builder newBuilder() {
    return new com.voole.ad.model.AdInfo.Builder();
  }
  
  /** Creates a new AdInfo RecordBuilder by copying an existing Builder */
  public static com.voole.ad.model.AdInfo.Builder newBuilder(com.voole.ad.model.AdInfo.Builder other) {
    return new com.voole.ad.model.AdInfo.Builder(other);
  }
  
  /** Creates a new AdInfo RecordBuilder by copying an existing AdInfo instance */
  public static com.voole.ad.model.AdInfo.Builder newBuilder(com.voole.ad.model.AdInfo other) {
    return new com.voole.ad.model.AdInfo.Builder(other);
  }
  
  /**
   * RecordBuilder for AdInfo instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<AdInfo>
    implements org.apache.avro.data.RecordBuilder<AdInfo> {

    private java.lang.Long amid;
    private java.lang.Long ven;
    private java.lang.CharSequence ip;
    private java.lang.CharSequence mac;
    private java.lang.Long ts;
    private java.lang.Long size;
    private java.lang.CharSequence devid;
    private java.lang.Long stamp;
    private java.lang.Long d;
    private java.lang.CharSequence hid;
    private int channelid;
    private int programid;
    private java.lang.CharSequence pos;
    private int te;
    private int provinceid;
    private int cityid;
    private int areatype;

    /** Creates a new Builder */
    private Builder() {
      super(com.voole.ad.model.AdInfo.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(com.voole.ad.model.AdInfo.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.amid)) {
        this.amid = data().deepCopy(fields()[0].schema(), other.amid);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.ven)) {
        this.ven = data().deepCopy(fields()[1].schema(), other.ven);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.ip)) {
        this.ip = data().deepCopy(fields()[2].schema(), other.ip);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.mac)) {
        this.mac = data().deepCopy(fields()[3].schema(), other.mac);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.ts)) {
        this.ts = data().deepCopy(fields()[4].schema(), other.ts);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.size)) {
        this.size = data().deepCopy(fields()[5].schema(), other.size);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.devid)) {
        this.devid = data().deepCopy(fields()[6].schema(), other.devid);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.stamp)) {
        this.stamp = data().deepCopy(fields()[7].schema(), other.stamp);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.d)) {
        this.d = data().deepCopy(fields()[8].schema(), other.d);
        fieldSetFlags()[8] = true;
      }
      if (isValidValue(fields()[9], other.hid)) {
        this.hid = data().deepCopy(fields()[9].schema(), other.hid);
        fieldSetFlags()[9] = true;
      }
      if (isValidValue(fields()[10], other.channelid)) {
        this.channelid = data().deepCopy(fields()[10].schema(), other.channelid);
        fieldSetFlags()[10] = true;
      }
      if (isValidValue(fields()[11], other.programid)) {
        this.programid = data().deepCopy(fields()[11].schema(), other.programid);
        fieldSetFlags()[11] = true;
      }
      if (isValidValue(fields()[12], other.pos)) {
        this.pos = data().deepCopy(fields()[12].schema(), other.pos);
        fieldSetFlags()[12] = true;
      }
      if (isValidValue(fields()[13], other.te)) {
        this.te = data().deepCopy(fields()[13].schema(), other.te);
        fieldSetFlags()[13] = true;
      }
      if (isValidValue(fields()[14], other.provinceid)) {
        this.provinceid = data().deepCopy(fields()[14].schema(), other.provinceid);
        fieldSetFlags()[14] = true;
      }
      if (isValidValue(fields()[15], other.cityid)) {
        this.cityid = data().deepCopy(fields()[15].schema(), other.cityid);
        fieldSetFlags()[15] = true;
      }
      if (isValidValue(fields()[16], other.areatype)) {
        this.areatype = data().deepCopy(fields()[16].schema(), other.areatype);
        fieldSetFlags()[16] = true;
      }
    }
    
    /** Creates a Builder by copying an existing AdInfo instance */
    private Builder(com.voole.ad.model.AdInfo other) {
            super(com.voole.ad.model.AdInfo.SCHEMA$);
      if (isValidValue(fields()[0], other.amid)) {
        this.amid = data().deepCopy(fields()[0].schema(), other.amid);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.ven)) {
        this.ven = data().deepCopy(fields()[1].schema(), other.ven);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.ip)) {
        this.ip = data().deepCopy(fields()[2].schema(), other.ip);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.mac)) {
        this.mac = data().deepCopy(fields()[3].schema(), other.mac);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.ts)) {
        this.ts = data().deepCopy(fields()[4].schema(), other.ts);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.size)) {
        this.size = data().deepCopy(fields()[5].schema(), other.size);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.devid)) {
        this.devid = data().deepCopy(fields()[6].schema(), other.devid);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.stamp)) {
        this.stamp = data().deepCopy(fields()[7].schema(), other.stamp);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.d)) {
        this.d = data().deepCopy(fields()[8].schema(), other.d);
        fieldSetFlags()[8] = true;
      }
      if (isValidValue(fields()[9], other.hid)) {
        this.hid = data().deepCopy(fields()[9].schema(), other.hid);
        fieldSetFlags()[9] = true;
      }
      if (isValidValue(fields()[10], other.channelid)) {
        this.channelid = data().deepCopy(fields()[10].schema(), other.channelid);
        fieldSetFlags()[10] = true;
      }
      if (isValidValue(fields()[11], other.programid)) {
        this.programid = data().deepCopy(fields()[11].schema(), other.programid);
        fieldSetFlags()[11] = true;
      }
      if (isValidValue(fields()[12], other.pos)) {
        this.pos = data().deepCopy(fields()[12].schema(), other.pos);
        fieldSetFlags()[12] = true;
      }
      if (isValidValue(fields()[13], other.te)) {
        this.te = data().deepCopy(fields()[13].schema(), other.te);
        fieldSetFlags()[13] = true;
      }
      if (isValidValue(fields()[14], other.provinceid)) {
        this.provinceid = data().deepCopy(fields()[14].schema(), other.provinceid);
        fieldSetFlags()[14] = true;
      }
      if (isValidValue(fields()[15], other.cityid)) {
        this.cityid = data().deepCopy(fields()[15].schema(), other.cityid);
        fieldSetFlags()[15] = true;
      }
      if (isValidValue(fields()[16], other.areatype)) {
        this.areatype = data().deepCopy(fields()[16].schema(), other.areatype);
        fieldSetFlags()[16] = true;
      }
    }

    /** Gets the value of the 'amid' field */
    public java.lang.Long getAmid() {
      return amid;
    }
    
    /** Sets the value of the 'amid' field */
    public com.voole.ad.model.AdInfo.Builder setAmid(java.lang.Long value) {
      validate(fields()[0], value);
      this.amid = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'amid' field has been set */
    public boolean hasAmid() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'amid' field */
    public com.voole.ad.model.AdInfo.Builder clearAmid() {
      amid = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'ven' field */
    public java.lang.Long getVen() {
      return ven;
    }
    
    /** Sets the value of the 'ven' field */
    public com.voole.ad.model.AdInfo.Builder setVen(java.lang.Long value) {
      validate(fields()[1], value);
      this.ven = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'ven' field has been set */
    public boolean hasVen() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'ven' field */
    public com.voole.ad.model.AdInfo.Builder clearVen() {
      ven = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'ip' field */
    public java.lang.CharSequence getIp() {
      return ip;
    }
    
    /** Sets the value of the 'ip' field */
    public com.voole.ad.model.AdInfo.Builder setIp(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.ip = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'ip' field has been set */
    public boolean hasIp() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'ip' field */
    public com.voole.ad.model.AdInfo.Builder clearIp() {
      ip = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'mac' field */
    public java.lang.CharSequence getMac() {
      return mac;
    }
    
    /** Sets the value of the 'mac' field */
    public com.voole.ad.model.AdInfo.Builder setMac(java.lang.CharSequence value) {
      validate(fields()[3], value);
      this.mac = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'mac' field has been set */
    public boolean hasMac() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'mac' field */
    public com.voole.ad.model.AdInfo.Builder clearMac() {
      mac = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /** Gets the value of the 'ts' field */
    public java.lang.Long getTs() {
      return ts;
    }
    
    /** Sets the value of the 'ts' field */
    public com.voole.ad.model.AdInfo.Builder setTs(java.lang.Long value) {
      validate(fields()[4], value);
      this.ts = value;
      fieldSetFlags()[4] = true;
      return this; 
    }
    
    /** Checks whether the 'ts' field has been set */
    public boolean hasTs() {
      return fieldSetFlags()[4];
    }
    
    /** Clears the value of the 'ts' field */
    public com.voole.ad.model.AdInfo.Builder clearTs() {
      ts = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /** Gets the value of the 'size' field */
    public java.lang.Long getSize() {
      return size;
    }
    
    /** Sets the value of the 'size' field */
    public com.voole.ad.model.AdInfo.Builder setSize(java.lang.Long value) {
      validate(fields()[5], value);
      this.size = value;
      fieldSetFlags()[5] = true;
      return this; 
    }
    
    /** Checks whether the 'size' field has been set */
    public boolean hasSize() {
      return fieldSetFlags()[5];
    }
    
    /** Clears the value of the 'size' field */
    public com.voole.ad.model.AdInfo.Builder clearSize() {
      size = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /** Gets the value of the 'devid' field */
    public java.lang.CharSequence getDevid() {
      return devid;
    }
    
    /** Sets the value of the 'devid' field */
    public com.voole.ad.model.AdInfo.Builder setDevid(java.lang.CharSequence value) {
      validate(fields()[6], value);
      this.devid = value;
      fieldSetFlags()[6] = true;
      return this; 
    }
    
    /** Checks whether the 'devid' field has been set */
    public boolean hasDevid() {
      return fieldSetFlags()[6];
    }
    
    /** Clears the value of the 'devid' field */
    public com.voole.ad.model.AdInfo.Builder clearDevid() {
      devid = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    /** Gets the value of the 'stamp' field */
    public java.lang.Long getStamp() {
      return stamp;
    }
    
    /** Sets the value of the 'stamp' field */
    public com.voole.ad.model.AdInfo.Builder setStamp(java.lang.Long value) {
      validate(fields()[7], value);
      this.stamp = value;
      fieldSetFlags()[7] = true;
      return this; 
    }
    
    /** Checks whether the 'stamp' field has been set */
    public boolean hasStamp() {
      return fieldSetFlags()[7];
    }
    
    /** Clears the value of the 'stamp' field */
    public com.voole.ad.model.AdInfo.Builder clearStamp() {
      stamp = null;
      fieldSetFlags()[7] = false;
      return this;
    }

    /** Gets the value of the 'd' field */
    public java.lang.Long getD() {
      return d;
    }
    
    /** Sets the value of the 'd' field */
    public com.voole.ad.model.AdInfo.Builder setD(java.lang.Long value) {
      validate(fields()[8], value);
      this.d = value;
      fieldSetFlags()[8] = true;
      return this; 
    }
    
    /** Checks whether the 'd' field has been set */
    public boolean hasD() {
      return fieldSetFlags()[8];
    }
    
    /** Clears the value of the 'd' field */
    public com.voole.ad.model.AdInfo.Builder clearD() {
      d = null;
      fieldSetFlags()[8] = false;
      return this;
    }

    /** Gets the value of the 'hid' field */
    public java.lang.CharSequence getHid() {
      return hid;
    }
    
    /** Sets the value of the 'hid' field */
    public com.voole.ad.model.AdInfo.Builder setHid(java.lang.CharSequence value) {
      validate(fields()[9], value);
      this.hid = value;
      fieldSetFlags()[9] = true;
      return this; 
    }
    
    /** Checks whether the 'hid' field has been set */
    public boolean hasHid() {
      return fieldSetFlags()[9];
    }
    
    /** Clears the value of the 'hid' field */
    public com.voole.ad.model.AdInfo.Builder clearHid() {
      hid = null;
      fieldSetFlags()[9] = false;
      return this;
    }

    /** Gets the value of the 'channelid' field */
    public java.lang.Integer getChannelid() {
      return channelid;
    }
    
    /** Sets the value of the 'channelid' field */
    public com.voole.ad.model.AdInfo.Builder setChannelid(int value) {
      validate(fields()[10], value);
      this.channelid = value;
      fieldSetFlags()[10] = true;
      return this; 
    }
    
    /** Checks whether the 'channelid' field has been set */
    public boolean hasChannelid() {
      return fieldSetFlags()[10];
    }
    
    /** Clears the value of the 'channelid' field */
    public com.voole.ad.model.AdInfo.Builder clearChannelid() {
      fieldSetFlags()[10] = false;
      return this;
    }

    /** Gets the value of the 'programid' field */
    public java.lang.Integer getProgramid() {
      return programid;
    }
    
    /** Sets the value of the 'programid' field */
    public com.voole.ad.model.AdInfo.Builder setProgramid(int value) {
      validate(fields()[11], value);
      this.programid = value;
      fieldSetFlags()[11] = true;
      return this; 
    }
    
    /** Checks whether the 'programid' field has been set */
    public boolean hasProgramid() {
      return fieldSetFlags()[11];
    }
    
    /** Clears the value of the 'programid' field */
    public com.voole.ad.model.AdInfo.Builder clearProgramid() {
      fieldSetFlags()[11] = false;
      return this;
    }

    /** Gets the value of the 'pos' field */
    public java.lang.CharSequence getPos() {
      return pos;
    }
    
    /** Sets the value of the 'pos' field */
    public com.voole.ad.model.AdInfo.Builder setPos(java.lang.CharSequence value) {
      validate(fields()[12], value);
      this.pos = value;
      fieldSetFlags()[12] = true;
      return this; 
    }
    
    /** Checks whether the 'pos' field has been set */
    public boolean hasPos() {
      return fieldSetFlags()[12];
    }
    
    /** Clears the value of the 'pos' field */
    public com.voole.ad.model.AdInfo.Builder clearPos() {
      pos = null;
      fieldSetFlags()[12] = false;
      return this;
    }

    /** Gets the value of the 'te' field */
    public java.lang.Integer getTe() {
      return te;
    }
    
    /** Sets the value of the 'te' field */
    public com.voole.ad.model.AdInfo.Builder setTe(int value) {
      validate(fields()[13], value);
      this.te = value;
      fieldSetFlags()[13] = true;
      return this; 
    }
    
    /** Checks whether the 'te' field has been set */
    public boolean hasTe() {
      return fieldSetFlags()[13];
    }
    
    /** Clears the value of the 'te' field */
    public com.voole.ad.model.AdInfo.Builder clearTe() {
      fieldSetFlags()[13] = false;
      return this;
    }

    /** Gets the value of the 'provinceid' field */
    public java.lang.Integer getProvinceid() {
      return provinceid;
    }
    
    /** Sets the value of the 'provinceid' field */
    public com.voole.ad.model.AdInfo.Builder setProvinceid(int value) {
      validate(fields()[14], value);
      this.provinceid = value;
      fieldSetFlags()[14] = true;
      return this; 
    }
    
    /** Checks whether the 'provinceid' field has been set */
    public boolean hasProvinceid() {
      return fieldSetFlags()[14];
    }
    
    /** Clears the value of the 'provinceid' field */
    public com.voole.ad.model.AdInfo.Builder clearProvinceid() {
      fieldSetFlags()[14] = false;
      return this;
    }

    /** Gets the value of the 'cityid' field */
    public java.lang.Integer getCityid() {
      return cityid;
    }
    
    /** Sets the value of the 'cityid' field */
    public com.voole.ad.model.AdInfo.Builder setCityid(int value) {
      validate(fields()[15], value);
      this.cityid = value;
      fieldSetFlags()[15] = true;
      return this; 
    }
    
    /** Checks whether the 'cityid' field has been set */
    public boolean hasCityid() {
      return fieldSetFlags()[15];
    }
    
    /** Clears the value of the 'cityid' field */
    public com.voole.ad.model.AdInfo.Builder clearCityid() {
      fieldSetFlags()[15] = false;
      return this;
    }

    /** Gets the value of the 'areatype' field */
    public java.lang.Integer getAreatype() {
      return areatype;
    }
    
    /** Sets the value of the 'areatype' field */
    public com.voole.ad.model.AdInfo.Builder setAreatype(int value) {
      validate(fields()[16], value);
      this.areatype = value;
      fieldSetFlags()[16] = true;
      return this; 
    }
    
    /** Checks whether the 'areatype' field has been set */
    public boolean hasAreatype() {
      return fieldSetFlags()[16];
    }
    
    /** Clears the value of the 'areatype' field */
    public com.voole.ad.model.AdInfo.Builder clearAreatype() {
      fieldSetFlags()[16] = false;
      return this;
    }

    @Override
    public AdInfo build() {
      try {
        AdInfo record = new AdInfo();
        record.amid = fieldSetFlags()[0] ? this.amid : (java.lang.Long) defaultValue(fields()[0]);
        record.ven = fieldSetFlags()[1] ? this.ven : (java.lang.Long) defaultValue(fields()[1]);
        record.ip = fieldSetFlags()[2] ? this.ip : (java.lang.CharSequence) defaultValue(fields()[2]);
        record.mac = fieldSetFlags()[3] ? this.mac : (java.lang.CharSequence) defaultValue(fields()[3]);
        record.ts = fieldSetFlags()[4] ? this.ts : (java.lang.Long) defaultValue(fields()[4]);
        record.size = fieldSetFlags()[5] ? this.size : (java.lang.Long) defaultValue(fields()[5]);
        record.devid = fieldSetFlags()[6] ? this.devid : (java.lang.CharSequence) defaultValue(fields()[6]);
        record.stamp = fieldSetFlags()[7] ? this.stamp : (java.lang.Long) defaultValue(fields()[7]);
        record.d = fieldSetFlags()[8] ? this.d : (java.lang.Long) defaultValue(fields()[8]);
        record.hid = fieldSetFlags()[9] ? this.hid : (java.lang.CharSequence) defaultValue(fields()[9]);
        record.channelid = fieldSetFlags()[10] ? this.channelid : (java.lang.Integer) defaultValue(fields()[10]);
        record.programid = fieldSetFlags()[11] ? this.programid : (java.lang.Integer) defaultValue(fields()[11]);
        record.pos = fieldSetFlags()[12] ? this.pos : (java.lang.CharSequence) defaultValue(fields()[12]);
        record.te = fieldSetFlags()[13] ? this.te : (java.lang.Integer) defaultValue(fields()[13]);
        record.provinceid = fieldSetFlags()[14] ? this.provinceid : (java.lang.Integer) defaultValue(fields()[14]);
        record.cityid = fieldSetFlags()[15] ? this.cityid : (java.lang.Integer) defaultValue(fields()[15]);
        record.areatype = fieldSetFlags()[16] ? this.areatype : (java.lang.Integer) defaultValue(fields()[16]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
