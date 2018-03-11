package com.zekrom_64.ze.base.gui.nuklear;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import org.lwjgl.nuklear.NkQueryFontGlyphCallbackI;
import org.lwjgl.nuklear.NkTextWidthCallbackI;
import org.lwjgl.nuklear.NkUserFont;
import org.lwjgl.nuklear.NkUserFontGlyph;
import org.lwjgl.system.MemoryUtil;

@Deprecated
public class NKUtil {
	
	// FontRenderContext w/ anti-aliasing on and fractional metrics off
	private static final FontRenderContext frc = new FontRenderContext(AffineTransform.getTranslateInstance(0, 0), true, false);

	/** Creates a Nuklear user font from a Java font.
	 * <p><b>Note: This is completely untested</b></p>
	 * 
	 * @param f Java font
	 * @return Nuklear font
	 */
	public static NkUserFont createFont(final Font f) {
		// TODO: Test if this actually works
		NkUserFont font = NkUserFont.create();
		Rectangle2D bounds = f.getMaxCharBounds(frc);
		font.height((float)bounds.getHeight());
		font.width(new NkTextWidthCallbackI() {

			@Override
			public float invoke(long handle, float h, long text, int len) {
				return (float)f.getStringBounds(MemoryUtil.memUTF8(text), frc).getWidth();
			}
			
		});
		font.query(new NkQueryFontGlyphCallbackI() {

			@Override
			public void invoke(long handle, float font_height, long glyph, int codepoint, int next_codepoint) {
				NkUserFontGlyph g = NkUserFontGlyph.create(glyph);
				GlyphVector vec = f.createGlyphVector(frc, new char[] {(char)codepoint});
				Rectangle bounds = vec.getGlyphPixelBounds(0, frc, 0, 0);
				g.offset().set(bounds.y, bounds.y);
			}
			
		});
		return font;
	}
	
}
