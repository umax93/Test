/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Максим
 */

public class A{  
public static void main(String[] args) throws  IOException
{
Scanner scan = new Scanner(System.in); 
System.out.println("Введите путь к файлу:( например с:/php/qq.txt)");
String fp= scan.nextLine();
//src/OCP/newfile
File path=new File(fp);
BufferedReader in=new BufferedReader(new FileReader(path));
List <String>a=new ArrayList(),b=new ArrayList();
String s,d;
while ((s=in.readLine()) != null)  a.add(s);
in.close();
for(int i=0;i<a.size();i++){d=a.get(i).split(" ")[1]+" "+a.get(i).split(" ")[0];if (a.contains(d)) { System.out.println(a.get(i)+" "+a.get(i).split(" ")[0]); 
b.add(d); a.removeAll(b);
}}

}
}
