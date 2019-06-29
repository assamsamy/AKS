package Messages;

import java.math.BigInteger;

public class LFSR {
    private static int[] seedArray ={1,0,1,1,0,1,1,0,1,0,1,0,0,0,1,1,1};




    public int xor(int x,int y){
        if (x==y){
            return 0;
        }
        else return 1;
    }

    //  simulate one step and return the new bit as 0 or 1
    public int generate(int i) {
        int result=(seedArray[i%seedArray.length]+seedArray[(i+1)%seedArray.length])%2;
        return result;


    }            //  simulate k steps and return k-bit integer
    public int[] genratearray(int length){
        int []prng=new int[length];
        for (int i=0;i<length;i++){
            prng[i]=generate(i);
        }
        return prng;
    }
    public int[] decrypt(int[] cipher,int[] prng,int length){
        int[] dcrpt=new int[100];
        for (int j=0;j<length;j++){
            dcrpt[j]=xor(cipher[j],prng[j]) ;
        }
        return dcrpt;
    }
    public  int[] encrypt(int[]prng,int[] st)  {
        int[] cipher=new int[100];
        for (int i=0;i<st.length;i++)
            cipher[i]=this.xor(st[i],prng[i]);
        return cipher;

    }                //  return a string representation of the LFSR
   /* public static void main(String[] args){
        LFSR essai=new LFSR();
        int[] arr={1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0};
        int[] prng=new int[100];
        prng=essai.genratearray(prng,arr.length);

        *//*for (int j=0;j<arr.length;j++){
            prng[j]= essai.generate(j);
        }*//*
        int[] rres=essai.encrypt(prng,arr);
        int[] decpt=new int[100];
        decpt=essai.decrypt(rres,prng,arr.length);

        *//*for (int j=0;j<arr.length;j++){
            decpt[j]=essai.xor(rres[j],prng[j]) ;
        }*//*

        for (int i=0;i<arr.length;i++ )
            System.out.print(arr[i]);
        System.out.println("\n");
        for (int i=0;i<arr.length;i++ )
            System.out.print(" encryption  "+rres[i]);
        System.out.println("\n");
        for (int i=0;i<arr.length;i++ )
            System.out.print(decpt[i]);
        System.out.println("\n");

    }*/
}
