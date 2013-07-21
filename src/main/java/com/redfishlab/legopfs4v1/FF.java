package com.redfishlab.legopfs4v1;

import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import android.content.Context;

public class FF {
    //global to class
    Object irdaService;
    Method irWrite;
    SparseArray<String> irData;
    Context tContext;

    //constructor
    public FF(Context ttContext){
        this.tContext = ttContext;

        irdaService = tContext.getSystemService("irda");
        Class c = irdaService.getClass();
        Class p[] = { String.class };
        try {
            irWrite = c.getMethod("write_irsend", p);
        } catch (NoSuchMethodException e) {
            //e.printStackTrace();
            Toast.makeText(ttContext.getApplicationContext(), "I ERROR", Toast.LENGTH_LONG).show();
        }
    }

    public void Red_Up(int chn, boolean direct) {
        if (direct) Raw_Red_Up(chn);
        else Raw_Red_Down(chn);
    }

    public void Red_Down(int chn, boolean direct) {
        if (direct) Raw_Red_Down(chn);
        else Raw_Red_Up(chn);
    }
    
    public void Blue_Up(int chn, boolean direct) {
        if (direct) Raw_Blue_Up(chn);
        else Raw_Blue_Down(chn);
    }

    public void Blue_Down(int chn, boolean direct) {
        if (direct) Raw_Blue_Down(chn);
        else Raw_Blue_Up(chn);
    }

    public void Red_Up_Blue_Up(int chn, boolean direct_red, boolean direct_blue) {
        if (direct_red && direct_blue) Raw_Red_Up_Blue_Up(chn);
        else if (direct_red && !direct_blue) Raw_Red_Up_Blue_Down(chn);
        else if (!direct_red && direct_blue) Raw_Red_Down_Blue_Up(chn);
        else Raw_Red_Down_Blue_Down(chn);
    }

    public void Red_Down_Blue_Up(int chn, boolean direct_red, boolean direct_blue) {
        if (direct_red && direct_blue) Raw_Red_Down_Blue_Up(chn);
        else if (direct_red && !direct_blue) Raw_Red_Down_Blue_Down(chn);
        else if (!direct_red && direct_blue) Raw_Red_Up_Blue_Up(chn);
        else Raw_Red_Up_Blue_Down(chn);
    }

    public void Red_Up_Blue_Down(int chn, boolean direct_red, boolean direct_blue) {
        if (direct_red && direct_blue) Raw_Red_Up_Blue_Down(chn);
        else if (direct_red && !direct_blue) Raw_Red_Up_Blue_Up(chn);
        else if (!direct_red && direct_blue) Raw_Red_Down_Blue_Down(chn);
        else Raw_Red_Down_Blue_Up(chn);
    }

    public void Red_Down_Blue_Down(int chn, boolean direct_red, boolean direct_blue) {
        if (direct_red && direct_blue) Raw_Red_Down_Blue_Down(chn);
        else if (direct_red && !direct_blue) Raw_Red_Down_Blue_Up(chn);
        else if (!direct_red && direct_blue) Raw_Red_Up_Blue_Down(chn);
        else Raw_Red_Up_Blue_Up(chn);
    }

    //nibble 1: 00CC, CC is channel (00 is 1, 01 is 2, 10 is 3, 11 is 4)
    //nibble 2: 0001 (combo direct mode)
    //nibble 3: BBAA (B is blue, A is red. 00=float, 01=forward, 10=back, 11=brake then float)
    //nibble 4: LRC = 1111 XOR nibble1 XOR nibble2 XOR nibble3

    public void Red_Brake(int chn) {
        String t_raw_nibble1 = "00"+intChnToBin(chn-1);
        String t_raw_nibble2 = "0001";
        String t_raw_nibble3 = "0011";
        Fire(t_raw_nibble1, t_raw_nibble2, t_raw_nibble3);
    }
    public void Blue_Brake(int chn) {
        String t_raw_nibble1 = "00"+intChnToBin(chn-1);
        String t_raw_nibble2 = "0001";
        String t_raw_nibble3 = "1100";
        Fire(t_raw_nibble1, t_raw_nibble2, t_raw_nibble3);
    }
    public void Red_Blue_Brake(int chn) {
        String t_raw_nibble1 = "00"+intChnToBin(chn-1);
        String t_raw_nibble2 = "0001";
        String t_raw_nibble3 = "1111";
        Fire(t_raw_nibble1, t_raw_nibble2, t_raw_nibble3);
    }



    ////////////////////////////////
    ////// Internal Functions //////
    ////////////////////////////////

    //nibble 1: 00CC, CC is channel (00 is 1, 01 is 2, 10 is 3, 11 is 4)
    //nibble 2: 0001 (combo direct mode)
    //nibble 3: BBAA (B is blue, A is red. 00=float, 01=forward, 10=back, 11=brake then float)
    //nibble 4: LRC = 1111 XOR nibble1 XOR nibble2 XOR nibble3

    private void Raw_Red_Up(int chn) {
        //raw form in 4char binary strings
        String t_raw_nibble1 = "00"+intChnToBin(chn-1);
        String t_raw_nibble2 = "0001";
        String t_raw_nibble3 = "0001";
        Fire(t_raw_nibble1, t_raw_nibble2, t_raw_nibble3);
    }

    private void Raw_Red_Down(int chn) {
        //raw form in 4char binary strings
        String t_raw_nibble1 = "00"+intChnToBin(chn-1);
        String t_raw_nibble2 = "0001";
        String t_raw_nibble3 = "0010";
        Fire(t_raw_nibble1, t_raw_nibble2, t_raw_nibble3);
    }

