package net.sqlwebclient.util;

import com.google.common.base.Throwables;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import net.sqlwebclient.core.AppConstant;
import org.apache.commons.beanutils.BeanUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Toolkit {
    private static final int SCALE = 2;

    public static double round(final double dbl) {
        return new BigDecimal(dbl).setScale(SCALE, RoundingMode.HALF_UP).doubleValue();
    }

	public static boolean isFullyNumeric(final String string) {
        if(string == null || string.isEmpty()) {
            return false;
        }

        for(final char character: string.toCharArray()) {
            if(! (Character.isDigit(character) || '.' == character || ',' == character)) {
                return false;
            }
        }

        return true;
    }

    public static <T> T coalesce(final T...ts) {
        for(T t: ts) {
            if(t != null) {
                return t;
            }
        }
        return null;
    }

	public static String toString(final InputStream inputStream) throws IOException {
		return CharStreams.toString(new InputStreamReader(inputStream, AppConstant.ENCODING));
	}

	public static void copy(final InputStream is, final OutputStream out) throws IOException {
		ByteStreams.copy(is, out);
	}

	public static <T> T wrapExceptionMethod(final ExceptionMethod method) {
		try {
			return (T) method.execute();
		} catch (Exception ex) {
			throw Throwables.propagate(ex);
		}
	}

	public static void copyProperties(final Object source, final Object destination) {
		try {
			BeanUtils.copyProperties(source, destination);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static interface ExceptionMethod {
		Object execute() throws Exception;
	}
}
