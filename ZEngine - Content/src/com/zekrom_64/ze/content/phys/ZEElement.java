package com.zekrom_64.ze.content.phys;

import java.util.ArrayList;
import java.util.Arrays;

import com.zekrom_64.ze.base.err.ZEngineInternalException;

/** An element abstracts the different isotopes for elements and allows basic chemistry to be performed. Every
 * Element object can be cast to an Isotope.
 * 
 * @author Zekrom_64
 *
 */
public abstract class ZEElement implements ZEChemistryType {

	private static ZEIsotope[] commonElements = new ZEIsotope[128];
	private static ZEIsotope[] customElements = new ZEIsotope[128];
	private static ArrayList<ZEIsotope> commonIsotopes = new ArrayList<>();
	private static ArrayList<ZEIsotope> customIsotopes = new ArrayList<>();
	private static int nextCustomElement = 1;
	
	public static ZEElement byId(int id) {
		if (id==0) return null;
		if (id>0) return commonElements[id-1];
		else {
			int customId = (~id);
			if (customId>customElements.length) return null;
			return customElements[customId-1];
		}
	}
	
	public static ZEElement bySymbol(String symbol) {
		if (symbol==null) return null;
		int isotopeMass = 0;
		// The symbol has a preceding isotope number
		if (symbol.startsWith("^")) {
			char[] chars = symbol.toCharArray();
			chars = Arrays.copyOfRange(chars, 1, chars.length);
			int index = 1;
			StringBuffer massbuf = new StringBuffer();
			// Copy the number into a StringBuffer
			for(;index < chars.length; index++) {
				char c = chars[index];
				if (!Character.isDigit(c)) break;
				else massbuf.append(c);
			}
			// Attempt to parse the isotope number, else return null
			try {
				isotopeMass = Integer.parseInt(massbuf.toString());
			} catch (NumberFormatException e) {
				return null;
			}
			// Remove the isotope prefix
			symbol = symbol.substring(index);
		}
		if (isotopeMass>0) {
			for (ZEIsotope i : commonIsotopes) {
				if (i.isotopeNumber==isotopeMass&&symbol.equals(i.symbol)) return i;
			}
		}
		for (ZEElement e : commonElements) {
			if (symbol.equals(e.symbol)) return e;
		}
		if (isotopeMass>0) {
			for (ZEIsotope i : customIsotopes) {
				if (i.isotopeNumber==isotopeMass&&symbol.equals(i.symbol)) return i;
			}
		}
		for (ZEElement e : customElements) {
			if (symbol.equals(e.symbol)) return e;
		}
		return null;
	}
	
