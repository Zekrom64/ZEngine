package com.zekrom_64.ze.content.phys;

public class ZEIsotope extends ZEElement {

	/** The sum of the neutrons and protons in the nucleus, this is 0 for custom isotopes */
	public final int massNumber;
	/** The number of neutrons in the nucleus, this is 0 for custom isotopes */
	public final int neutronNumber;
	/** A generic isotope number to be used for comparison, this may be odd for custom isotopes */
	public final int isotopeNumber;
	
	/** Creates a new isotope form of an element and registers it as an isotope. A new isotope requires
	 * a common isotope to build off of. If none is specified, this is considered the most common isotope.
	 * The symbol parameter is only used when creating a new element, otherwise it is ignored.
	 * 
	 * @param isotopeNumber The isotope number
	 * @param base The common form of the isotope, or null
	 * @param symbol The name of the new isotope, or null
	 */
	protected ZEIsotope(int isotopeNumber, ZEIsotope base, String symbol) {
		super(isotopeNumber, base, symbol);
		this.massNumber = this.neutronNumber = 0;
		this.isotopeNumber = isotopeNumber;
	}
	
	ZEIsotope(int periodicNumber, int isotopeMass, String symbol, boolean isMostCommon) {
		super(periodicNumber, isotopeMass, symbol, isMostCommon);
		this.isotopeNumber = this.massNumber = isotopeMass;
		this.neutronNumber = isotopeMass - periodicNumber;
	}

	@Override
	public String getFormula() {
		return "^" + isotopeNumber + super.getFormula();
	}
	
	public static double predictHalfLife(int neutrons, int protons) {
		// TODO: Implement half life prediction method
		return Double.POSITIVE_INFINITY;
	}
	
	public static double predictHalfLife(ZEIsotope iso) {
		return predictHalfLife(iso.neutronNumber, iso.massNumber - iso.neutronNumber);
	}
	
}
