package com.web.app.common.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    
    /**
     * <p>
     * ���鹮��(blank String)
     * </p>
     */
    private final static char WHITE_SPACE = ' ';

    /**
     * <p>
     * <strong>StringHelper</strong>�� default ����Ʈ����(Constructor).
     * </p>
     */
    protected StringUtil() {
    }

    /**
     * <p>
     * ���ڿ����� Property������ ���� �����Ѵ�. Property ���¶� 'key=value'�������� �Ǿ��ִ� ���� �ǹ��Ѵ�.
     * </p>
     *
     * <pre>
     * 
     * String source = &quot;key1=value1::key2=value2::key3=value3&quot;;
     * 
     * String key = &quot;key2&quot;;
     * 
     * String delim = &quot;::&quot;;
     * 
     * String result = StringHelper.getParam(source, key, delim, &quot;Default Value&quot;);
     * </pre>
     * 
     * <code>result</code>�� <code>"value2"</code> �� ������ �ȴ�.
     *
     * @param source
     *            ������Ƽ�� �˻��� ���� ���ڿ�
     * @param key
     *            �˻��� Ű ���ڿ�
     * @param delim
     *            ������
     * @param defaultValue
     *            �ش� Key�� �ش��ϴ� ���� ������ ��ȯ�� �⺻��
     * @return �˻��� Property�� Value
     */
    public static String getParam(String source,
        String key,
        String delim,
        String defaultValue) {
        if (isNull(source) || isNull(key)) {
            return defaultValue;
        }
        int i = source.indexOf(key + "=");
        if (i < 0) {
            return defaultValue;
        }
        int j = i + key.length() + 1;
        int k = source.indexOf(delim, j);
        if (k < 0) {
            k = source.length();
        }
        try {
            return source.substring(j, k);
        } catch (Exception _ex) {
            return defaultValue;
        }
    }

    /**
     * <p>
     * ������ �� �ڸ� ��ŭ ���ʺ��� �е����ڸ� ä���.
     * </p>
     *
     * <pre>
     * 
     * String source = &quot;300&quot;;
     * 
     * String result = StringHelper.lPad(source, 5, '#');
     * </pre>
     * 
     * <code>result</code>�� <code>"##300"</code> �� ������ �ȴ�.
     *
     * @param source
     *            ���� ���ڿ�
     * @param len
     *            ���ϰ��� �ϴ� ���ڿ��� ����
     * @param pad
     *            ���������� �ϴ� ����
     * @return �е��� ���ڿ�
     */
    public static String lPad(String source,
        int len,
        char pad) {
        return lPad(source, len, pad, false);
    }

    /**
     * <p>
     * ������ �� �ڸ� ��ŭ ���ʺ��� �е����ڸ� ä���.
     * </p>
     *
     * <pre>
     * 
     * String source = &quot;300&quot;;
     * 
     * String result = StringHelper.lPad(source, 5, '#');
     * </pre>
     * 
     * <code>result</code>�� <code>"##300"</code> �� ������ �ȴ�.
     *
     * @param source
     *            ���� ���ڿ�
     * @param len
     *            ���ϰ��� �ϴ� ���ڿ��� ����
     * @param pad
     *            ���������� �ϴ� ����
     * @param isTrim
     *            ���ڿ� trim ����
     * @return �е��� ���ڿ�
     */
    public static String lPad(String source,
        int len,
        char pad,
        boolean isTrim) {

        if (isTrim) {
            source = source.trim();
        }

        for (int i = source.length(); i < len; i++) {
            source = pad + source;
        }
        return source;
    }

    /**
     * <p>
     * ������ �� �ڸ� ��ŭ �����ʺ��� �е����ڸ� ä���.
     * </p>
     *
     * <pre>
     * 
     * String source = &quot;300&quot;;
     * 
     * String result = StringHelper.rPad(source, 5, '#');
     * </pre>
     * 
     * <code>result</code>�� <code>"300##"</code> �� ������ �ȴ�.
     *
     * @param source
     *            ���� ���ڿ�
     * @param len
     *            ���ϰ��� �ϴ� ���ڿ��� ����
     * @param pad
     *            ���������� �ϴ� ����
     * @return �е��� ���ڿ�
     */
    public static String rPad(String source,
        int len,
        char pad) {
        return rPad(source, len, pad, false);
    }

    /**
     * <p>
     * ������ �� �ڸ� ��ŭ �����ʺ��� �е����ڸ� ä���.
     * </p>
     *
     * <pre>
     * 
     * String source = &quot;300&quot;;
     * 
     * String result = StringHelper.rPad(source, 5, '#');
     * </pre>
     * 
     * <code>result</code>�� <code>"300##"</code> �� ������ �ȴ�.
     *
     * @param source
     *            ���� ���ڿ�
     * @param len
     *            ���ϰ��� �ϴ� ���ڿ��� ����
     * @param pad
     *            ���������� �ϴ� ����
     * @param isTrim
     *            ���ڿ��� trim ����
     * @return �е��� ���ڿ�
     */
    public static String rPad(String source,
        int len,
        char pad,
        boolean isTrim) {

        if (isTrim) {
            source = source.trim();
        }

        for (int i = source.length(); i < len; i++) {
            source = source + pad;
        }
        return source;
    }

    /**
     * <p>
     * �ش� ���ڿ��� ���� WhiteSpace({@link java.lang.Character#isWhitespace})�� �����.
     * </p>
     *
     * @param source
     *            ���ڿ�
     * @return ���� ������ ���ŵ� ���ڿ�
     */
    public static String lTrim(String source) {
        int strIdx = 0;
        char[] val = source.toCharArray();
        int lenIdx = val.length;

        while ((strIdx < lenIdx) && Character.isWhitespace(val[strIdx])) {
            strIdx++;
        }

        return (strIdx >= 0) ? source.substring(strIdx) : source;
    }

    /**
     * <p>
     * �ش� ���ڿ��� ������ WhiteSpace({@link java.lang.Character#isWhitespace})�� �����.
     * </p>
     *
     * @param source
     *            ���ڿ�
     * @return ������ ������ ���ŵ� ���ڿ�
     */
    public static String rTrim(String source) {
        int strIdx = 0;
        char[] val = source.toCharArray();
        int count = val.length;
        int lenIdx = count;

        while ((strIdx < lenIdx) && Character.isWhitespace(val[lenIdx - 1])) {
            lenIdx--;
        }

        return (lenIdx >= 0) ? source.substring(strIdx, lenIdx) : source;
    }

    /**
     * <p>
     * Byte �߶󳻱�(�ѱ�����).
     * </p>
     *
     * <pre>
     * 
     * String source = &quot;&quot;;
     * 
     * String result = StringHelper.byteSubString(source, 4);
     * </pre>
     * 
     * <code>result</code>�� <code>"?????"</code> �� ������ �ȴ�.
     *
     * @param source
     *            ���� ���ڿ�
     * @param len
     *            ���ϰ��� �ϴ� ���ڿ��� ����
     * @return �߷��� ���ڿ�
     */
    public static String byteSubString(String source,
        int len) {

        if (isNull(source)) {
            return "";
        }

        String tmp = source;

        int slen = 0, blen = 0;
        char c;
        if (getByteLength(tmp) > len) {
            while (blen + 1 < len) {
                c = tmp.charAt(slen);
                blen++;
                slen++;
                if (c > 127) {
                    blen++; // 2-byte character..
                }
            }
            tmp = tmp.substring(0, slen);
        }
        return tmp;
    }

    /**
     * <p>
     * ���ڿ��� ���� �����Ѵ�. �̶� ���ڿ��ڿ� ����ǥ�� ���� �ʴ´�.
     * </p>
     *
     * <pre>
     * 
     * String source = &quot;ABCDEFG&quot;;
     * 
     * String result = StringHelper.alignLeft(source, 10);
     * </pre>
     * 
     * <code>result</code>�� <code>"ABCDEFG  "</code> �� ������ �ȴ�.
     *
     * @param source
     *            ���� ���ڿ�
     * @param length
     *            ������ �̷���� ����
     * @return ������ �̷���� ���ڿ�
     */
    public static String alignLeft(String source,
        int length) {
        return alignLeft(source, length, false);
    }

    /**
     * <p>
     * ���ڿ��� ���� �����Ѵ�.
     * </p>
     *
     * <pre>
     * 
     * String source = &quot;ABCDEFG&quot;;
     * 
     * String result = StringHelper.alignLeft(source, 5, true);
     * </pre>
     * 
     * <code>result</code>�� <code>"AB..."</code> �� ������ �ȴ�.
     *
     * @param source
     *            ���� ���ڿ�
     * @param length
     *            ������ �̷���� ����
     * @param isEllipsis
     *            �������� ����ǥ("...")�� ����
     * @return ������ �̷���� ���ڿ�
     */
    public static String alignLeft(String source,
        int length,
        boolean isEllipsis) {

        if (source.length() <= length) {

            StringBuffer temp = new StringBuffer(source);
            for (int i = 0; i < (length - source.length()); i++) {
                temp.append(WHITE_SPACE);
            }
            return temp.toString();
        } else {
            if (isEllipsis) {

                StringBuffer temp = new StringBuffer(length);
                temp.append(source.substring(0, length - 3));
                temp.append("...");
                return temp.toString();
            } else {
                return source.substring(0, length);
            }
        }
    }

    /**
     * <p>
     * ���ڿ��� ���� �����Ѵ�. �̶� ���ڿ��ڿ� ����ǥ�� ���� �ʴ´�.
     * </p>
     *
     * <pre>
     * 
     * String source = &quot;ABCDEFG&quot;;
     * 
     * String result = StringHelper.alignRight(source, 10);
     * </pre>
     * 
     * <code>result</code>�� <code>"   ABCDEFG"</code> �� ������ �ȴ�.
     *
     * @param source
     *            ���� ���ڿ�
     * @param length
     *            ������ �̷���� ����
     * @return ������ �̷���� ���ڿ�
     */
    public static String alignRight(String source,
        int length) {

        return alignRight(source, length, false);
    }

    /**
     * <p>
     * ���ڿ��� ���� �����Ѵ�.
     * </p>
     *
     * <pre>
     * 
     * String source = &quot;ABCDEFG&quot;;
     * 
     * String result = StringHelper.alignRight(source, 5, true);
     * </pre>
     * 
     * <code>result</code>�� <code>"AB..."</code> �� ������ �ȴ�.
     *
     * @param source
     *            ���� ���ڿ�
     * @param length
     *            ������ �̷���� ����
     * @param isEllipsis
     *            �������� ����ǥ("...")�� ����
     * @return ������ �̷���� ���ڿ�
     */
    public static String alignRight(String source,
        int length,
        boolean isEllipsis) {

        if (source.length() <= length) {

            StringBuffer temp = new StringBuffer(length);
            for (int i = 0; i < (length - source.length()); i++) {
                temp.append(WHITE_SPACE);
            }
            temp.append(source);
            return temp.toString();
        } else {
            if (isEllipsis) {

                StringBuffer temp = new StringBuffer(length);
                temp.append(source.substring(0, length - 3));
                temp.append("...");
                return temp.toString();
            } else {
                return source.substring(0, length);
            }
        }
    }

    /**
     * <p>
     * ���ڿ��� �߾� �����Ѵ�. �̶� ���ڿ��ڿ� ����ǥ�� ���� �ʴ´�. ���� ������ Ȧ���� ���´ٸ� �����ʿ� ��� ����.
     * </p>
     *
     * <pre>
     * 
     * String source = &quot;ABCDEFG&quot;;
     * 
     * String result = StringHelper.alignCenter(source, 10);
     * </pre>
     * 
     * <code>result</code>�� <code>" ABCDEFG  "</code> �� ������ �ȴ�.
     *
     * @param source
     *            ���� ���ڿ�
     * @param length
     *            ������ �̷���� ����
     * @return ������ �̷���� ���ڿ�
     */
    public static String alignCenter(String source,
        int length) {
        return alignCenter(source, length, false);
    }

    /**
     * <p>
     * ���ڿ��� �߾� �����Ѵ�.�̶� ���ڿ��ڿ� ����ǥ�� ���� �ʴ´�. ���� ������ Ȧ���� ���´ٸ� �����ʿ� ��� ����.
     * </p>
     *
     * <pre>
     * 
     * String source = &quot;ABCDEFG&quot;;
     * 
     * String result = StringHelper.alignCenter(source, 5, true);
     * </pre>
     * 
     * <code>result</code>�� <code>"AB..."</code> �� ������ �ȴ�.
     *
     * @param source
     *            ���� ���ڿ�
     * @param length
     *            ������ �̷���� ����
     * @param isEllipsis
     *            �������� ����ǥ("...")�� ����
     * @return ������ �̷���� ���ڿ�
     */
    public static String alignCenter(String source,
        int length,
        boolean isEllipsis) {
        if (source.length() <= length) {

            StringBuffer temp = new StringBuffer(length);
            int leftMargin = (int) (length - source.length()) / 2;

            int rightMargin;
            if ((leftMargin * 2) == (length - source.length())) {
                rightMargin = leftMargin;
            } else {
                rightMargin = leftMargin + 1;
            }

            for (int i = 0; i < leftMargin; i++) {
                temp.append(WHITE_SPACE);
            }

            temp.append(source);

            for (int i = 0; i < rightMargin; i++) {
                temp.append(WHITE_SPACE);
            }

            return temp.toString();
        } else {
            if (isEllipsis) {

                StringBuffer temp = new StringBuffer(length);
                temp.append(source.substring(0, length - 3));
                temp.append("...");
                return temp.toString();
            } else {
                return source.substring(0, length);
            }
        }

    }

    /**
     * <p>
     * ���ڿ��� ���� ó�����ڸ� �빮��ȭ �Ѵ�.
     * </p>
     *
     * <pre>
     * 
     * String source = &quot;abcdefg&quot;;
     * 
     * String result = StringHelper.capitalize(source);
     * </pre>
     * 
     * <code>result</code>�� <code>"Abcdefg"</code> �� ������ �ȴ�.
     *
     * @param source
     *            ���� ���ڿ�
     * @return �빮��ȭ �� ���ڿ�
     */
    public static String capitalize(String source) {
        return !isNull(source) ? source.substring(0, 1).toUpperCase() + source.substring(1).toLowerCase() : source;
    }

    /**
     * <p>
     * ����ڿ�(source)���� �������ڿ�(target)�� �˻��� Ƚ����, �������ڿ��� ������ 0 �� ��ȯ�Ѵ�.
     * </p>
     *
     * <pre>
     * 
     * String source = &quot;ar&quot;;
     * 
     * String target = &quot;StringHelper Class &amp;ar search&quot;;
     * 
     * int result = StringHelper.search(source, target);
     * </pre>
     * 
     * <code>result</code>�� <code>2</code>�� ������ �ȴ�.
     *
     * @param source
     *            ����ڿ�
     * @param target
     *            �˻��� ���ڿ�
     * @return �������ڿ��� �˻��Ǿ����� �˻��� Ƚ����, �˻����� �ʾ����� 0 �� ��ȯ�Ѵ�.
     */
    public static int search(String source,
        String target) {
        int result = 0;
        String strCheck = new String(source);
        for (int i = 0; i < source.length();) {
            int loc = strCheck.indexOf(target);
            if (loc == -1) {
                break;
            } else {
                result++;
                i = loc + target.length();
                strCheck = strCheck.substring(i);
            }
        }
        return result;
    }

    /**
     * <p>
     * ���ڿ��� �߶� ����� �Ǵ� ���ڿ��� ���ԵǾ� �ִ��� üũ�Ѵ�.
     * </p>
     * 
     * @param source
     *            ���� ���ڿ�
     * @param target
     *            ����ڿ�
     * @param regex
     *            ������
     * @return
     */
    public static boolean stringSplitSearch(String source,
        String target,
        String regex) {
        boolean result = false;

        String[] strs = source.split(StringUtil.null2string(regex, ","));

        for (int i = 0; i < strs.length; i++) {
            if (strs[i].equals(target)) {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * <p>
     * �迭�� �޾� ����� ���ڿ��� �����Ѵ�. �̶� �� ������Ʈ ���̿� ���й��ڿ��� �߰��Ѵ�.
     * </p>
     *
     * <pre>
     * 
     * String[] source = new String[] { &quot;AAA&quot;, &quot;BBB&quot;, &quot;CCC&quot; };
     * 
     * String result = StringHelper.join(source, &quot;+&quot;);
     * </pre>
     * 
     * <code>result</code>�� <code>"AAA+BBB+CCC"</code>�� ������ �ȴ�.
     *
     * @param aryObj
     *            ���ڿ��� ���� �迭
     * @param delim
     *            �� ������Ʈ�� ���� ���ڿ�
     * @return ����� ���ڿ�
     */
    public static String join(Object aryObj[],
        String delim) {
        StringBuffer stringbuffer = new StringBuffer();
        int i = aryObj.length;
        if (i > 0) {
            stringbuffer.append(aryObj[0].toString());
        }
        for (int j = 1; j < i; j++) {
            stringbuffer.append(delim);
            stringbuffer.append(aryObj[j].toString());
        }

        return stringbuffer.toString();
    }

    /**
     * <p>
     * ���ڿ��� byte ���� üũ �Ѵ�. (�ѱ��� 2byte ���)
     * </p>
     *
     * <pre>
     * 
     * String source = &quot;A123456BB&quot;;
     * 
     * int result = StringHelper.getByteLength(source);
     * </pre>
     * 
     * <code>result</code>�� <code>10</code>�� ������ �ȴ�.
     *
     * @param source
     *            ���ڿ�
     * @return ���ڿ��� ����
     */
    public static int getByteLength(String source) {
        return source.getBytes().length;
    }

    /**
     * <p>
     * ���ڿ��� Null �̳� ���� ���ڿ� ���θ� �Ǵ��Ѵ�. isTrim = true;
     * </p>
     *
     * <pre>
     * 
     * String source = &quot;&quot;;
     * 
     * boolean result = StringHelper.isNull(source);
     * </pre>
     * 
     * <code>result</code>�� <code>true</code> �� ������ �ȴ�.
     *
     * @param source
     *            ���ڿ�
     * @return NULL("", null) ����
     */
    public static boolean isNull(String source) {
        return isNull(source, true);
    }

    /**
     * <p>
     * ���ڿ��� Null �̳� ���� ���ڿ� ���θ� �Ǵ��Ѵ�.
     * </p>
     *
     * <pre>
     * 
     * String source = &quot;&quot;;
     * 
     * boolean result = StringHelper.isNull(source);
     * </pre>
     * 
     * <code>result</code>�� <code>true</code> �� ������ �ȴ�.
     *
     * @param source
     *            ���ڿ�
     * @param isTrim
     *            Trim ���� (default�� true);
     * @return NULL("",null) ����
     */
    public static boolean isNull(String source,
        boolean isTrim) {
        boolean isNullString = false;

        if (isTrim && source != null && !source.isEmpty()) {
            source = source.trim();
        }

        if (source == null || source.isEmpty()) {
            isNullString = true;
        }

        return isNullString;
    }

    /**
     * <p>
     * �迭�� Vector�� �����.
     * </p>
     *
     * <pre>
     * 
     * String[] source = new String[] { &quot;AAA&quot;, &quot;BBB&quot;, &quot;CCC&quot;, &quot;DDD&quot; };
     * 
     * Vector result = StringHelper.toVector(source);
     * </pre>
     * 
     * <code>result</code>�� <code>[AAA,BBB,CCC,DDD]</code>
     *
     * @param array
     *            ���� �迭
     * @return �迭�� ���� ������ ������ Vector
     */
    public static <T> Vector<T> toVector(T[] array) {
        if (array == null) {
            return null;
        }

        Vector<T> vec = new Vector<T>(array.length);

        for (int i = 0; i < array.length; i++) {
            vec.add(i, array[i]);
        }
        return vec;
    }

    /**
     * <p>
     * ���ڿ��� �迭�� �����Ѵ�.
     * </p>
     *
     * <pre>
     * String[] source = new String[] { &quot;CCC&quot;, &quot;BBB&quot;, &quot;DDD&quot;, &quot;AAA&quot; };
     * StringHelper.sortStringArray(source);
     * </pre>
     * 
     * <code>source</code>�� <code>[AAA,BBB,CCC,DDD]</code>
     *
     * @param source
     *            ������ ���ڿ��� �迭
     */
    public static void sortStringArray(String[] source) {

        java.util.Arrays.sort(source);
    }

    /**
     * <p>
     * ���ڿ��� Enemration�� ���ĵ� �迭�� ��ȯ�Ѵ�.
     * </p>
     *
     * @param source
     *            �����ϰ��� �ϴ� Enumeration
     * @return ���ĵ� ���ڿ� �迭
     */
    public static String[] sortStringArray(Enumeration<String> source) {
        Vector<String> buf = new Vector<String>();
        while (source.hasMoreElements()) {
            buf.add(source.nextElement());
        }
        String[] buf2 = new String[buf.size()];

        for (int i = 0; i < buf.size(); i++) {
            buf2[i] = buf.get(i);
        }
        java.util.Arrays.sort(buf2);
        return buf2;
    }

    /**
     * <p>
     * ���ڿ��� �޾� null�̳� ���ڿ��� space�� �̷��� " " - ���ڿ� �� ��� ""���� ��ȯ �Ѵ�. �ش� ���� ���� ��� ���� ���ڿ��� ���� �Ѵ�.
     * </p>
     *
     * <pre>
     * 
     * String source = null;
     * 
     * String result = StringHelper.null2void(source);
     * </pre>
     * 
     * <code>result</code>�� <code>""</code>�� ������ �ȴ�.
     *
     * @param source
     *            ���ڿ�
     * @return source ��ȯ�� ���ڿ�
     */
    public static String null2void(String source) {
        if (isNull(source)) {
            source = "";
        }
        return source;
    }

    /**
     * ��ȸ��, ����form�� �ѷ� �ٶ� ���� null�̸� ȭ�鿡 null�̶�� ������ ���� ���ֱ� ���ػ���Ѵ�.
     * 
     * @param obj
     *            null ó���� ���ڿ�
     * @return null ó���� ���ڿ�
     */
    public static String null2void(Object obj) {
        if (obj != null)
            return obj.toString();
        else
            return "";
    }

    /**
     * Object�� boolean Ÿ������ ��ȯ �� �����Ѵ�.
     * 
     * @param obj
     * @param flag
     * @return
     */
    public static boolean null2boolean(Object obj,
        boolean flag) {
        boolean returnVal = false;

        if (obj == null) {
            return flag;
        } else {
            returnVal = Boolean.parseBoolean(obj.toString());
        }

        return returnVal;
    }

    /**
     * <p>
     * �Ķ���Ͱ� null�̳� "", " " �̸� 0 ���� (Trim ���� true).
     * </p>
     *
     * <pre>
     * 
     * String source = null;
     * 
     * int result = StringHelper.null2zero(source);
     * </pre>
     * 
     * <code>result</code>�� <code>0</code>�� ������ �ȴ�.
     *
     * @param source
     *            ���ڿ�
     * @param val
     *            obj�� null �ΰ�� ġȯ��ų int��
     * @return ��ȯ�� int��
     */

    public static int null2zero(Object obj,
        int val) {
        int returnVal = 0;

        if (obj == null) {
            return val;
        } else {
            returnVal = Integer.parseInt(obj.toString());
        }

        return returnVal;
    }

    /**
     * <p>
     * �Ķ���Ͱ� null�̳� "", " " �̸� 0 ���� (Trim ���� true).
     * </p>
     *
     * <pre>
     * 
     * String source = null;
     * 
     * int result = StringHelper.null2zero(source);
     * </pre>
     * 
     * <code>result</code>�� <code>0</code>�� ������ �ȴ�.
     *
     * @param source
     *            ���ڿ�
     * @return ��ȯ�� int��
     */
    public static int null2zero(Object obj) {
        return null2zero(obj, 0);
    }

    /**
     * <p>
     * �Ķ���Ͱ� null�̳� "", " " �̸� 0.0F ���� (Trim ���� true).
     * </p>
     *
     * <pre>
     * 
     * String source = null;
     * 
     * float result = StringHelper.null2float(source);
     * </pre>
     * 
     * <code>result</code>�� <code>0.0F</code>�� ������ �ȴ�.
     *
     * @param source
     *            ���ڿ�
     * @return ��ȯ�� float��
     */
    public static float null2float(String source) {
        if (isNull(source)) {
            return 0.0F;
        }
        return Float.parseFloat(source);
    }

    /**
     * <p>
     * �Ķ���Ͱ� null�̳� "", " " �̸� 0.0D ���� (Trim ���� true).
     * </p>
     *
     * <pre>
     * 
     * String source = null;
     * 
     * double result = StringHelper.null2double(source);
     * </pre>
     * 
     * <code>result</code>�� <code>0.0D</code>�� ������ �ȴ�.
     *
     * @param source
     *            ���ڿ�
     * @return ��ȯ�� double��
     */
    public static double null2double(String source) {
        if (isNull(source)) {
            return 0.0D;
        }
        return Double.parseDouble(source);
    }

    /**
     * <p>
     * �Ķ���Ͱ� null�̳� "", " " �̸� 0L ���� (Trim ���� true).
     * </p>
     *
     * <pre>
     * 
     * String source = null;
     * 
     * long result = StringHelper.null2long(source);
     * </pre>
     * 
     * <code>result</code>�� <code>0</code>�� ������ �ȴ�.
     *
     * @param source
     *            ���ڿ�
     * @return ��ȯ�� long��
     */
    public static long null2long(String source) {
        if (isNull(source)) {
            return 0L;
        }
        return Long.parseLong(source);
    }

    /**
     * <p>
     * �Ķ���Ͱ� null�̳� "", " " �̸� value�� ����, �ƴϸ� source�� ����
     * </p>
     *
     * <pre>
     * 
     * String source = null;
     * 
     * String result = StringHelper.null2string(source, &quot;0&quot;);
     * </pre>
     * 
     * <code>result</code>�� <code>"0"</code>�� ������ �ȴ�.
     *
     * @param source
     *            ���ڿ�
     * @return ��ȯ�� ���ڿ�
     */
    public static String null2string(Object source,
        String value) {
        if (source == null) {
            return value;
        }

        if (source instanceof String) {
            if (new String(source.toString()).isEmpty()) {
                return value;
            }
        }

        return new String(source.toString());
    }

    /**
     * <p>
     * ���忭�� �񱳽� ���. ���� <strong>String</strong> Class�� <code>equals()</code> Method�� ���� �������� �ذ� �ϱ� ���� Method�̴�.
     * <p/>
     *
     * <p>
     * �Ʒ��� ���� �ΰ����� ����ϰ� ������ �� �����
     * </p>
     * 
     * <pre>
     * 1.
     * String source = null;
     * 
     * if(source == null || "".equals(source)){
     *     ...
     * }
     * 
     * 2.
     * String target = "?"
     * 
     * if(source != null && source.equals(target)) {
     *     ...
     * }
     * </pre>
     *
     * <p>
     * <code>strEquals()</code> Method�� �̿��Ͽ� ���� �� �� �ִ�.
     * </p>
     *
     * <pre>
     * 1.
     * String source = null;
     * 
     * if(StringHelper.strEquals(source, "")){
     *     ...
     * }
     * 
     * 2.
     * String target = "?";
     * 
     * if(StringHelper.strEquals(source, target)) {
     *  ...
     * }
     * </pre>
     *
     * @param source
     *            �� ���ڿ�1
     * @param target
     *            �� ���ڿ�2
     * @return boolean �� ��� (true, false)
     */
    public static boolean strEquals(String source,
        String target) {
        return null2void(source).equals(null2void(target));
    }

    /**
     * <p>
     * ������ ���ڿ��� �Ϻκ��� ������. ���� <strong>String</strong> Class�� <code>substring()</code>���� ���� �߻��ϴ� <code>NullpointException</code>�� ����Ͽ� ���� Method�̴�.
     * </p>
     *
     * <pre>
     * String source = "200403"
     * String result = StringHelper.toSubString(source, 4, 8);
     * </pre>
     *
     * <code>result</code>�� <strong>IndexOutOfBoundException</strong>�� �߻����� �ʰ� <code>"03"</code>�� ������ �ȴ�.
     *
     * @param source
     *            ���� ���ڿ�
     * @param beginIndex
     *            ������ġ
     * @param endIndex
     *            ������ ��ġ
     * @return �ش� ���ڿ�
     */
    public static String toSubString(String source,
        int beginIndex,
        int endIndex) {

        if (strEquals(source, "")) {
            return source;
        } else if (source.length() < beginIndex) {
            return "";
        } else if (source.length() < endIndex) {
            return source.substring(beginIndex);
        } else {
            return source.substring(beginIndex, endIndex);
        }

    }

    /**
     * <p>
     * ������ ���ڿ��� �Ϻκ��� ������. ���� <strong>String</strong> Class�� <code>substring()</code>���� ���� �߻��ϴ� <code>NullpointException</code>�� ����Ͽ� ���� Method�̴�.
     * </p>
     *
     * <pre>
     * String source = "200403"
     * String result = StringHelper.toSubString(source, 8);
     * </pre>
     * 
     * <code>result</code>�� <strong>IndexOutOfBoundException</strong>�� �߻����� �ʰ� <code>""</code>�� ������ �ȴ�.
     *
     * @param source
     *            ���� ���ڿ�
     * @param beginIndex
     *            ������ġ
     * @return �ش� ���ڿ�
     */
    public static String toSubString(String source,
        int beginIndex) {

        if (strEquals(source, "")) {
            return source;
        } else if (source.length() < beginIndex) {
            return "";
        } else {
            return source.substring(beginIndex);
        }

    }

    /**
     * ���ڿ��� delims�� �������� �߶� List�� ��ȯ�Ѵ�. delims �� ��ü�� token���� ��ȯ���� �ʴ´�. ���������� StringTokenizer�� ����Ѵ�.
     * 
     * @param str
     * @param delims
     * @param returnDelims
     * @return
     */
    public static List<String> tokenize(String str,
        String delims) {
        return tokenize(str, delims, false);
    }

    /**
     * ���ڿ��� delims�� �������� �߶� List�� ��ȯ�Ѵ�. delims �� ��ü�� token���� ��ȯ��ų�� ���θ� returnDelims�� �����Ѵ�. ���������� StringTokenizer�� ����Ѵ�.
     * 
     * @param str
     * @param delims
     * @param returnDelims
     * @return
     */
    public static List<String> tokenize(String str,
        String delims,
        boolean returnDelims) {
        if (str == null) {
            return null;
        }

        List<String> tokens = new ArrayList<String>();
        if (isNull(delims)) {
            tokens.add(str);
        } else {
            StringTokenizer tokenizer = new StringTokenizer(str, delims, returnDelims);
            while (tokenizer.hasMoreElements()) {
                tokens.add(tokenizer.nextToken());
            }
        }

        return tokens;
    }

    /**
     * <p>
     * str ���ڿ��� delim�� �������� split�Ͽ� List�� ����ش�. <code>String.split(String regexp)</code>������ delimiter�� reqular expression���� ǥ���ϴµ� ���� �� �Լ��� delimiter�� �״�� split�ϹǷ� ������ �����Ѵ�.
     * </p>
     * 
     * @param str
     *            ������ ���ڿ�
     * @param delim
     *            ���ڿ��� �����ϱ� ���� ������
     * @return List ��ü
     */
    public static List<String> split(String str,
        String delim) {
        if (str == null) {
            return null;
        }
        if (delim == null) {
            delim = "";
        }

        int beginIdx = 0;
        int endIdx = 0;
        List<String> splitList = new ArrayList<String>();
        int strLen = str.length();
        int delimLen = delim.length();

        do {
            endIdx = str.indexOf(delim, beginIdx);
            if (endIdx >= 0) {
                splitList.add(str.substring(beginIdx, endIdx));
            } else {
                splitList.add(str.substring(beginIdx, strLen));
            }
            beginIdx = endIdx + delimLen;
        } while (endIdx >= 0);

        return splitList;
    }

    /**
     * ���ڿ��� delim �������� split�Ͽ� �־��� dest �迭�� ��´�. dest �迭�� ũ�� ��ŭ ���� �Ǹ�, �� �̻��� ���õȴ�. ���� �迭�� ũ�� ���� ���� ������ �Ǹ� ���ڶ�� �κ��� defStr�� ä������.
     * 
     * @param str
     *            ������ ����
     * @param delim
     *            null �̸� ���鹮��(" ")�� ����Ѵ�.
     * @param dest
     *            ���ҵ� ���ڰ� ��� �迭 ��ü
     * @param defStr
     *            ���� ������ ���ڶ� ��� ä��� ���� ����� ���ڿ�
     */
    public static void split(String str,
        String delim,
        String[] dest,
        String defStr) {
        if (dest == null || dest.length == 0) {
            return;
        }
        int cnt = dest.length;

        if (str == null) {
            str = "";
        }
        if (delim == null) {
            delim = " ";
        }

        int beginIdx = 0;
        int endIdx = 0;

        int strLen = str.length();
        int delimLen = delim.length();
        int count = 0;
        do {
            endIdx = str.indexOf(delim, beginIdx);
            if (endIdx > 0) {
                dest[count] = str.substring(beginIdx, endIdx);
            } else {
                dest[count] = str.substring(beginIdx, strLen);
            }
            beginIdx = endIdx + delimLen;
            count++;
        } while (endIdx > 0 && count < cnt);

        for (int i = count; i < cnt; i++) {
            dest[i] = defStr;
        }
    }

    /**
     * <p>
     * str ���ڿ��� delim�� �������� split�Ͽ� List�� ����ش�. cnt ���� ��ŭ�� ����, �� �̻��� ���õȴ�. ���� cnt ���� ���� ������ �Ǹ� ���ڶ�� �κ��� defStr�� ä������. <code>String.split(String regexp)</code>������ delimiter�� reqular expression���� ǥ���ϴµ�
     * ���� �� �Լ��� delimiter�� �״�� split�ϹǷ� �ξ� ������ �����Ѵ�.
     * </p>
     * 
     * @param str
     *            ������ ���ڿ�
     * @param delim
     *            ���ڿ��� �����ϱ� ���� ������, null �̸� ���鹮��(" ")�� ����Ѵ�.
     * @param cnt
     *            ���� ����
     * @param defStr
     *            ���� ������ ���ڶ� ��� ä��� ���� ����� ���ڿ�
     * @return List ��ü
     */
    public static List<String> split(String str,
        String delim,
        int cnt,
        String defStr) {
        if (str == null) {
            return null;
        }
        if (delim == null) {
            delim = " ";
        }
        if (cnt <= 0) {
            return new ArrayList<String>();
        }
        List<String> splitList = new ArrayList<String>(cnt);

        int beginIdx = 0;
        int endIdx = 0;

        int strLen = str.length();
        int delimLen = delim.length();
        int count = 0;
        do {
            endIdx = str.indexOf(delim, beginIdx);
            if (endIdx > 0) {
                splitList.add(str.substring(beginIdx, endIdx));
            } else {
                splitList.add(str.substring(beginIdx, strLen));
            }
            beginIdx = endIdx + delimLen;
            count++;
        } while (endIdx > 0 && count < cnt);

        for (int i = count; i < cnt; i++) {
            splitList.add(defStr);
        }

        return splitList;
    }

    /**
     * <p>
     * �ش� ���ڿ��� ������ WhiteSpace({@link java.lang.Character#isWhitespace})�� �ش� ���ڷ� ��ü.
     * </p>
     *
     * <pre>
     * 
     * String source = &quot;  300  &quot;;
     * 
     * String repStr = &quot;0&quot;;
     * 
     * String result = StringHelper.lSpaceReplace(source, repStr);
     * </pre>
     * 
     * <code>result</code>�� <code>"00300  "</code> �� ������ �ȴ�.
     *
     * @param source
     *            ���ڿ�
     * @param repStr
     *            ��ü ���ڿ�
     * @return ��ü�� ���ڿ�
     */
    public static String replaceLeftSpace(String source,
        String repStr) {
        Matcher matcher = Pattern.compile("^\\s*").matcher(source);

        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            byte[] findGroup = matcher.group().getBytes();
            for (int i = 0; i < findGroup.length; i++) {
                if (Character.isWhitespace((char) findGroup[i])) {
                    sb.append(repStr);
                }
            }
        }

        return sb.toString().concat(matcher.replaceAll(""));
    }

    /**
     * <p>
     * �ش� ���ڿ��� �������� WhiteSpace({@link java.lang.Character#isWhitespace})�� �ش� ���ڷ� ��ü.
     * </p>
     *
     * <pre>
     * 
     * String source = &quot;  300  &quot;;
     * 
     * String repStr = &quot;0&quot;;
     * 
     * String result = StringHelperExt.rSpaceReplace(source, repStr);
     * </pre>
     * 
     * <code>result</code>�� <code>"  30000"</code> �� ������ �ȴ�.
     *
     * @param source
     *            ���ڿ�
     * @param repStr
     *            ��ü ���ڿ�
     * @return ��ü�� ���ڿ�
     */
    public static String replaceRightSpace(String source,
        String repStr) {

        Matcher matcher = Pattern.compile("\\s*$").matcher(source);

        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            char[] findGroup = matcher.group().toCharArray();
            for (int i = 0; i < findGroup.length; i++) {
                if (Character.isWhitespace(findGroup[i])) {
                    sb.append(repStr);
                }
            }
        }

        return matcher.replaceAll("").concat(sb.toString());
    }

    private static final Pattern unicodePattern = Pattern.compile("\\\\u([0-9A-Fa-f]{4})");

    /**
     * Unicode �� ǥ���� ���ڵ��� ���� ���ڷ� �����Ѵ�. ���� ��� "Hello\u003aWorld"�� ���� Unicode ǥ���� �� ���ڿ��� �ѱ�� ���� ���ڿ��� �����̵� "Hello:World"�� ����Ǿ� ��ȯ�ȴ�.
     * 
     * @param s
     *            Unicode ǥ���� �ִ� ���ڿ�
     * @return ���� ���ڷ� ����� ���
     */
    public static String unescapeUnicode(String s) {
        String res = s;
        Matcher m = unicodePattern.matcher(res);

        while (m.find()) {
            res = res.replaceAll("\\" + m.group(0), Character.toString((char) Integer.parseInt(m.group(1), 16)));
        }
        return res;
    }

    public static String toDbStyle(String name) {
        StringBuffer sb = new StringBuffer(name.replace('.', '_'));
        for (int i = 1; i < sb.length() - 1; i++) {
            if (Character.isLowerCase(sb.charAt(i - 1)) && Character.isUpperCase(sb.charAt(i)) && Character.isLowerCase(sb.charAt(i + 1))) {
                sb.insert(i++, '_');
            }
        }
        return sb.toString().toUpperCase();
    }

    public static boolean getBoolean(String value,
        boolean defaultValue) {
        if (value == null)
            return defaultValue;
        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("on") || value.equalsIgnoreCase("y") || value.equalsIgnoreCase("t") || value.equalsIgnoreCase("yes"))
            return true;
        return false;
    }

    /**
     * �Է¹��� ���ڿ��� null�̰ų� ""(empty-String) ���� �Ǵ��Ͽ� null �̸� true ���� ������ false ���� �մϴ�. �־��� �����Ϳ� ���ؼ� trim() ���� �ʽ��ϴ�.
     * 
     * @param str
     *            üũ ��� ���ڿ�
     * @return null Ȥ�� empty-String �̸� true, �ƴϸ� false
     * @since 2005.11.02
     * @author Helexis
     */
    public static boolean isNullNotTrim(String str) {
        if (str == null || str.length() == 0)
            return true;
        return false;
    }

    /**
     * �Ϲ� ���ڿ��� xml �ȿ� �ֱ� ���� ��ƼƼ�� ��ȯ �Ѵ�.
     * 
     * @param str
     * @return
     */
    public static String escapeXml(String str) {
        str = str.replaceAll("&", "&amp;"); // �̰� �Ʒ��� ���� �߸��ȴ�.
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");
        str = str.replaceAll("\"", "&quot;");
        str = str.replaceAll("'", "&apos;");
        return str;
    }

    /**
     * �����ǵ�� Ŀ���� ������ ������ �� �������� escape���ڸ� ��ȯ�� �� �����Ѵ�.
     * 
     * @param str
     * @return
     */
    public static String unescapeWithLine(String str) {
        String reStr = str;
        String[] oStr = { "&amp;", "&lt;", "&gt;", "&quot;", "&apos;", "\n", "\r" };
        String[] nStr = { "&", "<", ">", "\"", "'", "", "" };

        for (int i = 0; i < 5; i++) {
            reStr = replace(reStr, oStr[i], nStr[i]);
        }

        return reStr;
    }

    /**
     * escape���ڸ� ��ȯ�� �� �����Ѵ�.
     * 
     * @param str
     * @return
     */
    public static String unescape(String str) {
        String reStr = str;
        String[] oStr = { "&amp;", "&lt;", "&gt;", "&quot;", "&apos;" };
        String[] nStr = { "&", "<", ">", "\"", "'" };

        for (int i = 0; i < oStr.length; i++) {
            reStr = reStr.replaceAll(oStr[i], nStr[i]);
        }

        return reStr;
    }

    public static String replace(String source,
        String sOld,
        String sNew) {
        String target = source;
        String sOldStr = sOld;
        String sNewStr = sNew;
        int mPos = 0;
        int mOffset = 0;

        if (sOldStr == null || StringUtil.isNull(sOldStr))
            return target;
        if (sNewStr == null)
            sNewStr = "";

        while ((mPos = (target.substring(mOffset, target.length())).indexOf(sOldStr)) > -1) {
            target = target.substring(0, mOffset + mPos) + sNewStr + target.substring(mOffset + mPos + sOldStr.length(), target.length());
            mOffset = mOffset + mPos + sNewStr.length();
        }

        return target;
    }

    /**
     * 
     * @param text
     * @return
     */
    public static String get8859_1(String text) {
        String rtn = new String("");
        System.out.println("get8859_1 : " + text);
        if (text == null) {
            return rtn;
        } else {
            try {
                System.out.println("get8859_11 : " + new String(new String(text.getBytes("UTF-8"), "ISO-8859-1")));
                return new String(text.getBytes("UTF-8"), "ISO-8859-1");
            } catch (UnsupportedEncodingException UEE) {
                return rtn;
            }
        }
    }

    public static String stripXSS(String value) {
        if (value != null) {
            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
            // avoid encoded attacks.
            // value = ESAPI.encoder().canonicalize(value);

            // Avoid null characters
            value = value.replaceAll("", "");

            // Avoid anything between script tags
            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid anything in a src='...' type of expression
            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Remove any lonesome </script> tag
            scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            // Remove any lonesome <script ...> tag
            scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid eval(...) expressions
            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid expression(...) expressions
            scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid javascript:... expressions
            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid vbscript:... expressions
            scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid onload= expressions
            scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
        }
        return value;
    }

    /**
     * <p>
     * Get the value of a string as a delimiter
     * </p>
     * @param str
     * @param delimiter
     * @param index
     * @return
     */
    public static String getSplitIndex(String str,
        String delimiter,
        int index) {
        String[] paths = str.split(delimiter);
        String value = "";
        if (paths.length > 0) {
            value = paths[index];
        }

        return value;
    }
}
