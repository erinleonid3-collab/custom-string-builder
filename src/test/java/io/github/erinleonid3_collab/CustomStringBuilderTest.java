package io.github.erinleonid3_collab;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomStringBuilderTest {
    @Test
    void defaultConstructor_lengthIsZero() {
        CustomStringBuilder sb = new CustomStringBuilder();
        assertEquals(0, sb.length());
    }

    @Test
    void defaultConstructor_capacityIsSixteen() {
        CustomStringBuilder sb = new CustomStringBuilder();
        assertEquals(16, sb.capacity());
    }

    @Test
    void capacityConstructor_setsCapacity() {
        CustomStringBuilder sb = new CustomStringBuilder(100);
        assertEquals(100, sb.capacity());
    }

    @Test
    void capacityConstructor_lengthIsZero() {
        CustomStringBuilder sb = new CustomStringBuilder(100);
        assertEquals(0, sb.length());
    }

    @Test
    void stringConstructor_lengthEqualsStringLength() {
        CustomStringBuilder sb = new CustomStringBuilder("abc");
        assertEquals(3, sb.length());
    }

    @Test
    void stringConstructor_capacityIsLengthPlusSixteen() {
        CustomStringBuilder sb = new CustomStringBuilder("abc");
        assertEquals(19, sb.capacity());
    }

    @Test
    void stringConstructor_containsOriginalString() {
        CustomStringBuilder sb = new CustomStringBuilder("abc");
        assertEquals("abc", sb.toString());
    }

    @Test
    void capacityConstructor_negativeCapacity_throwsException() {
        assertThrows(NegativeArraySizeException.class,
                () -> new CustomStringBuilder(-1));
    }

    @Test
    void stringConstructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> new CustomStringBuilder((String) null));
    }

    @Test
    void undo_afterCreation_returnsFalse() {
        CustomStringBuilder sb = new CustomStringBuilder();
        assertFalse(sb.undo());
    }

    @Test
    void undo_afterStringConstructor_returnsFalse() {
        CustomStringBuilder sb = new CustomStringBuilder("abc");
        assertFalse(sb.undo());
    }

    @Test
    void undo_afterCapacityConstructor_returnsFalse() {
        CustomStringBuilder sb = new CustomStringBuilder(50);
        assertFalse(sb.undo());
    }

    @Test
    void append_string_appendContent() {
        CustomStringBuilder sb = new CustomStringBuilder();
        sb.append("str");
        assertEquals("str", sb.toString());
    }

    @Test
    void append_string_updateLength() {
        CustomStringBuilder sb = new CustomStringBuilder();
        sb.append("str");
        assertEquals(3, sb.length());
    }


    @Test
    void append_chain_appendsAll() {
        CustomStringBuilder sb = new CustomStringBuilder();
        sb.append("a").append("b").append("c");
        assertEquals("abc", sb.toString());
    }

    @Test
    void append_null_appendsNullString() {
        CustomStringBuilder sb = new CustomStringBuilder();
        sb.append(null);
        assertEquals("null", sb.toString());
    }

    @Test
    void append_emptyString_doesNotChangeContent() {
        CustomStringBuilder sb = new CustomStringBuilder("abc");
        sb.append("");
        assertEquals("abc", sb.toString());
    }

    @Test
    void append_emptyString_doesNotCreateSnapshot() {
        CustomStringBuilder sb = new CustomStringBuilder();
        sb.append("");
        assertFalse(sb.undo());
    }

    @Test
    void append_multiple_appendsInOrder() {
        CustomStringBuilder sb = new CustomStringBuilder();
        sb.append("abc").append("def");
        assertEquals("abcdef", sb.toString());
        assertEquals(6, sb.length());
    }

    @Test
    void append_manyChars_growsCapacity() {
        CustomStringBuilder sb = new CustomStringBuilder();
        for (int i = 0; i < 20; i++) {
            sb.append("x");
        }
        assertEquals(20, sb.length());
        assertEquals("xxxxxxxxxxxxxxxxxxxx", sb.toString());
        assertTrue(sb.capacity() >= 20);

    }

    @Test
    void undo_noChanges_returnsFalse() {
        CustomStringBuilder sb = new CustomStringBuilder();
        assertFalse(sb.undo());
    }

    @Test
    void undo_singleAppend_restoresEmptyState() {
        CustomStringBuilder sb = new CustomStringBuilder();
        sb.append("a");
        assertTrue(sb.undo());
        assertEquals("", sb.toString());
        assertEquals(0, sb.length());
    }

    @Test
    void undo_twiceInARow_secondReturnsFalse() {
        CustomStringBuilder sb = new CustomStringBuilder();
        sb.append("a");
        assertTrue(sb.undo());
        assertFalse(sb.undo());
        assertEquals("", sb.toString());
    }

    @Test
    void undo_thenNewAppend_startsWithRolledBackState() {
        CustomStringBuilder sb = new CustomStringBuilder();
        sb.append("a");
        sb.append("b");
        sb.undo();
        sb.append("c");
        assertEquals("ac", sb.toString());
    }

    @Test
    void undo_thenAppend_doesNotCorruptSnapshot() {
        CustomStringBuilder sb = new CustomStringBuilder();
        sb.append("a");
        sb.undo();
        sb.append("b");
        assertEquals("b", sb.toString());
    }

    @Test
    void charAt_firstChar_returnsFirstChar() {
        CustomStringBuilder sb = new CustomStringBuilder("abc");
        assertEquals('a', sb.charAt(0));
    }

    @Test
    void charAt_lastChar_returnsLastChar() {
        CustomStringBuilder sb = new CustomStringBuilder("abc");
        assertEquals('c', sb.charAt(2));
    }

    @Test
    void charAt_outOfBounds_throwsException() {
        CustomStringBuilder sb = new CustomStringBuilder("abc");
        assertThrows(IndexOutOfBoundsException.class, () -> sb.charAt(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> sb.charAt(3));
    }

    @Test
    void toString_afterAppend_returnsContent() {
        CustomStringBuilder sb = new CustomStringBuilder();
        sb.append("abc");
        assertEquals("abc", sb.toString());
    }

    @Test
    void toString_emptyBuilder_returnsEmptyString() {
        CustomStringBuilder sb = new CustomStringBuilder();
        assertEquals("", sb.toString());
    }

    @Test
    void delete_middleRange_removesOnlyThatRange() {
        CustomStringBuilder sb = new CustomStringBuilder("abcd");
        sb.delete(1, 3);
        assertEquals("ad", sb.toString());
    }

    @Test
    void delete_fullRange_emptiesBuilder() {
        CustomStringBuilder sb = new CustomStringBuilder("abcd");
        sb.delete(0, 4);
        assertEquals("", sb.toString());
        assertEquals(0, sb.length());
    }

    @Test
    void delete_emptyRange_doesNothing() {
        CustomStringBuilder sb = new CustomStringBuilder("abcd");
        sb.delete(0, 0);
        assertEquals("abcd", sb.toString());
    }

    @Test
    void delete_thenUndo_restoresContent() {
        CustomStringBuilder sb = new CustomStringBuilder("abcd");
        sb.delete(1, 3);
        assertTrue(sb.undo());
        assertEquals("abcd", sb.toString());
    }

    @Test
    void delete_StringIndexOutOfBound_throwsException() {
        CustomStringBuilder sb = new CustomStringBuilder("abc");
        assertThrows(StringIndexOutOfBoundsException.class, () -> sb.delete(-1, 2));
        assertThrows(StringIndexOutOfBoundsException.class, () -> sb.delete(0, 4));
        assertThrows(StringIndexOutOfBoundsException.class, () -> sb.delete(2, 1));
    }

    @Test
    void indexOf_firstChar_returnsZero() {
        CustomStringBuilder sb = new CustomStringBuilder("abc");
        assertEquals(0, sb.indexOf("a"));
    }

    @Test
    void indexOf_lastChar_returnsLastIndex() {
        CustomStringBuilder sb = new CustomStringBuilder("abc");
        assertEquals(2, sb.indexOf("c"));   // ← ЛОВИТ БАГ i < limit
    }

    @Test
    void indexOf_absentString_returnsMinusOne() {
        CustomStringBuilder sb = new CustomStringBuilder("abc");
        assertEquals(-1, sb.indexOf("d"));
    }

    @Test
    void indexOf_middleSubstring_returnsCorrectIndex() {
        CustomStringBuilder sb = new CustomStringBuilder("abcabc");
        assertEquals(2, sb.indexOf("ca"));
    }

    @Test
    void indexOf_emptyString_returnsZero() {
        CustomStringBuilder sb = new CustomStringBuilder("abc");
        assertEquals(0, sb.indexOf(""));
    }

    @Test
    void indexOf_emptyBuilder_returnsMinusOne() {
        CustomStringBuilder sb = new CustomStringBuilder();
        assertEquals(-1, sb.indexOf("a"));
    }

    @Test
    void indexOf_null_throwsNullPointerException() {
        CustomStringBuilder sb = new CustomStringBuilder("abc");
        assertThrows(NullPointerException.class, () -> sb.indexOf(null));
    }

    @Test
    void insert_atStart_prependsString() {
        CustomStringBuilder sb = new CustomStringBuilder("abc");
        sb.insert(0, "X");
        assertEquals("Xabc", sb.toString());
    }

    @Test
    void insert_atEnd_appendsString() {
        CustomStringBuilder sb = new CustomStringBuilder("abc");
        sb.insert(3, "X");
        assertEquals("abcX", sb.toString());
    }

    @Test
    void insert_inMiddle_shiftsTailRight() {
        CustomStringBuilder sb = new CustomStringBuilder("abc");
        sb.insert(1, "XY");
        assertEquals("aXYbc", sb.toString());
    }

    @Test
    void insert_emptyString_doesNotChangeContent() {
        CustomStringBuilder sb = new CustomStringBuilder("abc");
        sb.insert(1, "");
        assertEquals("abc", sb.toString());
    }

    @Test
    void insert_null_insertsNullString() {
        CustomStringBuilder sb = new CustomStringBuilder("abc");
        sb.insert(1, (String) null);
        assertEquals("anullbc", sb.toString());
    }

    @Test
    void insert_StringIndexOutOfBounds_throwsException() {
        CustomStringBuilder sb = new CustomStringBuilder("abc");
        assertThrows(StringIndexOutOfBoundsException.class, () -> sb.insert(-1, "X"));
        assertThrows(StringIndexOutOfBoundsException.class, () -> sb.insert(4, "X"));

    }


}