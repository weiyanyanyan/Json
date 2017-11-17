/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Scanner;
import parser.JObject;
import parser.Json;
import parser.Parser;
import tokenizer.Tokenizer;

/**
 *
 *@author MaiBenBen on 2017/11/15.
 */
public class Test {
    public static void main(String[] args) throws Exception{
         Scanner in =new Scanner(System.in);
         String str=in.nextLine();
         Tokenizer tokenizer = new Tokenizer(new BufferedReader(new StringReader(str)));
         try {
         tokenizer.tokenize();
         Parser parser = new Parser(tokenizer);
         Json result = parser.json(); 
             System.out.println("合法Json字符！");
        } catch (Exception e) {
             System.out.println("不合法Json字符!");
        }
         
    }
   
    
}
