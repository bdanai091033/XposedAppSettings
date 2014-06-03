package me.piebridge.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;

public class SharedPreferencesUtil {

	public static Set<String> getStringSet(SharedPreferences preferences, String key, Set<String> defValues) {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
			return getStringSetHC(preferences, key, defValues);
		} else {
			return getStringSetGB(preferences, key, defValues);
		}
	}

	private static String toString(final Set<String> values) {
		if (values == null) {
			return null;
		}
		if (values.size() == 0) {
			return "";
		}
		StringBuilder buffer = new StringBuilder();
		// i'd like it's been sorted
		Iterator<String> it = (new TreeSet<String>(values)).iterator();
		while (it.hasNext()) {
			buffer.append(it.next());
			if (it.hasNext()) {
				buffer.append(",");
			}
		}
		return buffer.toString();
	}

	private static Set<String> toSet(String value) {
		if (value == null) {
			return null;
		}
		Set<String> values = new HashSet<String>();
		// in previous version, i use @@ instead of ,
		for (String s : value.split("(,)|(@@)")) {
			if (s != null && s.length() > 0) {
				values.add(s);
			}
		}
		return values;
	}

	private static Set<String> getStringSetGB(SharedPreferences preferences, String key, Set<String> defValues) {
		return toSet(preferences.getString(key, toString(defValues)));
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private static Set<String> getStringSetHC(SharedPreferences preferences, String key, Set<String> defValues) {
		return preferences.getStringSet(key, defValues);
	}

	public static Editor putStringSet(Editor editor, String key, Set<String> values) {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
			return putStringSetHC(editor, key, values);
		} else {
			return putStringSetGB(editor, key, values);
		}
	}

	private static Editor putStringSetGB(Editor editor, String key, Set<String> values) {
		return editor.putString(key, toString(values));
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private static Editor putStringSetHC(Editor editor, String key, Set<String> values) {
		return editor.putStringSet(key, values);
	}

}
