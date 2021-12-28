package com.example.ocr;

public class ReceiptInfo {
    private static String merchant;
    private static String merchantType;
    private static String date;
    private static String amount;

    public ReceiptInfo() {}

    public ReceiptInfo(String merchant , String merchantType, String date, String amount){
        this.merchant = merchant;
        this.merchantType= merchantType;
        this.date = date;
        this.amount = amount;
    }

    public static void setMerchant(String merchant) {
        ReceiptInfo.merchant = merchant;
    }

    public static void setMerchantType(String merchantType) {
        ReceiptInfo.merchantType = merchantType;
    }

    public static void setDate(String date) {
        ReceiptInfo.date = date;
    }

    public static void setAmount(String amount) {
        ReceiptInfo.amount = amount;
    }

    public static String getMerchant() {
        return merchant;
    }

    public static String getMerchantType() {
        return merchantType;
    }

    public static String getDate() {
        return date;
    }

    public static String getAmount() {
        return amount;
    }

}
