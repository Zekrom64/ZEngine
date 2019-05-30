package com.zekrom_64.ze.base.lang;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ZELanguageRegistry extends HashMap<String, String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6715653785959101250L;
	/** English - United States */
	public static final String ENGLISH_US = "en_US";
	/** English - United Kingdom */
	public static final String ENGLISH_BRITAIN = "en_GB";
	/** French - Canada */
	public static final String FRENCH_CANADA = "fr_CA";
	/** French - France */
	public static final String FRENCH_FRANCE = "fr_FR";
	/** Russian - Russia */
	public static final String RUSSIAN_RUSSIA = "ru_RU";
	/** Spanish - Spain */
	public static final String SPANISH_SPAIN = "es_ES";
	/** Spanish - Mexico */
	public static final String SPANISH_MEXICO = "es_MX";
	/** Spanish - Uruguay */
	public static final String SPANISH_URUGUAY = "es_UY";
	/** Spanish - Venezuela */
	public static final String SPANISH_VENEZUELA = "es_VE";
	/** Spanish - Argentina */
	public static final String SPANISH_ARGENTINA = "es_AR";
	/** Hungarian - Hungary */
	public static final String HUNGARIAN_HUNGARY = "hu_HU";
	/** Japanese - Japan */
	public static final String JAPANESE_JAPAN = "ja_JP";
	/** Korean - Korea */
	public static final String KOREAN_KOREA = "ko_KR";
	
	private static final Map<Locale, ZELanguageRegistry> registries = new HashMap<>();
	
	/** Registry for English-US, since it is used most commonly.*/
	public static final ZELanguageRegistry REGISTRY_ENGLISH_US = getRegistry(ENGLISH_US);
	
	/** Attempts to find a registry for a language and creates and registers a new registry if none
	 * is currently found.
	 * 
	 * @param key Language ID
	 * @return The registry for the language
	 */
	public static ZELanguageRegistry getRegistry(String key) {
		String[] vals = key.split("\\_");
		Locale locale = new Locale(vals[0], vals[1]);
		ZELanguageRegistry reg = registries.get(locale);
		if (reg==null) {
			reg = new ZELanguageRegistry(key);
			registries.put(locale, reg);
		}
		return reg;
	}
	
	/** Attempts to find a registry for a locale and creates and registers a new registry if none
	 * is currently found.
	 * 
	 * @param locale Locale
	 * @return The registry for the locale
	 */
	public static ZELanguageRegistry getRegistry(Locale locale) {
		ZELanguageRegistry reg = registries.get(locale);
		if (reg==null) {
			reg = new ZELanguageRegistry(locale);
			registries.put(locale, reg);
		}
		return reg;
	}
	
	/** The language id of the registry. */
	public final String registryId;
	/** The locale of the registry. */
	public final Locale locale;
	
	private ZELanguageRegistry(String key) {
		registryId = key;
		String[] vals = key.split("\\_");
		locale = new Locale(vals[0], vals[1]);
	}
	
	private ZELanguageRegistry(Locale loc) {
		locale = loc;
		registryId = loc.getLanguage() + "_" + loc.getLanguage();
	}
	
	/** Sets the localized name for a piece of content.
	 * 
	 * @param content Content
	 * @param value Localized name
	 * @return The previous localized name, or null
	 */
	public String put(ZEHasUnlocalizedName content, String value) {
		return put(content.getUnlocalizedName(), value);
	}
	
	/** Gets the localized name for a piece of content.
	 * 
	 * @param content Content
	 * @return Localized name, or <b>null</b>
	 */
	public String get(ZEHasUnlocalizedName content) {
		return get(content.getUnlocalizedName());
	}
	
	/** Registers all the values in a set of properties in the language registry.
	 * 
	 * @param lang Language properties
	 */
	public void putAll(Properties lang) {
		Set<Object> keys = lang.keySet();
		for(Object key : keys) {
			if (key instanceof String) {
				String skey = (String)key;
				put(skey, lang.getProperty(skey));
			}
		}
	}
	
	/** Saves all the values in this registry to a Properties object.
	 * 
	 * @param props Properties to save to
	 */
	public void saveToProperties(Properties props) {
		Set<String> keys = this.keySet();
		for(String key : keys) props.setProperty(key, get(key));
	}
}