	// Periodic elements, there are technically infinite numbers of these, but these are the only (relatively) stable
	public static final ZEIsotope HYDROGEN_1 = new ZEIsotope(1, 1, "H", true);
	public static final ZEIsotope HYDROGEN_2 = new ZEIsotope(1, 2, "H", false);
	public static final ZEIsotope HYDROGEN_3 = new ZEIsotope(1, 3, "H", false);
	public static final ZEIsotope HELIUM_2 = new ZEIsotope(2, 2, "He", false);
	public static final ZEIsotope HELIUM_3 = new ZEIsotope(2, 3, "He", false);
	public static final ZEIsotope HELIUM_4 = new ZEIsotope(2, 4, "He", true);
	public static final ZEIsotope LITHIUM_6 = new ZEIsotope(3, 6, "Li", false);
	public static final ZEIsotope LITHIUM_7 = new ZEIsotope(3, 7, "Li", true);
	public static final ZEIsotope BERYLLIUM_9 = new ZEIsotope(4, 9, "Be", true);
	public static final ZEIsotope BERYLLIUM_10 = new ZEIsotope(4, 10, "Be", false);
	public static final ZEIsotope BORON_10 = new ZEIsotope(5, 10, "B", false);
	public static final ZEIsotope BORON_11 = new ZEIsotope(5, 11, "B", true);
	public static final ZEIsotope CARBON_12 = new ZEIsotope(6, 12, "C", true);
	public static final ZEIsotope CARBON_13 = new ZEIsotope(6, 13, "C", false);
	public static final ZEIsotope CARBON_14 = new ZEIsotope(6, 14, "C", false);
	public static final ZEIsotope NITROGEN_14 = new ZEIsotope(7, 14, "N", true);
	public static final ZEIsotope NITROGEN_15 = new ZEIsotope(7, 15, "N", false);
	public static final ZEIsotope OXYGEN_16 = new ZEIsotope(8, 16, "O", true);
	public static final ZEIsotope OXYGEN_17 = new ZEIsotope(8, 17, "O", false);
	public static final ZEIsotope OXYGEN_18 = new ZEIsotope(8, 18, "O", false);
	public static final ZEIsotope FLUORINE_19 = new ZEIsotope(9, 19, "F", true);
	public static final ZEIsotope NEON_20 = new ZEIsotope(10, 20, "Ne", true);
	public static final ZEIsotope NEON_21 = new ZEIsotope(10, 21, "Ne", false);
	public static final ZEIsotope NEON_22 = new ZEIsotope(10, 22, "Ne", false);
	public static final ZEIsotope SODIUM_23 = new ZEIsotope(11, 23, "Na", true);
	public static final ZEIsotope MAGNESIUM_24 = new ZEIsotope(12, 24, "Mg", true);
	public static final ZEIsotope MAGNESIUM_25 = new ZEIsotope(12, 25, "Mg", false);
	public static final ZEIsotope MAGNESIUM_26 = new ZEIsotope(12, 26, "Mg", false);
	public static final ZEIsotope ALUMINUM_26 = new ZEIsotope(13, 26, "Al", false);
	public static final ZEIsotope ALUMINUM_27 = new ZEIsotope(13, 27, "Al", true);
	public static final ZEIsotope SILICON_28 = new ZEIsotope(14, 28, "Si", true);
	public static final ZEIsotope SILICON_29 = new ZEIsotope(14, 29, "Si", false);
	public static final ZEIsotope SILICON_30 = new ZEIsotope(14, 30, "Si", false);
	public static final ZEIsotope PHOSPHORUS_31 = new ZEIsotope(15, 31, "P", true);
	public static final ZEIsotope SULFUR_32 = new ZEIsotope(16, 32, "S", true);
	public static final ZEIsotope SULFUR_33 = new ZEIsotope(16, 33, "S", false);
	public static final ZEIsotope SULFUR_34 = new ZEIsotope(16, 34, "S", false);
	public static final ZEIsotope CHLORINE_35 = new ZEIsotope(17, 35, "Cl", true);
	public static final ZEIsotope CHLORINE_36 = new ZEIsotope(17, 36, "Cl", false);
	public static final ZEIsotope CHLORINE_37 = new ZEIsotope(17, 37, "Cl", false);
	public static final ZEIsotope ARGON_36 = new ZEIsotope(18, 36, "Ar", false);
	public static final ZEIsotope ARGON_38 = new ZEIsotope(18, 38, "Ar", false);
	public static final ZEIsotope ARGON_40 = new ZEIsotope(18, 40, "Ar", true);
	
	// Common elements
	public static final ZEIsotope HYDROGEN = HYDROGEN_1;
	public static final ZEIsotope HELIUM = HELIUM_4;
	public static final ZEIsotope LITHIUM = LITHIUM_7;
	public static final ZEIsotope BERYLLIUM = BERYLLIUM_9;
	public static final ZEIsotope BORON = BORON_11;
	public static final ZEIsotope CARBON = CARBON_12;
	public static final ZEIsotope NITROGEN = NITROGEN_14;
	public static final ZEIsotope OXYGEN = OXYGEN_16;
	public static final ZEIsotope FLUORINE = FLUORINE_19;
	public static final ZEIsotope NEON = NEON_20;
	public static final ZEIsotope SODIUM = SODIUM_23;
	public static final ZEIsotope MAGNESIUM = MAGNESIUM_24;
	public static final ZEIsotope ALUMINUM = ALUMINUM_27;
	
	// Well-known names of elements
	public static final ZEIsotope DEUTERIUM = HYDROGEN_2;
	public static final ZEIsotope TRITIUM = HYDROGEN_3;
	
	public final String symbol;
	public final int id;
	public final boolean isCommonForm;
	
	// This is used by the Isotope class for registering periodic elements
	ZEElement(int periodicNumber, int isotopeMass, String symbol, boolean isMostCommon) {
		id = periodicNumber;
		isCommonForm = isMostCommon;
		this.symbol = symbol;
		if (isMostCommon) commonElements[id] = (ZEIsotope)this;
		else commonIsotopes.add((ZEIsotope)this);
	}
	
	// This is used by the Isotope class for registering new custom isotopes
	ZEElement(int isotopeNumber, ZEIsotope base, String symbol) {
		if (base==null) {
			if (nextCustomElement>=0xFFFF) ZEngineInternalException.throwInternallyNoExcept("Out of element ids");
			isCommonForm = true;
			id = nextCustomElement++;
			customElements[id] = (ZEIsotope)this;
		} else {
			isCommonForm = base.isCommonForm;
			id = base.id;
			customIsotopes.add((ZEIsotope)this);
		}
		this.symbol = symbol;
	}

	@Override
	public String getFormula() {
		return symbol;
	}

	@Override
	public boolean isComplex() {
		return false;
	}
	
}
