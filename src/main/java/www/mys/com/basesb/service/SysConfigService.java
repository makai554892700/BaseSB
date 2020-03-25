package www.mys.com.basesb.service;

import www.mys.com.basesb.pojo.SysConfig;

public interface SysConfigService {

    public SysConfig saveSysConfig(String key, String value);

    public SysConfig getSysConfigByKey(String key);

    public boolean getValueByKey(String key, boolean defaultValue);

    public int getValueByKey(String key, int defaultValue);

    public float getValueByKey(String key, float defaultValue);

    public double getValueByKey(String key, double defaultValue);

    public long getValueByKey(String key, long defaultValue);

    public String getValueByKey(String key, String defaultValue);

}
