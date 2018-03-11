package com.zekrom_64.ze.se.files;

import javax.vecmath.Vector3f;

public class SEBSPFile {
	
	public class SEBSPFilePlane {
		
		public final Vector3f normal = new Vector3f();
		public float dist;
		public int type;
		
	}
	
	public class SEBSPFilePlaneLump {
		
		public SEBSPFilePlane[] planes;
		
	}
	
	public class SEBSPFileVertexLump {
		
		// Each vertex is 3 floats, tightly packed in the array
		public float[] vertexArray;
		
	}
	
	public class SEBSPFileEdgeLump {
		
		// Each edge is a pair of vertex indices, tightly packed
		public short[] edgeArray;
		
	}
	
	public class SEBSPFileSurfedgeLump {
		
		// Each surfedge is a pair of vertex indices, tightly packed
		public int[] surfedgeArray;
		
	}
	
	public class SEBSPFileFace {
		
		// Unsigned
		public short planenum;
		public byte side;
		public boolean onNode;
		public int firstedge;
		public short numedges;
		public short texinfo;
		public short dispinfo;
		public short surfaceFogVolumeID;
		public final byte[] styles = new byte[4];
		public int lightofs;
		public float area;
		public final int[] lightmapTextureMinsInLuxels = new int[2];
		public final int[] lightmapTextureSizeInLuxels = new int[2];
		public int origFace;
		// Unsigned
		public short numPrims;
		// Unsigned
		public short firstPrimID;
		// Unsigned
		public int smoothingGroups;
		
	}
	
	public class SEBSPFileFaceLump {
		
		public SEBSPFileFace[] faces;
		
	}
	
	public class SEBSPFileOriginalFaceLump {
		
		public SEBSPFileFace[] originalFaces;
		
	}
	
	public class SEBSPFileBrush {
		
		public int firstside;
		public int numsides;
		public int contents;
		
	}
	
	public class SEBSPFileBrushLump {
		
		public SEBSPFileBrush[] brushes;
		
	}
	
	public enum SEBSPBrushContents {
		CONTENTS_EMPTY,
		CONTENTS_SOLID,
		CONTENTS_WINDOW,
		CONTENTS_AUX,
		CONTENTS_GRATE,
		CONTENTS_SLIME,
		CONTENTS_WATER,
		CONTENTS_MIST,
		CONTENTS_OPAQUE,
		CONTENTS_TESTFOGVOLUME,
		CONTENTS_UNUSED,
		CONTENTS_UNUSED6,
		CONTENTS_TEAM1,
		CONTENTS_TEAM2,
		CONTENTS_IGNORE_NODRAW_OPAQUE,
		CONTENTS_MOVEABLE,
		CONTENTS_AREAPORTAL,
		CONTENTS_PLAYERCLIP,
		CONTENTS_MONSTERCLIP,
		CONTENTS_CURRENT_0,
		CONTENTS_CURRENT_90,
		CONTENTS_CURRENT_180,
		CONTENTS_CURRENT_270,
		CONTENTS_CURRENT_UP,
		CONTENTS_CURRENT_DOWN,
		CONTENTS_ORIGIN,
		CONTENTS_MONSTER,
		CONTENTS_DEBRIS,
		CONTENTS_DETAIL,
		CONTENTS_TRANSLUCENT,
		CONTENTS_LADDER,
		CONTENTS_HITBOX;
		
		public final int value;
		
		private SEBSPBrushContents() {
			value = ordinal() == 0 ? 0 : 1 << (ordinal() - 1);
		}
	}
	
	public class SEBSPFileBrushSide {
		
		// Unsigned
		public short planenum;
		public short texinfo;
		public short dispinfo;
		public short bevel;
		
	}
	
	public class SEBSPFileBrushSideLump {
		
		public SEBSPFileBrushSide[] brushSides;
		
	}
	
	public class SEBSPFileNode {
		
