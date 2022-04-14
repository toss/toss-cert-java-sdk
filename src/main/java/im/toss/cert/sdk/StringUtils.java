package im.toss.cert.sdk;

class StringUtils {
    static String join(String separator, String[] dataList) {
        if (dataList == null) return "";
        if (dataList.length == 0) return "";
        if (dataList.length == 1) return dataList[0];

        StringBuilder builder = new StringBuilder();
        builder.append(dataList[0]);
        for (int i = 1; i < dataList.length; i++) {
            if (dataList[i].length() == 0) continue;

            builder.append(separator);
            builder.append(dataList[i]);
        }
        return builder.toString();
    }
}
