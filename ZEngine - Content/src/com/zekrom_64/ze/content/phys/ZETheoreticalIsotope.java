package com.zekrom_64.ze.content.phys;

/** This class simulates the theoretical infinite number of isotopes elements can have. Although
 * elements can have infinite numbers of isotopes, only a few of them are stable while the others
 * fall apart quickly. These stable forms are listed in the {@link ZEElement} class because they are 
 * the most common and therefore should be stored as constants to save memory. The theoretical
 * isotope class is rarely used, and thus is instantiated when needed.
 * 
 * @author Zekrom_64
 *
 */
public class ZETheoreticalIsotope implements ZEChemistryType {

	/** The base element of this isotope */
	public final ZEElement element;
	/** The isotope number of this element */
	public final int isotope;
	
	/** Creates a new theoretical isotope of an element. The isotope number must be equal to or
	 * greater than the smallest form of the element.
	 * 
	 * @param element Base element
	 * @param isotope Isotope number of the isotope
	 */
	public ZETheoreticalIsotope(ZEElement element, int isotope) {
		if (element==null) throw new NullPointerException("The base element cannot be null");
		if (isotope<0) throw new IllegalArgumentException("The isotope number cannot be less than 0");
		this.element = element;
		this.isotope = isotope;
	}

	@Override
	public String getFormula() {
		return "^" + isotope + element.symbol;
	}

	@Override
	public boolean isComplex() {
		return false;
	}
	
}
