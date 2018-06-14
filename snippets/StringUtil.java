package snippets;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static String[] divide(String target, String cutStr) {
		if (target == null)
			return new String[] { "", "" };
		if (cutStr == null || cutStr.length() == 0)
			return new String[] { target, "" };
		int idx = target.indexOf(cutStr);
		if (idx < 0)
			return new String[] { target, "" };
		else
			return new String[] { target.substring(0, idx), target.substring(idx + cutStr.length()) };
	}

	public static String erase(String str, String delim) {
		if (str == null || delim == null)
			return str;
		StringTokenizer tor = new StringTokenizer(str, delim);
		StringBuffer sb = new StringBuffer(str.length());
		while (tor.hasMoreTokens()) {
			sb.append(tor.nextToken());
		}
		return sb.toString();
	}

	public static String limiting(String s, int max) {
		if (s == null)
			return null;
		if (s.length() > max)
			return s.substring(0, max);
		return s;
	}

	// TODO: 추후 중복 제거 필요
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    // TODO: 추후 중복 제거 필요
    public static String trimToEmpty(final String str) {
        return str == null ? "" : str.trim();
    }
    
    /**
	 * CharSequence가 공백인지 여부를 반환한다.
	 * @param cs 공백여부를 판단할 CharSequence 객체
	 * @return 공백여부
	 */
	public static boolean isBlank(CharSequence cs) {
		if(cs == null) {
			return true;
		}
		
		int strLen = cs.length();
		if (strLen == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(cs.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	

    private static final String EMPTY_STRING = "";

    /**
     * Y/N 을 예/아니오 로 변환한다.
     *
     * @param ynStr Y/N
     * @return 예/아니오
     */
    public static String convertYn(String ynStr) {
        if (ynStr == null) return "";
        if (ynStr.trim().toUpperCase().equals("Y")) return "예";
        if (ynStr.trim().toUpperCase().equals("N")) return "아니오";
        return "";
    }

    /**
     * 문자열의 특정 위치에 추가할 문자열을 붙인다.
     *
     * <pre>
     * insert("abc", 2, "d") -> abdc
     * insert("abc", -1, "d") -> abdc
     * </pre>
     *
     * @param string 문자열
     * @param location 지정문자열을 추가할 위치. 대상문자열의 첫문자 위치는 0으로 시작한다.('abc'의 c는 location 값이 2)
     *            location이 0 보다 작은 경우, 문자열의 끝자리를 0으로 시작한다.
     * @param add 추가할 문자열
     * @return 추가 완료된 문자열
     */
    public static String insert(String string, int location, String add) {
        String result = null;
        int insertPosition = location;

        StringBuilder strBuf = new StringBuilder();
        int length = string.length();
        if (insertPosition >= 0) {
            if (length < insertPosition) {
            	insertPosition = length;
            }
            strBuf.append(string.substring(0, insertPosition));
            strBuf.append(add);
            strBuf.append(string.substring(insertPosition));
        } else {
            if (length < Math.abs(insertPosition)) {
            	insertPosition = length * (-1);
            }
            strBuf.append(string.substring(0, (length - 1) + insertPosition));
            strBuf.append(add);
            strBuf.append(string.substring((length - 1) + insertPosition));
        }
        result = strBuf.toString();

        return result;
    }

    /**
     * 문자열의 특정 위치에 추가할 문자열을 붙인다. 단 추가되는 문자열의 길이만큼 기존의 문자열에 값을 overwrite한다.
     *
     * <pre>
     * insert("abc", 2, "d") -> abd
     * insert("abc", -1, "d") -> abd
     * </pre>
     *
     * @param string 문자열
     * @param location 지정문자열을 추가할 위치. 대상문자열의 첫문자 위치는 0으로 시작한다.('abc'의 c는 location 값이 2)
     *            location이 0 보다 작은 경우, 문자열의 끝에서 부터 역순으로 count한 위치가 된다.
     * @param add 추가할 문자열
     * @return 추가 완료된 문자열
     */
    public static String insertAndOverwrite(String string, int location, String add) {
    	 int insertPosition = location;
        String result = null;

        StringBuilder strBuf = new StringBuilder();
        int lengthSize = string.length();
        if (insertPosition >= 0) {
            if (lengthSize < insertPosition) {
            	insertPosition = lengthSize;
            }
            strBuf.append(string.substring(0, insertPosition));
            strBuf.append(add);
            strBuf.append(string.substring(insertPosition + add.length()));
        } else {
            if (lengthSize < Math.abs(insertPosition)) {
            	insertPosition = lengthSize * (-1);
            }
            strBuf.append(string.substring(0, (lengthSize - 1) + insertPosition));
            strBuf.append(add);
            strBuf.append(string.substring((lengthSize - 1) + insertPosition + add.length()));
        }
        result = strBuf.toString();

        return result;
    }

    /**
     * 원본 문자열에 포함된 특정 문자열을 제거한다.
     *
     * @param source 문자열
     * @param pattern 삭제할 문자열
     * @return 새로 변환된 문자열
     */
    public static String delete(String source, String pattern) {
        return replace(source, pattern, "");
    }

    /**
     * 문자열에 포함된 특정 문자열을 새로운 문자열로 변환한다.
     *
     * @param source 문자열
     * @param pattern 원본 문자열에 포함된 특정 문자열
     * @param replacement 변환할 문자열
     * @return 새로운 문자열로 변환된 문자열
     */
    public static String replace(String source, String pattern, String replacement) {
        StringBuilder rtnStr = new StringBuilder();
        String preStr = "";
        String nextStr = source;
        String srcStr = source;

        while (srcStr.indexOf(pattern) >= 0) {
            preStr = srcStr.substring(0, srcStr.indexOf(pattern));
            nextStr = srcStr.substring(srcStr.indexOf(pattern) + pattern.length(), srcStr.length());
            srcStr = nextStr;
            rtnStr.append(preStr).append(replacement);
        }
        rtnStr.append(nextStr);
        return rtnStr.toString();
    }

    /**
     * 문자열을 지정한 분리자를 기준으로 분리하여 배열로 반환한다.
     *
     * @param string 문자열
     * @param separator 분리자
     * @return 분리자로 나뉘어진 문자열 배열(문자열에 null 입력시 빈스트링 배열 반환)
     * @throws NullPointerException
     */
    public static String[] split(String string, String separator) throws NullPointerException {
        if (string == null) return new String[] {};
        if (separator == null || separator.length() == 0) return new String[] { string };

        String[] returnVal = null;
        int cnt = 1;

        int index = string.indexOf(separator);
        int index0 = 0;
        while (index >= 0) {
            cnt++;
            index = string.indexOf(separator, index + 1);
        }
        returnVal = new String[cnt];
        cnt = 0;
        index = string.indexOf(separator);
        while (index >= 0) {
            returnVal[cnt] = string.substring(index0, index);
            index0 = index + 1;
            index = string.indexOf(separator, index + 1);
            cnt++;
        }
        returnVal[cnt] = string.substring(index0);

        return returnVal;
    }

    /**
     * 문자열을 지정한 분리자를 기준으로 분리하여 배열로 반환한다.
     *
     * @param source 문자열
     * @param separator 분리자
     * @param containNull 구분되어진 문자열 중 공백문자열의 포함여부. true : 포함, false : 포함하지 않음.
     * @return 분리된 문자열의 스트링 배열
     */
    public static String[] split(String source, String separator, boolean containNull) {
        // StringTokenizer는 구분자가 연속으로 중첩되어 있을 경우 공백 문자열을 반환하지 않음.
        // 따라서 아래와 같이 작성함.

        ArrayList<String> matchList = new ArrayList<String>();

        Pattern regex = Pattern.compile(separator);
        Matcher matcher = regex.matcher(source);

        int inx = 0;
        while (matcher.find()) {
            String match = source.subSequence(inx, matcher.start()).toString();
            matchList.add(match);
            inx = matcher.end();
        }

        if (inx == 0) { return new String[] { source }; }

        matchList.add(source.subSequence(inx, source.length()).toString());

        int resultSize = matchList.size();
        if (!containNull) {
            for (int jnx = 0; jnx < matchList.size(); jnx++) {
                if (matchList.get(jnx).equals("")) {
                    resultSize--;
                }
            }
        }

        String[] returnVal = new String[resultSize];
        return matchList.subList(0, resultSize).toArray(returnVal);
    }

    /**
     * 문자열을 지정한 분리자를 분리한 후, 주어진 배열의 크기만큼 반환한다.
     *
     * @param source 문자열
     * @param separator 분리자
     * @param arraylength 배열 길이
     * @return 분리자로 나뉘어진 문자열 배열
     * @throws NullPointerException
     */
    public static String[] split(String source, String separator, int arraylength) throws NullPointerException {
        String[] returnVal = new String[arraylength];
        int cnt = 0;
        int index0 = 0;
        int index = source.indexOf(separator);
        while (index >= 0 && cnt < (arraylength - 1)) {
            returnVal[cnt] = source.substring(index0, index);
            index0 = index + 1;
            index = source.indexOf(separator, index + 1);
            cnt++;
        }
        returnVal[cnt] = source.substring(index0);
        if (cnt < (arraylength - 1)) {
            for (int inx = cnt + 1; inx < arraylength; inx++) {
                returnVal[inx] = "";
            }
        }

        return returnVal;
    }

    /**
     * 문자열을 지정한 분리크기(cutLength)에 의해 Byte 단위로 분리하여 배열로 반환한다.<br>
     * 한글의 경우 UTF-8 인코딩일 때는 3byte, 아닌 경우 2byte를 기준으로 한다.
     *
     * @param source 원본 문자열
     * @param cutLength 문자열을 분리할 크기
     * @param encoding 인코딩
     * @return 분리자로 나뉘어진 문자열 배열
     * @throws UnsupportedEncodingException
     */
    public static String[] splitByBytes(String source, int cutLength, String encoding) throws UnsupportedEncodingException {
        if (source == null) return null;

        String[] resultStrArray = null;

        byte[] byteStr = source.getBytes(encoding);
        int byteLength = byteStr.length;
        int koreanByteLength = encoding.equalsIgnoreCase("UTF-8") ? 3 : 2;

        int index = 0;
        int targetBytelength = 0;
        int offset = 0;

        if (byteLength > cutLength) {
            int arrayLength = (byteLength / cutLength) + (byteLength % cutLength == 0 ? 0 : 1);
            resultStrArray = new String[arrayLength];

            for (int inx = 0; inx < arrayLength; inx++) {
                targetBytelength = 0;
                offset = cutLength;

                if (index + offset > byteStr.length) {
                    offset = byteStr.length - index;
                }

                for (int jnx = 0; jnx < offset; jnx++) {
                    if (((int)byteStr[index + jnx] & 0x80) != 0) {
                        targetBytelength++;
                    }
                }

                if (targetBytelength % koreanByteLength != 0) {
                    offset -= targetBytelength % koreanByteLength;
                }

                resultStrArray[inx] = new String(byteStr, index, offset, encoding);
                index += offset;
            }
        } else {
            resultStrArray = new String[] { source };
        }

        return resultStrArray;
    }

    /**
     * 문자열에서 검색어가 조회된 횟수를 반환한다.
     *
     * @param source 문자열
     * @param search 검색어
     * @return 검색어가 검색된 횟수
     */
    public static int search(String source, String search) {
        int result = 0;
        String strCheck = source;
        for (int inx = 0; inx < source.length();) {
            int loc = strCheck.indexOf(search);
            if (loc == -1) {
                break;
            } else {
                result++;
                inx = loc + search.length();
                strCheck = strCheck.substring(inx);
            }
        }
        return result;
    }

    /**
     * 문자열에 포함된 '$'에 back-slash('\\') 를 추가한다.
     *
     * @param source 문자열
     * @return 변경된 문자열
     */
    public static String replaceEscapingDollarSign(String source) {
        return replace(source, "$", "\\$");
    }

    /**
     * 문자열을 분리자로 분리한 후, index에 해당하는 값(token)을 반환한다.
     *
     * @param source 문자열
     * @param separator 분리자
     * @param index index
     * @return index에 해당하는 token
     */
    public static String token(String source, String separator, int index) {
        if (source == null) return null;
        StringTokenizer stringTokenizer = new StringTokenizer(source, separator);
        int inx = 0;
        while (stringTokenizer.hasMoreTokens()) {
            if ((inx++) == index) {
                return stringTokenizer.nextToken();
            } else {
                stringTokenizer.nextToken();
            }
        }
        return null;
    }

//    /**
//     * 문자열을 분리자로 나뉜 두개의 문자열 배열로 반환한다.
//     *
//     * @param source 문자열
//     * @param separator 분리자
//     * @return 분리자로 나뉘어진 문자열 배열
//     */
//    public static String[] divide(String source, String separator) {
//        if (source == null) return new String[] { "", "" };
//        if (separator == null || separator.length() == 0) return new String[] { source, "" };
//        int idx = source.indexOf(separator);
//        if (idx < 0) {
//            return new String[] { source, "" };
//        } else {
//            return new String[] { source.substring(0, idx), source.substring(idx + separator.length()) };
//        }
//    }

    /**
     * 문자열의 첫글자를 소문자로 변경한다.
     *
     * @param source 문자열
     * @return 변경된 문자열
     */
    public static String toLowerCaseFirstLetter(String source) {
        if (source == null || source.length() < 1) return source;
        return (source.substring(0, 1).toLowerCase() + source.substring(1));
    }

    /**
     * 지정된 길이까지 문자열의 왼쪽에 특정 문자를 덧붙인다.
     *
     * @param source 문자열
     * @param pad 추가할 문자
     * @param length 문자열 길이
     * @return 특정 문자로 채워진 문자열
     */
    public static String leftPad(String source, byte pad, int length) {
    	 String sourceStr = source;
        if (sourceStr == null) sourceStr = "";
        byte[] byteString;
        try {
            byteString = sourceStr.getBytes("ksc5601");
        } catch (Exception e) {
            return sourceStr;
        }
        if (length <= byteString.length) return sourceStr;
        byte[] chs = new byte[length];
        int jnx = length - 1;
        for (int inx = byteString.length - 1; inx >= 0; inx--) {
            chs[jnx--] = byteString[inx];
        }
        for (; jnx >= 0; jnx--) {
            chs[jnx] = pad;
        }
        try {
            return new String(chs, "ksc5601");
        } catch (Exception e) {
            return sourceStr;
        }
    }

    /**
     * 지정된 길이까지 문자열의 오른쪽에 특정 문자를 덧붙인다.
     *
     * @param source 문자열
     * @param pad 추가할 문자
     * @param length 문자열 길이
     * @return 특정 문자로 채워진 문자열
     */
    public static String rightPad(String source, byte pad, int length) {
    	 String sourceStr = source;
        if (sourceStr == null) sourceStr = "";
        byte[] byteString;
        try {
            byteString = sourceStr.getBytes("ksc5601");
        } catch (Exception e) {
            return sourceStr;
        }
        if (length <= byteString.length) return sourceStr;
        byte[] chs = new byte[length];
        int jnx = 0;
        for (int inx = 0; inx < byteString.length; inx++) {
            chs[jnx++] = byteString[inx];
        }
        for (; jnx < length; jnx++) {
            chs[jnx] = pad;
        }
        try {
            return new String(chs, "ksc5601");
        } catch (Exception e) {
            return sourceStr;
        }
    }

    /**
     * 입력된 스트링에서 특정 문자를 제거한다.
     *
     * @param string 문자열
     * @param separator 분리자
     * @return 특정 문자가 제거된 문자열
     */
    public static String strip(String string, String separator) {
        if (string == null || string.length() == 0 || separator == null) return string;
        StringBuilder buf = new StringBuilder();
        StringTokenizer st = new StringTokenizer(string, separator);
        while (st.hasMoreTokens()) {
            buf.append(st.nextToken());
        }
        return buf.toString();
    }

    /**
     * 문자열에서 '-'을 찾아 제거한다.
     *
     * @param string '-'이 포함된 문자열
     * @return '-'가 제거된 문자열
     */
    public static String stripHyphen(String string) {
    	 String changeStr = string;
        if (changeStr == null) return null;
        for (;;) {
            int index = changeStr.indexOf("-");
            if (index >= 0) {
            	changeStr = changeStr.substring(0, index) + changeStr.substring(index + 1);
            } else {
                break;
            }
        }
        return changeStr;
    }

    /**
     * 문자열의 첫글자를 대문자로 변경한다.
     *
     * @param source 문자열
     * @return 변경된 문자열
     */
    public static String toUpperCaseFirstLetter(String source) {
        if (source == null || source.length() < 1) return source;
        return (source.substring(0, 1).toUpperCase() + source.substring(1));
    }

    /**
     * 문자열 배열을 분리자로 연결한 문자열로 반환한다.
     *
     * @param values 문자열 배열
     * @param separator 분리자
     * @return 문자열 배열을 분리자로 연결한 문자열
     */
    public static String arrayToString(String[] values, String separator) {
        StringBuilder sbf = new StringBuilder();
        if (values == null || values.length < 1) return "";
        sbf.append(values[0]);
        for (int inx = 1; inx < values.length; inx++) {
            sbf.append(separator).append(values[inx]);
        }
        return sbf.toString();
    }

    /**
     * java String을 mysql에 적합한 String으로 변환한다.
     *
     * @param string java String
     * @return mySQL용 String
     */
    public static String java2mysql(String string) {
        if (string == null) return null;
        StringBuilder buf = new StringBuilder();
        char[] cha = string.toCharArray();
        int len = cha.length;
        for (int inx = 0; inx < len; inx++) {
            if (cha[inx] == '\n') {
                buf.append("\\n");
            } else if (cha[inx] == '\t') {
                buf.append("\\t");
            } else if (cha[inx] == '\r') {
                buf.append("\\r");
            } else if (cha[inx] == '\'') {
                buf.append("\\'");
            } else if (cha[inx] == '"') {
                buf.append("\\\"");
            } else if (cha[inx] == '%') {
                buf.append("\\%");
            } else {
                buf.append(cha[inx]);
            }
        }
        return buf.toString();
    }

    /**
     * java String을 alert(script)에 적합한 String으로 변환한다.
     *
     * @param string java String
     * @return String alert(script)용 String
     */
    public static String java2alert(String string) {
        if (string == null) return null;
        StringBuilder buf = new StringBuilder();
        char[] cha = string.toCharArray();
        int len = cha.length;
        for (int inx = 0; inx < len; inx++) {
            if (cha[inx] == '\n') {
                buf.append("\\n");
            } else if (cha[inx] == '\t') {
                buf.append("\\t");
            } else if (cha[inx] == '"') {
                buf.append("'");
            } else {
                buf.append(cha[inx]);
            }
        }
        return buf.toString();
    }

    /**
     * String을 message(LMessage)에 적합한 String으로 변환한다.
     *
     * @param string java String
     * @return String message(LMessage)용 String
     */
    public static String java2msg(String string) {
        if (string == null) return null;
        StringBuilder buf = new StringBuilder();
        char[] cha = string.toCharArray();
        int len = cha.length;
        for (int inx = 0; inx < len; inx++) {
            if (cha[inx] == '\n') {
                buf.append("\\n");
            } else if (cha[inx] == '\t') {
                buf.append("\\t");
            } else {
                buf.append(cha[inx]);
            }
        }
        return buf.toString();
    }

    /**
     * String을 html에 적합한 String으로 변환한다.
     *
     * @param string java String
     * @return String html용 String
     */
    public static String java2html(String string) {
        if (string == null) return null;
        StringBuilder buf = new StringBuilder();
        char[] cha = string.toCharArray();
        int len = cha.length;
        for (int inx = 0; inx < len; inx++) {
            if (cha[inx] == '&') {
                buf.append("&amp;");
            } else if (cha[inx] == '<') {
                buf.append("&lt;");
            } else if (cha[inx] == '>') {
                buf.append("&gt;");
            } else if (cha[inx] == '"') {
                buf.append("&quot;");
            } else if (cha[inx] == '\'') {
                buf.append("&#039;");
            } else {
                buf.append(cha[inx]);
            }
        }
        return buf.toString();
    }

    /**
     * '\n'를 ' '으로 변경한다.
     *
     * @param string 문자열
     * @return 변경된 문자열
     */
    public static String toOneLine(String string) {
        if (string == null) {
            return null;
        } else {
            return string.replace('\n', ' ');
        }
    }

    /**
     * format을 적용한 문자열로 변환한다.
     *
     * @param string 포맷을 적용할 객체
     * @param format 문자열 포맷
     * @param locale
     * @return 변환되 문자열(Object가 null인 경우 ""을 반환)
     */
    public static String print(String string, String format) {
        if (string == null) return "";
        if (format == null) return string;

        // 데이타가 문자열인경우 ###-###형태를 사용합니다.
        int jnx = 0;
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < format.length(); i++) {
            if (format.charAt(i) == '#') {
                if (jnx >= string.length()) return buf.toString();
                buf.append(string.charAt(jnx++));
            } else {
                buf.append(format.charAt(i));
            }
        }
        return buf.toString();
    }

    /**
     * format을 적용한 문자열로 변환한다.
     *
     * @param date 포맷을 적용할 Date 객체
     * @param format 문자열 포맷
     * @param locale 지역
     * @return 변환되 문자열(Object가 null인 경우 ""을 반환)
     * @see SimpleDateFormat#SimpleDateFormat(String pattern, Locale locale)
     */
    public static String print(Date date, String format, Locale locale) {
        if (date == null) return "";
        if (format == null || locale == null) return date.toString();

        return new SimpleDateFormat(format, locale).format(date);
    }

    /**
     * format을 적용한 문자열로 변환한다.
     *
     * @param number 포맷을 적용할 객체
     * @param format 문자열 포맷
     * @return 변환되 문자열(숫자가 null인 경우 ""을 반환)
     * @see DecimalFormat#DecimalFormat(String pattern)
     */
    public static String print(Number number, String format) {
        if (number == null) return "";
        if (format == null) return number.toString();
        // 포멧이 "#,##0"경우에는 3,100 형태로 출력되고
        // 포멧이 "00000"인경우에는 00009형태로 출력
        return new DecimalFormat(format).format(number);
    }

    /**
     * 입력받은 String의 시작 인덱스부터 마지막 인덱스(-1) 까지의 String을 반환한다.
     *
     * @param source 문자열
     * @param strat 추출할 문자열의 시작 인덱스 위치
     * @param end 추출할 문자열의 마지막 인덱스 위치
     * @return String start 부터 end-1 까지의 String
     */
    public static String getCalcStr(String source, int strat, int end) {
        String rltStr = "";
        try {
            rltStr = new String(source.getBytes(), strat, end - strat);
        } catch (Exception e) {
            return source;
        }
        return rltStr;
    }

    /**
     * 문자열을 반복 횟수만큼 덧붙인다.
     *
     * @param string 문자열
     * @param count 반복 횟수
     * @return 반복 횟수만큼 덧붙여진 문자열
     */
    public static String makeRepeatString(String string, int count) {
        StringBuilder resultStr = new StringBuilder();
        for (int inx = 0; inx < count; inx++) {
            resultStr.append(string);
        }
        return resultStr.toString();
    }

    /**
     * 문자열에 '$', '\'가 포함되어 있으면 해당 문자앞에 '\'를 추가한다.
     *
     * @param string '$', '\'앞에 '\'가 없는 문자열
     * @return \가 추가된 문자열
     */
    public static String escapeBackslashAndDollarSign(String string) {
        if ((string.indexOf('\\') == -1) && (string.indexOf('$') == -1)) return string;

        StringBuilder builder = new StringBuilder();
        for (int inx = 0; inx < string.length(); inx++) {
            char cha = string.charAt(inx);
            if (cha == '\\') {
                builder.append('\\').append('\\');
            } else if (cha == '$') {
                builder.append('\\').append('$');
            } else {
                builder.append(cha);
            }
        }
        return builder.toString();
    }

    /**
     * 문자열에 '\'가 포함되어 있으면 해당 문자앞에 '\'를 추가한다.
     *
     * @param string 문자열
     * @return \가 추가된 문자열
     */
    public static String escapeBackslash(String string) {
        return escapeCharacter(string, '\\');
    }

    /**
     * 문자열에 '$'가 포함되어 있으면 해당 문자앞에 '\'를 추가한다.
     *
     * @param string 문자열
     * @return \가 추가된 문자열
     */
    public static String escapeDollarSign(String string) {
        return escapeCharacter(string, '$');
    }

    /**
     * 문자열에서 원하는 문자 앞에 '\'를 추가한다.
     *
     * @param string 문자열
     * @param add 앞에 '\'를 붙이고 싶은 문자
     * @return \가 추가된 문자열
     */
    public static String escapeCharacter(String string, char add) {
        if (string.indexOf(add) == -1) return string;
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (c == add) {
                buf.append('\\').append(add);
            } else {
                buf.append(c);
            }
        }
        return buf.toString();
    }

    /**
     * 문자열에서 원하는 문자 앞에 같은 문자를 추가한다.
     *
     * @param string 문자열
     * @param add 앞에 추가하고 싶은 문자
     * @return 추가된 문자열
     */
    public static String insertDelimToString(String string, char add) {
        StringBuilder buf = new StringBuilder();

        char[] chars = string.toCharArray();
        for (int inx = 0; inx < chars.length; inx++) {
            if (inx == 0) {
                if (chars[inx] == add) {
                    buf.append(add).append(chars[inx]);
                } else {
                    buf.append(chars[inx]);
                }
            } else if (inx == chars.length - 1) {
                if (chars[inx - 1] == add) {
                    buf.append(add).append(chars[inx]);
                } else {
                    buf.append(chars[inx]);
                }
            } else {
                if (chars[inx] == add && chars[inx - 1] != add) {
                    buf.append(add).append(chars[inx]);
                } else {
                    buf.append(chars[inx]);
                }
            }
        }

        return buf.toString();
    }

    /**
     * Throwable의 stackTrace를 문자열로 반환한다.
     *
     * @param cause 대상 Throwable
     * @return stackTrace 문자열
     */
    public static String getStackTrace(Throwable cause) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream print = new PrintStream(out);
        cause.printStackTrace(print);
        return out.toString();
    }

    /**
     * {@link String#toLowerCase()}를 이용하여 소문자로 변환한다.
     *
     * <pre>
     * StringUtils.lowerCase(null) = null
     * StringUtils.lowerCase("") = ""
     * StringUtils.lowerCase("aBc") = "abc"
     * </pre>
     *
     * @param string 소문자로 변환되어야 할 문자열
     * @return 소문자로 변환된 문자열(null 입력시 null 반환)
     */
    public static String toLowerCase(String string) {
        if (string == null) return null;
        return string.toLowerCase();
    }

    /**
     * {@link String#toUpperCase()}를 이용하여 대문자로 변환한다.
     *
     * <pre>
     * StringUtils.upperCase(null) = null
     * StringUtils.upperCase("") = ""
     * StringUtils.upperCase("aBc") = "ABC"
     * </pre>
     *
     * @param string 대문자로 변환되어야 할 문자열
     * @return 대문자로 변환된 문자열(null 입력시 null 반환)
     */
    public static String toUpperCase(String string) {
        if (string == null) return null;
        return string.toUpperCase();
    }

    /**
     * 문자열 좌우의 공백문자를 제거한다.
     *
     * @param string 문자열
     * @return 공백문자가 제거된 문자열 (null 입력시 null 반환)
     */
    public static String trim(String string) {
        if (string == null) return null;
        return string.trim();
    }

    /**
     * 2개의 문자열을 비교한 결과를 반환한다.
     *
     * @param string 비교대상 문자열
     * @param anotherString 비교문자열
     * @return 문자열이 null 이면 -1, 동일하면 0, 비교대상 문자열이 크면 양수, 작으면 음수를 반환
     * @see String#compareTo(String)
     */
    public static int compareTo(String string, String anotherString) {
        if (string == null || anotherString == null) { return -1; }
        return string.compareTo(anotherString);
    }

    /**
     * 2개의 문자열을 비교한 결과를 반환한다.(대소문자 무시)
     *
     * @param string 비교대상 문자열
     * @param anotherString 비교문자열
     * @return 문자열이 null 이면 -1, 동일하면 0, 비교대상 문자열이 크면 양수, 작으면 음수를 반환
     * @see String#compareToIgnoreCase(String)
     */
    public static int compareToIgnoreCase(String string, String anotherString) {
        if (string == null || anotherString == null) { return -1; }
        return string.compareToIgnoreCase(anotherString);
    }

    /**
     * Object를 문자열로 변환한다.(null인 경우 빈스트링으로 반환)
     *
     * @param object 원본 객체
     * @return 문자열
     */
    public static String objectToString(Object object) {
        String string = "";

        if (object != null) {
            string = object.toString().trim();
        }

        return string;
    }

    /**
     * 문자열에 포함된 모든 대상 문자(char)를 제거한다.
     *
     * @param string 문자열
     * @param remove 제거 대상 문자
     * @return 제거대상 문자열이 제거된 입력문자열(문자열이 null인 경우 null반환)
     */
    public static String remove(String string, char remove) {
 //       if (NullUtil.isNone(string) || string.indexOf(remove) == -1) { return string; }
        char[] chars = string.toCharArray();
        int pos = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != remove) {
                chars[pos++] = chars[i];
            }
        }
        return new String(chars, 0, pos);
    }

    /**
     * 문자열의 콤마 character(,)를 모두 제거한다.
     *
     * @param string 문자열
     * @return ","가 제거된 문자열(문자열이 null인 경우 null 반환)
     */
    public static String removeCommaChar(String string) {
        return remove(string, ',');
    }

    /**
     * 문자열의 마이너스 character(-)를 모두 제거한다.
     *
     * @param string 문자열
     * @return "-"가 제거된 문자열(문자열이 null인 경우 null 반환)
     */
    public static String removeMinusChar(String string) {
        return remove(string, '-');
    }

    /**
     * 문자열에서 {@link Character#isWhitespace(char)}에 정의된 모든 공백문자를 제거한다.
     *
     * @param string 문자열
     * @return 공백문자가 제거된 문자열(문자열이 null인 경우 null 반환)
     */
    public static String removeWhitespace(String string) {
//        if (NullUtil.isNone(string)) { return string; }
        int length = string.length();
        char[] chs = new char[length];
        int count = 0;
        for (int inx = 0; inx < length; inx++) {
            if (!Character.isWhitespace(string.charAt(inx))) {
                chs[count++] = string.charAt(inx);
            }
        }
        if (count == length) { return string; }

        return new String(chs, 0, count);
    }

    /**
     * 문자열을 지정한 길이만큼 자른 후 추가할 문자열과 합친다.
     * <pre>
     * StringUtil.cutString("123456", 3, "가나다") == "123가나다"
     * </pre>
     *
     * @param source 문자열
     * @param length 지정 길이
     * @param add 추가할 문자열
     * @return 지정길이로 잘라서 추가문자열과 합친 문자열
     */
    public static String substringThenAddString(String source, int length, String add) {
        String returnVal = null;
        if (source != null) {
            if (source.length() > length) {
                returnVal = source.substring(0, length) + add;
            } else {
                returnVal = source;
            }
        }
        return returnVal;
    }

    /**
     * 문자열을 주어진 길이만큼 잘라준다.<br>
     * 글자 길이는 한글의 경우 한 글자당 2byte로 계산하여 입력한다.
     *
     * @param string 문자열
     * @param length 글자 길이
     * @return 잘라진 문자열
     */
    public static String substringByBytes(String string, final int length) {
    	 String changeStr = string;
        if (changeStr == null) { return changeStr; }

        int stringLength = changeStr.length();
        int count = 0, index = 0;

        while (index < stringLength && count < length) {
            // 1바이트 문자라면...
            if (changeStr.charAt(index++) < 256) {
                count++; // 길이 1 증가
                // 2바이트 문자라면...
            } else {
                count += 2; // 길이 2 증가
            }
        }

        if (index < stringLength) changeStr = changeStr.substring(0, index);

        return changeStr;
    }

    /**
     * 문자열을 지정한 길이만큼 자른다.
     * <pre>
     * StringUtil.cutString("123456", 3) == "123"
     * </pre>
     *
     * @param source 문자열
     * @param length 지정 길이
     * @return 지정길이로 자른 문자열(문자열이 null인 경우 null반환)
     */
    public static String substring(String source, int length) {
        return substringThenAddString(source, length, EMPTY_STRING);
    }

    /**
     * 데이터에 format을 적용한 포맷 스트링을 반환한다.
     * 예) value="20120101", format="####-##-##" 을 지정하면 2012-01-01가 반환된다.
     *
     * @param format #을 포함하는 포매팅 문자열
     * @param value 실제 포맷을 적용할 문자열
     * @param leftAlign 왼쪽 정렬 여부(true: 왼쪽, false: 오른쪽)
     * @return 포맷이 적용된 문자열
     */
    public static String format(String format, String value, boolean leftAlign) {
        if (!leftAlign) { return formatWithRightAlign(format, value); }

        StringBuilder buf = new StringBuilder();
        int index = 0;
        for (int inx = 0; inx < format.length(); inx++) {
            if (format.charAt(inx) == '#') {
                if (index < value.length()) {
                    buf.append(value.charAt(index++));
                }
            } else {
                if (format.charAt(inx) == '\\') {
                    if (inx + 1 < format.length() && format.charAt(inx + 1) == '#') {
                        buf.append("#");
                        inx++;
                    } else if (inx + 1 < format.length() && format.charAt(inx + 1) == '\\') {
                        buf.append("\\");
                        inx++;
                    } else {
                        buf.append(format.charAt(inx));
                    }
                } else {
                    buf.append(format.charAt(inx));
                }
            }
        }
        return buf.toString();
    }

    private static String formatWithRightAlign(String format, String string) {
        StringBuilder buf = new StringBuilder();
        int index = string.length() - 1;
        for (int inx = format.length() - 1; inx >= 0; inx--) {
            if (format.charAt(inx) == '#') {
                if (inx - 2 >= 0 && format.charAt(inx - 1) == '\\' && format.charAt(inx - 2) != '\\') {
                    buf.insert(0, "#");
                    inx--;
                } else {
                    if (index >= 0) {
                        buf.insert(0, string.charAt(index--));
                    }
                }
            } else {
                if (format.charAt(inx) == '\\') {
                    if (inx - 1 >= 0 && format.charAt(inx - 1) == '\\') {
                        buf.insert(0, "\\");
                        inx--;
                    } else {
                        buf.insert(0, format.charAt(inx));
                    }
                } else {
                    buf.insert(0, format.charAt(inx));
                }
            }
        }
        return buf.toString();
    }
    
    /**
     * String 문자열의 값을 Masking Pattern을 적용하는 Util<br>
     * maskPattern 과 value 의 length가 다를 경우에는 value의 앞에서부터 masking 작업을 한다.<br><br> 
     * 
     * 예) value : 12345678   ,   maskPattern : ****      ->   ****5678 <br>
     * 예) value : 12345678   ,   maskPattern : ****0000  ->   ****5678 <br>
     * 예) value : 12345678   ,   maskPattern : 0000****  ->   1234**** <br>
     *
     * @param value String Value
     * @param maskPattern Mask Pattern
     * @return Masking Value
     */
	public static String maskValue(String value, String maskPattern) {
        char[] buffer = new char[value.length()];
        int mlen = maskPattern.length();
        for(int inx = 0 ; inx < value.length() ; inx++){
            buffer[inx] = ( inx < mlen && maskPattern.charAt(inx) == '*' ) ? '*' : value.charAt(inx);
        }
        value = new String(buffer);
        return value;
    }	
}