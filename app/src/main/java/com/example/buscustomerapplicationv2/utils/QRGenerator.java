package com.example.buscustomerapplicationv2.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

public class QRGenerator {

    private static final int QR_WIDTH = 500;
    private static final int QR_HEIGHT = 500;


    public void createQR(String qrText, ImageView imageview) {
        try {
            QRCodeWriter writer = new QRCodeWriter();

            BitMatrix martix = writer.encode(qrText, BarcodeFormat.QR_CODE,
                    QR_WIDTH, QR_HEIGHT);

            System.out.println("w:" + martix.getWidth() + "h:"
                    + martix.getHeight());

            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode(qrText,
                    BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
                    Bitmap.Config.ARGB_8888);

            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            imageview.setImageBitmap(bitmap);

          /*  byte[] data = PrintPicture.POS_PrintBMP(bitmap, 300, 0);
            SendDataByte(data);
            // SendDataByte(PrinterCommand.POS_Set_Cut(1));
            //SendDataByte(PrinterCommand.POS_Set_LineSpace(5));
            SendDataByte(PrinterCommand.POS_Set_PrtInit());
            byte[] powered = "    Powered by: Mulah.co.in  ".getBytes();
            SendDataByte(powered);
            SendDataByte(PrinterCommand.POS_Set_PrtAndFeedPaper(120));*/
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

// --Commented out by Inspection START (22-07-2020 18:46):
//    private static String asciiToHex(String asciiValue)
//    {
//        char[] chars = asciiValue.toCharArray();
//        StringBuffer hex = new StringBuffer();
//        for (int i = 0; i < chars.length; i++)
//        {
//            hex.append(Integer.toHexString((int) chars[i]));
//        }
//        return hex.toString();
//    }
// --Commented out by Inspection STOP (22-07-2020 18:46)
}