		public int planenum;
		public final int[] children = new int[2];
		public final short[] mins = new short[3];
		public final short[] maxs = new short[3];
		// Unsigned
		public short fistface;
		// Unsigned
		public short numfaces;
		public short area;
		public short padding;
		
	}
	
	public class SEBSPFileNodeLump {
		
		public SEBSPFileNode[] nodes;
		
	}
	
	public class SEBSPFileLeaf {
		
		public int contents;
		public short cluster;
		// Area and flags in bitfields
		public final short[] mins = new short[3];
		public final short[] maxs = new short[3];
		// Unsigned
		public short firstleafface;
		// Unsigned
		public short numleaffaces;
		// Unsigned
		public short firstleafbrush;
		// Unsigned
		public short numleafbrushes;
		public short leafWaterDataID;
		
	}
	
	public class SEBSPFileLeafLump {
		
		public SEBSPFileLeaf[] leaves;
		
	}
	
	public class SEBSPFileLeafFaceLump {
		
		// Unsigned
		public short[] faceMap;
		
	}
	
	public class SEBSPFileLeafBrushLump {
		
		// Unsigned
		public short[] brushMap;
		
	}
	
	public enum SEBSPFileTextureFlags {
		SURF_LIGHT(0x0001),
		SURF_SKY2D(0x0002),
		SURF_SKY(0x0004),
		SURF_WARP(0x0008),
		SURF_TRANS(0x0010),
		SURF_NOPORTAL(0x0020),
		SURF_TRIGGER(0x0040),
		SURF_NODRAW(0x0080),
		SURF_HINT(0x0100),
		SURF_SKIP(0x0200),
		SURF_NOLIGHT(0x0400),
		SURF_BUMPLIGHT(0x0800),
		SURF_NOSHADOWS(0x1000),
		SURF_NODECALS(0x2000),
		SURF_NOCHOP(0x4000),
		SURF_HITBOX(0x8000);
		
		public final int value;
		
		private SEBSPFileTextureFlags(int val) {
			value = val;
		}
	}
	
	public class SEBSPFileTextureInfoLump {
		
		public final float[][] textureVecs = new float[2][4];
		public final float[][] lightmapVecs = new float[2][4];
		public int flags;
		public int texdata;
		
	}
	
	public class SEBSPFileTextureDataEntry {
		
		public Vector3f reflectivity = new Vector3f();
		public int nameStringTableID;
		public int width, height;
		public int view_width, view_height;
		
	}
	
	public class SEBSPFileTextureDataLump {
		
		public SEBSPFileTextureDataEntry[] entries;
		
	}
	
	public class SEBSPFileTextureStringTable {
	
		public int[] offsets;
		
	}
	
	public class SEBSPFileTextureStringData {
		
		public String[] names;
		
	}
	
	public class ZEBSPFileModelLump {
		
		public Vector3f mins = new Vector3f(), maxs = new Vector3f();
		public float[] origin = new float[3];
		public int headnode;
		public int firstface, numfaces;
		
	}
	
	public class SEBSPFileVisibilityLump {
		
		// TODO: Figure out what this structure actually is
		
	}
	
	public class SEBSPFileEntityLump {
		
		public String entityText;
		
	}
	
	public class SEBSPFileGameLumpEntry {
		
		public int id;
		// Unsigned
		public short flags;
		// Unsigned
		public short version;
		public int fileofs;
		public int filelen;
		
	}
	
	public class SEBSPFileGameLump {
		
		public SEBSPFileGameLumpEntry[] entries;
		
	}
	
	public class SEBSPFileLump {
		
		public int version;
		public final char[] identifier = new char[4];
		public final SEBSPLumpType type;
		
		private SEBSPFileLump(int ver, char[] ident, SEBSPLumpType t) {
			version = ver;
			System.arraycopy(ident, 0, identifier, 0, identifier.length);
			type = t;
		}
		
	}
	
	public class SEBSPFileDispInfoLump {
		
		public Vector3f startPosition = new Vector3f();
		public int dispVertStart;
		public int dispTriStart;
		public int power;
		public int minTess;
		public float smoothingAngle;
		public int contents;
		// Unsigned
		public short mapFace;
		public int lightmapAlphaStart;
		public int lightmapSamplePositionStart;
		
