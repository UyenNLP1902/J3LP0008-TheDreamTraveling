/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyennlp.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 *
 * @author HP
 */
public class DataTypeConverter {

    public static Timestamp convertStringToTimestamp(String strDate) {
        try {
            //DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            Date date = formatter.parse(strDate);
            Timestamp timeStampDate = new Timestamp(date.getTime());

            return timeStampDate;
        } catch (ParseException ex) {
            return null;
        }
    }

    public static int convertStringToInteger(String strNumber) {
        try {
            int result = Integer.parseInt(strNumber);
            if (result < 0) {
                return -1;
            }
            return result;
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    public static String convertVietnamese(String originalString) {
        String temp = Normalizer.normalize(originalString, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile(temp).compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replace('đ','d').replace('Đ','D');
    }
}