    private void Raw_Blue_Up(int chn) {
        String t_raw_nibble1 = "00"+intChnToBin(chn-1);
        String t_raw_nibble2 = "0001";
        String t_raw_nibble3 = "0100";
        Fire(t_raw_nibble1, t_raw_nibble2, t_raw_nibble3);
    }

    private void Raw_Blue_Down(int chn) {
        String t_raw_nibble1 = "00"+intChnToBin(chn-1);
        String t_raw_nibble2 = "0001";
        String t_raw_nibble3 = "1000";
        Fire(t_raw_nibble1, t_raw_nibble2, t_raw_nibble3);
    }

    private void Raw_Red_Up_Blue_Up(int chn) {
        String t_raw_nibble1 = "00"+intChnToBin(chn-1);
        String t_raw_nibble2 = "0001";
        String t_raw_nibble3 = "0101";
        Fire(t_raw_nibble1, t_raw_nibble2, t_raw_nibble3);
    }
    //nibble 3: BBAA (B is blue, A is red. 00=float, 01=forward, 10=back, 11=brake then float)
    private void Raw_Red_Up_Blue_Down(int chn) {
        String t_raw_nibble1 = "00"+intChnToBin(chn-1);
        String t_raw_nibble2 = "0001";
        String t_raw_nibble3 = "1001";
        Fire(t_raw_nibble1, t_raw_nibble2, t_raw_nibble3);
    }
    private void Raw_Red_Down_Blue_Up(int chn) {
        String t_raw_nibble1 = "00"+intChnToBin(chn-1);
        String t_raw_nibble2 = "0001";
        String t_raw_nibble3 = "0110";
        Fire(t_raw_nibble1, t_raw_nibble2, t_raw_nibble3);
    }
    private void Raw_Red_Down_Blue_Down(int chn) {
        String t_raw_nibble1 = "00"+intChnToBin(chn-1);
        String t_raw_nibble2 = "0001";
        String t_raw_nibble3 = "1010";
        Fire(t_raw_nibble1, t_raw_nibble2, t_raw_nibble3);
    }

    //Channel number to 2 char binary rep, chn is from 0 to 3
    private String intChnToBin(int chn) {
        //error adjust
        if (chn < 0 || chn > 3) {
            //error
            Toast.makeText(tContext, "Chn error", Toast.LENGTH_LONG).show();
            return "00";
        }
        //convert
        String t_res1 = Integer.toBinaryString(chn);
        if (t_res1.length()==1) {t_res1 = "0"+t_res1;}

        return t_res1;
    }

    //Get LRC
    private String getLRC(String nib1, String nib2, String nib3) {
        //check if all nib are 4 char long
        if (nib1.length()!=4 || nib2.length()!=4 || nib3.length()!=4) {
            Toast.makeText(tContext, "LRC error. "+nib1+nib2+nib3, Toast.LENGTH_LONG).show();
            return "0000";
        }
        //XOR
        int t_res1 = 15 ^ Integer.parseInt(nib1, 2) ^ Integer.parseInt(nib2, 2) ^ Integer.parseInt(nib3, 2);
        //convert to string
        String t_res2 = Integer.toBinaryString(t_res1);
        if (t_res2.length()==1) {t_res2 = "000"+t_res2;}
        else if (t_res2.length()==2) {t_res2 = "00"+t_res2;}
        else if (t_res2.length()==3) {t_res2 = "0"+t_res2;}

        return t_res2;
    }

    //convert 4 char binary string to wave codes
    //e.g. convert "0001" to "6,10,6,10,6,10,6,21"
    private String binToWave(String t_bin) {
        String res = new String();
        char[] charArray = t_bin.toCharArray();
        //bin 1
        if (charArray[0]=='0') {res += "6,10,";}
        else {res += "6,21,";}
        //bin 2
        if (charArray[1]=='0') {res += "6,10,";}
        else {res += "6,21,";}
        //bin 3
        if (charArray[2]=='0') {res += "6,10,";}
        else {res += "6,21,";}
        //bin 4
        if (charArray[3]=='0') {res += "6,10";}
        else {res += "6,21";}
        return res;
    }

    //Fire IR
    private void Fire(String t_raw_nibble1, String t_raw_nibble2, String t_raw_nibble3) {
        //in wave numeric codes
        String t_wave_nibble1 = binToWave(t_raw_nibble1);
        String t_wave_nibble2 = binToWave(t_raw_nibble2);
        String t_wave_nibble3 = binToWave(t_raw_nibble3);
        String t_wave_lrc = binToWave(getLRC(t_raw_nibble1, t_raw_nibble2, t_raw_nibble3));
        String data = "38000,6,39," + t_wave_nibble1 +","+ t_wave_nibble2 +","+ t_wave_nibble3 +","+ t_wave_lrc + ",6,39";
        //event
        try {
            irWrite.invoke(irdaService, data);
        } catch (IllegalArgumentException e) {
            Toast.makeText(tContext, "IllegalArgumentException", Toast.LENGTH_LONG).show();
        } catch (IllegalAccessException e) {
            Toast.makeText(tContext, "IllegalAccessException", Toast.LENGTH_LONG).show();
        } catch (InvocationTargetException e) {
            Toast.makeText(tContext, "InvocationTargetException", Toast.LENGTH_LONG).show();
        }
    }

}
