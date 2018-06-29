package com.zerofall.progressivealchemy.config;

import com.zerofall.progressivealchemy.proxy.CommonProxy;

import net.minecraftforge.common.config.Configuration;

public class Config {
	
	private static final String CATEGORY_ENABLE = "enable";
	private static final String CATEGORY_LIMITS = "limits";
	
	public static boolean tier1Enabled = true;
	public static boolean tier2Enabled = true;
	public static boolean tier3Enabled = true;
	public static boolean tier4Enabled = true;
	public static boolean tier5Enabled = true;
	public static boolean tier6Enabled = true;
	public static boolean tier7Enabled = true;
	public static boolean tier8Enabled = true;
	
	public static int tier1EmcLimit = 1;
	public static int tier2EmcLimit = 8;
	public static int tier3EmcLimit = 64;
	public static int tier4EmcLimit = 256;
	public static int tier5EmcLimit = 1024;
	public static int tier6EmcLimit = 8192;
	public static int tier7EmcLimit = 262144;
	public static int tier8EmcLimit = 1048576;
	
	public static void readConfig() {
        Configuration cfg = CommonProxy.config;
        try {
            cfg.load();
            initGeneralConfig(cfg);
        } catch (Exception e1) {
            
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }
	
	private static void initGeneralConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_ENABLE, "Enable/Disable Tiers of Condenser");
        tier1Enabled = cfg.getBoolean("tier1Enabled", CATEGORY_ENABLE, tier1Enabled, "Set to false to disable Tier 1");
        tier2Enabled = cfg.getBoolean("tier2Enabled", CATEGORY_ENABLE, tier2Enabled, "Set to false to disable Tier 2");
        tier3Enabled = cfg.getBoolean("tier3Enabled", CATEGORY_ENABLE, tier3Enabled, "Set to false to disable Tier 3");
        tier4Enabled = cfg.getBoolean("tier4Enabled", CATEGORY_ENABLE, tier4Enabled, "Set to false to disable Tier 4");
        tier5Enabled = cfg.getBoolean("tier5Enabled", CATEGORY_ENABLE, tier5Enabled, "Set to false to disable Tier 5");
        tier6Enabled = cfg.getBoolean("tier6Enabled", CATEGORY_ENABLE, tier6Enabled, "Set to false to disable Tier 6");
        tier7Enabled = cfg.getBoolean("tier7Enabled", CATEGORY_ENABLE, tier7Enabled, "Set to false to disable Tier 7");
        tier8Enabled = cfg.getBoolean("tier8Enabled", CATEGORY_ENABLE, tier8Enabled, "Set to false to disable Tier 8");
        
        cfg.addCustomCategoryComment(CATEGORY_LIMITS, "Set EMC Limits for each Tier of Condenser");
        tier1EmcLimit = cfg.getInt("tier1EmcLimit", CATEGORY_LIMITS, tier1EmcLimit, 1, Integer.MAX_VALUE, "EMC Limit for Tier 1");
        tier2EmcLimit = cfg.getInt("tier2EmcLimit", CATEGORY_LIMITS, tier2EmcLimit, 1, Integer.MAX_VALUE, "EMC Limit for Tier 2");
        tier3EmcLimit = cfg.getInt("tier3EmcLimit", CATEGORY_LIMITS, tier3EmcLimit, 1, Integer.MAX_VALUE, "EMC Limit for Tier 3");
        tier4EmcLimit = cfg.getInt("tier4EmcLimit", CATEGORY_LIMITS, tier4EmcLimit, 1, Integer.MAX_VALUE, "EMC Limit for Tier 4");
        tier5EmcLimit = cfg.getInt("tier5EmcLimit", CATEGORY_LIMITS, tier5EmcLimit, 1, Integer.MAX_VALUE, "EMC Limit for Tier 5");
        tier6EmcLimit = cfg.getInt("tier6EmcLimit", CATEGORY_LIMITS, tier6EmcLimit, 1, Integer.MAX_VALUE, "EMC Limit for Tier 6");
        tier7EmcLimit = cfg.getInt("tier7EmcLimit", CATEGORY_LIMITS, tier7EmcLimit, 1, Integer.MAX_VALUE, "EMC Limit for Tier 7");
        tier8EmcLimit = cfg.getInt("tier8EmcLimit", CATEGORY_LIMITS, tier8EmcLimit, 1, Integer.MAX_VALUE, "EMC Limit for Tier 8");
    }
}
