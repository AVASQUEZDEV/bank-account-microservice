package com.nttdata.bankaccount.util;

import lombok.SneakyThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class defines methods the global use.
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
public class AppUtil {

    /**
     * This method set format to date
     *
     * @param date date
     * @return date formatted
     */
    @SneakyThrows(ParseException.class)
    public static Date dateFormat(Date date) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String newDate = simpleDateFormat.format(date);
        return simpleDateFormat.parse(newDate);
    }

    public static String checkAccountTypeName(String requestName) {
        String accountTypeName = Constant.FIXED_TERM_ACCOUNT;
        if (requestName.matches("(.*)ahorros(.*)")) {
            accountTypeName = Constant.SAVING_ACCOUNT;
        } else if (requestName.matches("(.*)corriente(.*)")) {
            accountTypeName = Constant.CURRENT_ACCOUNT;
        }
        return accountTypeName;
    }

}
