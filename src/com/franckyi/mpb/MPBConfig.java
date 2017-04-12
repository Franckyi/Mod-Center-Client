package com.franckyi.mpb;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.ConfigFactory;

public class MPBConfig  {
	
	public static final IMPBConfig CFG = ConfigFactory.create(IMPBConfig.class);
	
	@Sources({"file:./data/mpb.config", "classpath:com/franckyi/mpb/core/data/defaults/mpb.config"})
	public interface IMPBConfig extends Config {
		
		@DefaultValue("true")
		boolean displayModsThumbnail();

	}
	
}
