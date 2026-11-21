package io.github.erinleonid3_collab;

import java.util.Arrays;

public class CustomStringBuilder {
    private char[] value;
    private int count;
    private Snapshot snapshot;

    public CustomStringBuilder() {
        value = new char[16];
    }

    public CustomStringBuilder(String valueString) {
        int len = valueString.length();
        value = Arrays.copyOf(valueString.toCharArray(), len + 16);
        count = len;
    }

    public CustomStringBuilder(int capacity) {
        value = new char[capacity];
    }

    private void saveSnapshot() {
        snapshot = new Snapshot(value, count);
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity <= value.length) {
            return;
        }
        value = Arrays.copyOf(value, Math.max(minCapacity, (value.length * 2) + 2));
    }

    public int length() {
        return count;
    }

    public int capacity() {
        return value.length;
    }

    public boolean undo() {
        if (snapshot == null) {
            return false;
        }
        System.arraycopy(snapshot.snapshotValue, 0, value, 0, snapshot.snapshotCount);
        count = snapshot.snapshotCount;
        snapshot = null;
        return true;

    }

    public CustomStringBuilder append(String str) {
        if (str == null) {
            str = "null";
        }
        int len = str.length();
        if (len == 0) {
            return this;
        }
        ensureCapacity(count + len);
        saveSnapshot();
        str.getChars(0, len, value, count);
        count += len;
        return this;
    }

    public char charAt(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + count);
        }
        return value[index];
    }

    @Override
    public String toString() {
        return new String(value, 0, count);
    }

    public CustomStringBuilder delete(int start, int end) {
        if (start < 0 || start > end || end > count) {
            throw new StringIndexOutOfBoundsException(
                    "start " + start + ", end " + end + ", length " + count);
        }
        if (start == end) {
            return this;
        }
        saveSnapshot();
        System.arraycopy(value, end, value, start, count - end);
        count -= (end - start);
        return this;
    }

    public int indexOf(String str) {
        if (str == null) {
            throw new NullPointerException("str is null");
        }
        int strLen = str.length();
        if (strLen == 0) {
            return 0;
        }
        if (strLen > count) {
            return -1;
        }
        int limit = count - strLen;
        for (int i = 0; i <= limit; i++) {
            if (value[i] != str.charAt(0)) {
                continue;
            }
            boolean match = true;
            for (int j = 1; j < strLen; j++) {
                if (value[i + j] != str.charAt(j)) {
                    match = false;
                    break;
                }
            }
            if (match) {
                return i;
            }
        }
        return -1;
    }

    public CustomStringBuilder insert(int index, String str) {
        if (index > count || index < 0) {
            throw new StringIndexOutOfBoundsException(
                    "index " + index + ", length " + count);
        }
        if (str == null) {
            str = "null";
        }
        if (str.length() == 0) {
            return this;
        }
        int strLen = str.length();
        if (strLen == 0) {
            return this;
        }
        ensureCapacity(count + strLen);
        saveSnapshot();
        System.arraycopy(value, index, value, index + strLen, count - index);
        str.getChars(0, strLen, value, index);
        count += strLen;
        return this;
    }

    private static class Snapshot {
        private final char[] snapshotValue;
        private final int snapshotCount;

        private Snapshot(char[] value, int count) {
            snapshotCount = count;
            snapshotValue = Arrays.copyOf(value, count);
        }

    }
}
