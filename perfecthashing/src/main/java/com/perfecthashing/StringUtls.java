package com.perfecthashing;

public class StringUtls {
    
    public static int getStringKey(String s){
        int res=0;

        for(int i=0;i<s.length();i++){
            res += s.charAt(i)*(i+1);
        }

        return res;
    }
    
}

