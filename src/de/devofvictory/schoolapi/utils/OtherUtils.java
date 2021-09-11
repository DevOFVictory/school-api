package de.devofvictory.schoolapi.utils;

import java.util.HashMap;

public class OtherUtils {
	
	public static HashMap<String, String> convertToQueryStringToHashMap(
            String source) throws Exception {

        HashMap<String, String> data = new HashMap<String, String>();

        final String[] arrParameters = source.split("&");
        for (final String tempParameterString : arrParameters) {

            final String[] arrTempParameter = tempParameterString
                    .split("=");

            if (arrTempParameter.length >= 2) {
                final String parameterKey = arrTempParameter[0];
                final String parameterValue = arrTempParameter[1];
                data.put(parameterKey, parameterValue);
            } else {
                final String parameterKey = arrTempParameter[0];
                data.put(parameterKey, "");
            }
        }

        return data;
    }

}