		public int[] allowedVerts = new int[10];
		
	}
	
	public enum SEBSPLumpType {
		LUMP_ENTITIES,
		LUMP_PLANES,
		LUMP_TEXDATA,
		LUMP_VERTEXES,
		LUMP_VISIBILITY,
		LUMP_NODES,
		LUMP_TEXINFO,
		LUMP_FACES,
		LUMP_LIGHTING,
		LUMP_OCCLUSION,
		LUMP_LEAFS,
		LUMP_FACIDS,
		LUMP_EDGES,
		LUMP_SURFEDGES,
		LUMP_MODELS,
		LUMP_WORLDLIGHTS,
		LUMP_LEAFFACES,
		LUMP_LEAFBRUSHES,
		LUMP_BRUSHES,
		LUMP_BRUSHSIDES,
		LUMP_AREAS,
		LUMP_AREAPORTALS,
		LUMP_PORTALS,
		LUMP_UNUSED0(22),
		LUMP_PROPCOLLISION(22),
		LUMP_CLUSTERS(23),
		LUMP_UNUSED1(23),
		LUMP_PROPHULLS(23),
		LUMP_PORTALVERTS(24),
		LUMP_UNUSED2(24),
		LUMP_PROPHULLVERTS(24),
		LUMP_CLUSTERPORTALS(25),
		LUMP_UNUSED3(25),
		LUMP_PROPTRIS(25),
		LUMP_DISPINFO(26),
		LUMP_ORIGINALFACES(27),
		LUMP_PHYSDISP(28),
		LUMP_PHYSCOLLIDE(29),
		LUMP_VERTNORMALS(30),
		LUMP_VERTNORMALINDICES(31),
		LUMP_DISP_LIGHTMAP_ALPHAS(32),
		LUMP_DISP_VERTS(33),
		LUMP_DISP_LIGHTMAP_SAMPLE_POSITIONS(34),
		LUMP_GAME_LUMP(35),
		LUMP_LEAFWATERDATA(36),
		LUMP_PRIMITIVES(37),
		LUMP_PRIMVERTS(38),
		LUMP_PRIMINDICES(39),
		LUMP_PAKFILE(40),
		LUMP_CLIPPORTALVERTS(41),
		LUMP_CUBEMAPS(42),
		LUMP_TEXDATA_STRING_DATA(43),
		LUMP_TEXDATA_STRING_TABLE(44),
		LUMP_OVERLAYS(45),
		LUMP_LEAMMINDISTTOWATER(46),
		LUMP_FACE_MACRO_TEXTURE_INFO(47),
		LUMP_DISP_TRIS(48),
		LUMP_PHYSCOLLIDESURFACE(49),
		LUMP_PROP_BLOB(49),
		LUMP_WATEROVERLAYS(50),
		LUMP_LIGHTMAPPAGES(51),
		LUMP_LEAF_AMBIENT_INDEX_HDR(51),
		LUMP_LIGHTMAPPAGEINFOS(52),
		LUMP_LEAF_AMBIENT_INDEX(52),
		LUMP_LIGHTING_HDR(53),
		LUMP_WORLDLIGHTS_HDR(54),
		LUMP_LEAF_AMBIENT_LIGHTING_HDR(55),
		LUMP_LEAF_AMBIENT_LIGHTING(56),
		LUMP_XZIPPAKFILE(57),
		LUMP_FACES_HDR(58),
		LUMP_MAP_FLAGS(59),
		LUMP_OVERLAY_FADES(60),
		LUMP_OVERLAY_SYSTEM_LEVELS(61),
		LUMP_PHYSLEVEL(62),
		LUMP_DISP_MULTIBLEND(63);
		
		public final int value;
		private SEBSPLumpType() {
			value = super.ordinal();
		}
		
		private SEBSPLumpType(int val) {
			value = val;
		}
	}

	public int identifier;
	public int version;
	
}
