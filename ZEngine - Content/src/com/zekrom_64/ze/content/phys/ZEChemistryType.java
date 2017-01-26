package com.zekrom_64.ze.content.phys;

/** Interface for objects used in chemistry.
 * 
 * @author Zekrom_64
 *
 */
public interface ZEChemistryType {

	/** Gets the chemical formula for this type in string form.
	 * 
	 * @return The formula of this type
	 */
	public String getFormula();
	
	/** Returns true if this chemical type is complex, i.e. not consisting of one element.
	 * 
	 * @return If the type is complex
	 */
	public boolean isComplex();
	
}
