/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;
import parser.Parser;
import tokenizer.Tokenizer;

/**
 *
 * @author MaiBenBen on 2017/11/15.
 */
public class Json {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Scanner in =new Scanner(System.in);
         String str=in.nextLine();
         Tokenizer tokenizer = new Tokenizer(new BufferedReader(new StringReader(str)));
         try {
         tokenizer.tokenize();
         Parser parser = new Parser(tokenizer);
         parser.Json result = parser.json(); 
             System.out.println("合法Json字符！");
        } catch (Exception e) {
             System.out.println("不合法Json字符!");
        }
    }
    
}
