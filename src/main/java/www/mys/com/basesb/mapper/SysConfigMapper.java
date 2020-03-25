package www.mys.com.basesb.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import www.mys.com.basesb.pojo.SysConfig;

@Repository("sysConfigMapper")
public interface SysConfigMapper extends JpaRepository<SysConfig, Integer> {

    public SysConfig getByConfigKey(String configKey);

}
