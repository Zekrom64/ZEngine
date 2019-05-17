package com.zekrom_64.ze.base.gui;

import java.awt.Dimension;

import com.zekrom_64.mathlib.tuple.impl.Vector2I;

public interface ZELayoutEngine {

	public static interface ZELayoutNode {
		
		public void setPosition(int x, int y);
		
		public void getPosition(Vector2I pos);
		
	}
	
	public void mapChild(ZELayoutNode parent, ZELayoutNode child);

	public void unmapChild(ZELayoutNode parent, ZELayoutNode child);
	
	public ZELayoutNode[] getChildren(ZELayoutNode node);
	
	public ZELayoutNode getParent(ZELayoutNode node);
	
	/** A layout direction describes the axis that will be preferred
	 * for aligning items during layout.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public enum LayoutDirection {
		VERTICAL,
		HORIZONTAL
	}
	
	public void setLayoutDirection(ZELayoutNode node, LayoutDirection dir);
	
	public LayoutDirection getLayoutDirection(ZELayoutNode node);
	
	/** A justify describes how items will be spaced from the beginning of
	 * the layout direction (top for vertical, left for horizontal).
	 * 
	 * @author Zekrom_64
	 *
	 */
	public enum LayoutJustify {
		/** Justifies content at the start of the direction */
		START,
		/** Justifies content in the center of the direction */
		CENTER,
		/** Justifies content at the end of the direction */
		END,
		/** Justifies content with equal spacing between elements, with
		 * no added spacing relative to the start and end */
		SPACE_BETWEEN,
		/** Justifies content with equal spacing around elements towards
		 * both the start and end. */
		SPACE_AROUND
	}
	
	public void setContentJustify(ZELayoutNode node, LayoutJustify justify);
	
	public LayoutJustify getContentJustify(ZELayoutNode node);
	
	public void setWraparound(ZELayoutNode node, boolean wraparound);
	
	public boolean getWraparound(ZELayoutNode node);
	
	/** An alignment describes how items will be positioned relative to
	 * the axis opposite that of their layout direction.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public enum LayoutAlignment {
		/** Items will be stretched to match the size of the container
		 * in the opposite axis. */
		STRETCH,
		/** Items will align to the start of the opposing axis */
		START,
		/** Items will align to the end of the opposing axis */
		END,
		/** Items will align to the center of the opposing axis */
		CENTER
	}
	
	/** Sets the alignment of child nodes in this node.
	 * 
	 * @param node Node
	 * @param align Child alignment
	 */
	public void setAlignChildren(ZELayoutNode node, LayoutAlignment align);
	
	/** Gets the alignment of child nodes in this node.
	 * 
	 * @param node
	 * @return
	 */
	public LayoutAlignment getAlignChildren(ZELayoutNode node);
	
	public void setAlignSelf(ZELayoutNode node, LayoutAlignment align);
	
	public LayoutAlignment getAlignSelf(ZELayoutNode node);
	
	/** Content alignment allows for more properties than {@link LayoutAlignment}.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public enum LayoutContentAlignment {
		START,
		END,
		CENTER,
		STRETCH,
		SPACE_BETWEEN,
		SPACE_AROUND
	}
	
	public void setAlignContent(ZELayoutNode node, LayoutContentAlignment align);
	
	public LayoutContentAlignment getAlignContent(ZELayoutNode node);
	
	public void setSpacingRatio(ZELayoutNode node, double ratio);
	
	public double getSpacingRatio(ZELayoutNode node);
	
	public void setAbsolute(ZELayoutNode node, boolean absolute);
	
	public boolean getAbsolute(ZELayoutNode node);
	
	public enum LayoutAbsoluteOffset {
		LEFT,
		TOP,
		RIGHT,
		BOTTOM,
		START,
		END
	}
	
	public void setAbsoluteOffset(ZELayoutNode node, LayoutAbsoluteOffset offset, double value);
	
	public double getAbsoluteOffset(ZELayoutNode node, LayoutAbsoluteOffset offset);
	
	public void setMaxSize(ZELayoutNode node, Dimension size);
	
	public Dimension getMaxSize(ZELayoutNode node);
	
}
