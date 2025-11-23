package main.java.com.upb.agripos;

import main.java.com.upb.agripos.model.pembayaran.*;
import main.java.com.upb.agripos.model.kontrak.*;
import main.java.com.upb.agripos.util.CreditBy;

public class MainAbstraction {

    public static void main(String[] args) {

        Pembayaran cash = new Cash("W-1", 125000, 200000);
        Pembayaran ewallet = new EWallet("W-2", 50000, "Rizki", "10108");
        Pembayaran transfer = new TransferBank("W-3", 400000, "280768453", "498071");

        System.out.println(((Receiptable) cash).cetakStruk());
        System.out.println(((Receiptable) ewallet).cetakStruk());
        System.out.println(((Receiptable) transfer).cetakStruk());

        CreditBy.print("Efan Aryanto Adli", "240202860", "3IKRA");
    }
}