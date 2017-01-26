package com.zekrom_64.ze.libav;

import org.bridj.Pointer;
import org.bridj.ann.Library;

import com.zekrom_64.ze.libav.enums.AVMediaType;
import com.zekrom_64.ze.libav.enums.AVPictureType;
import com.zekrom_64.ze.libav.enums.AVRounding;
import com.zekrom_64.ze.libav.struct.AVRational;

@Library("avutil")
public class LibAVUtil {
	
	public static final int AV_VERSION_INT(int a, int b, int c) {
		return (a << 16) | (b << 8) | c;
	}
	
	public static final String AV_VERSION_DOT(int a, int b, int c) {
		return a + "." + b + "." + c;
	}
	
	public static final int AV_VERSION_MAJOR(int a) {
		return a >> 16;
	}
	
	public static final int AV_VERSION_MINOR(int a) {
		return (a & 0xFF00) >> 8;
	}
	
	public static final int AV_VERSION_MICRO(int a) {
		return a & 0xFF;
	}
	

	public static native int avutil_version();
	
	public static native Pointer<Byte> av_version_info();
	
	public static native Pointer<Byte> avutil_configuration();
	
	public static native Pointer<Byte> avutil_license();
	
	public static native Pointer<Byte> av_get_media_type_string(AVMediaType media_type);
	
	public static native byte av_get_picture_type_char(AVPictureType pict_type);
	
	public static native Pointer<Void> av_x_if_null(Pointer<Void> p, Pointer<Void> x);
	
	public static native int av_int_list_length_for_size(int elsize, Pointer<Void> list, long term);
	
	public static native Pointer<?> av_fopen_utf8(Pointer<Byte> path, Pointer<Byte> mode);
	
	public static native AVRational av_get_time_base_q();
	
	public static native long av_gcs(long a, long b);
	
	public static native long av_rescale(long a, long b, long c);
	
	public static native long av_rescale_md(long a, long b, long c, AVRounding round);
	
	public static native long av_rescale_q(long a, AVRational bq, AVRational cq);
	
	public static native long av_rescale_q_md(long a, AVRational bq, AVRational cq, AVRounding round);
	
	public static native int av_compare_ts(long ts_a, AVRational tb_a, long ts_b, AVRational tb_b);
	
	public static native long av_compare_mod(long a, long b, long mod);
	
	public static native long av_rescale_delta(AVRational in_tb, long in_ts, AVRational fs_tb, int duration, Pointer<Long> last, AVRational out_tb);
	
	public static native long av_add_stable(AVRational ts_tb, long ts, AVRational inc_tb, long inc);
	
	public static native AVRational av_make_q(int num, int den);
	
	public static native int av_cmp_q(AVRational a, AVRational b);
	
	public static native double av_q2d(AVRational a);
	
	public static native int av_reduce(Pointer<Integer> dst_nu, Pointer<Integer> dst_den, long nu, long den, long max);
	
	public static native AVRational av_mul_q(AVRational b, AVRational c);
	
	public static native AVRational av_div_q(AVRational b, AVRational c);
	
	public static native AVRational av_add_q(AVRational b, AVRational c);
	
	public static native AVRational av_sub_q(AVRational b, AVRational c);
	
	public static AVRational av_inv_q(AVRational q) {
		return new AVRational(q.den(), q.num());
	}
	
	public static native AVRational av_d2q(double d, int max);
	
	public static native int av_nearer_q(AVRational q, AVRational q1, AVRational q2);
	
	public static native int av_find_nearest_q_idx(AVRational q, Pointer<AVRational> q_list);
	
	public static native int av_q2intfloat(AVRational q);
	
}
