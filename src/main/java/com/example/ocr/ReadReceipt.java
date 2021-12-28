package com.example.ocr;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadReceipt {
    ITesseract image = new Tesseract();

    public ReadReceipt() {
    }

    public ReceiptInfo DoOCR(String imagePath){
        ReceiptInfo receiptInfo = new ReceiptInfo();
        File directory = new File(imagePath);
        System.out.println(directory.getAbsolutePath());
        try {
            String str = image.doOCR(new File(directory.getAbsolutePath()));
            System.out.println(str);
            receiptInfo.setDate(ExtractDate(str));
            receiptInfo.setAmount(ExtractAmount(str));
            receiptInfo.setMerchant(ExtractVendor(str));
            return receiptInfo;
        } catch (TesseractException e) {
            e.printStackTrace();
            return receiptInfo;
        }

    }

    private String ExtractAmount(String text){
        String regex="([0-9]+[.][0-9]+)";
        String amount = "";
        float amt = 0.0F;

        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(text);

        while(matcher.find())
        {
//            System.out.println(matcher.group());
            try{
                float curr = Float.parseFloat(matcher.group());
                if(curr>amt) {
                    amt=curr;
                    amount=matcher.group();
                }
            }catch (Exception e){
                continue;
            }
        }

        System.out.println(amount);

        return amount;
    }

    private String ExtractVendor(String text){
        String[] vendors = {"Walmart", "WALMART", "KFC","Dominos","DOMINOS"};
        String mVendor="";
        for (String vendor : vendors)
        {
            if (text.contains(vendor))
            {
                mVendor = vendor;
                break;
            }
        }

        return mVendor;
    }

    private String ExtractDate(String text){
        Pattern pattern;
        Matcher matcher;
        String date = "";
        String datePattern1 = "(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)";
        String datePattern2 = "(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/[0-9][0-9]";
        pattern = Pattern.compile(datePattern1);
        matcher = pattern.matcher(text);

        if (matcher.find())
        {
            date = "";
            date += matcher.group(0);
        }

        else
        {
            pattern = Pattern.compile(datePattern2);
            matcher = pattern.matcher(text);
            if (matcher.find())
            {
                date = "";
                date += matcher.group(0);

            }
        }

        System.out.println(date);

        return date;
    }

}
