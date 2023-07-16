import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
class User
{
	private String name;
	private String id1;
	private String no;
	
	 public User() {
	    }

	    public User(String name, String id1, String no) {
	       this.name=name;
	       this.id1=id1;
	       this.no=no;
	    }

	    public String getId1() {
	        return id1;
	    }

	    public void setId1(String id1) {
	        this.id1 = id1;
	    }

	    public String getname() {
	        return name;
	    }

	    public void setname(String name) {
	        this.name = name;
	    }

	    public String getNo() {
	        return no;
	    }

	    public void setNo(String no) {
	        this.no = no;
	    }
	    @Override
	    public String toString() {
	        return  "\nname='" + name + '\'' +
	                "\nid='" + id1 + '\'' +
	                "\nnumber='" + no + '\'' ;
	    }
}


class Book extends User {
    private String id;
    private String title;
    private String author;
    private String publishYear;
    private String status;
    private String date;
    
    public Book() {
    }

    public Book(String id, String author, String title, String publishYear, String status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
        this.status = status;
        
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long diff(String s1, String s2)
    {
        String arr1[]=s1.split("-");
        String arr2[]=s2.split("-");
        String s3="";
        String s4 = "";
        for(int i=arr1.length-1;i>=0;i--)
        {
            s3=s3+arr1[i]+"-";
        }
        s3=s3.substring(0,s3.length()-1);
        for(int i=arr2.length-1;i>=0;i--)
        {
            s4=s4+arr2[i]+"-";
        }
        s4=s4.substring(0,s4.length()-1);
        LocalDate db=LocalDate.parse(s3);
        LocalDate da=LocalDate.parse(s4); 
        return ChronoUnit.DAYS.between(db,da);
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(String publishYear) {
        this.publishYear = publishYear;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String toString() {
        return  "\nid='" + id + '\'' +
                "\ntitle='" + title + '\'' +
                "\nauthor='" + author + '\'' +
                "\npublishYear='" + publishYear + '\'' +
                "\nStatus='" + status + '\'';
    }
}

abstract class LibrarySystem {
    abstract void addBook();

    abstract void showAllBooks();

    abstract void showAllAvailableBooks();

    abstract void borrowBook();

    abstract void returnBook();
}



class LibraryService extends LibrarySystem {
    public static final String RED="\u001B[31m";
    public static final String RESET="\u001B[0m";
    public static final String BLUE="\u001B[34m";
    public static final String GREEN="\u001B[32m";
    public static final String CYAN="\u001B[36m";
    public static final String BLACK="\u001B[30m";
    Scanner sc=new Scanner(System.in);
    Validator validator=new Validator();
    ArrayList<Book> books=new ArrayList<>();

    public void addFromFile()
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("lms.txt"));
            String s;
            String any_t = br.readLine();
            while(any_t != null && (s = br.readLine()) != null)
            {
                String s1[]=s.split("\t");
                String id_p = s1[0];
                String title_p = s1[2];
                String author_p = s1[1];
                String publish_year_p = s1[3];
                String status_p = s1[4];
                String u_id_p = s1[5];
                String name_p = s1[6];
                String number_p = s1[7];
                String dt_p = s1[8];
                
                Book book=new Book(id_p,author_p,title_p,publish_year_p,status_p);
                book.setId1(u_id_p);
                book.setname(name_p);
                book.setNo(number_p);
                book.setDate(dt_p);
                books.add(book);
            }
        }
        catch(Exception ex)
        {
            return;
        }
    }

    
    public void addBook() {

       String bookid= validator.validateId();
       String Author=validator.validateAuthorTitle("Author");
       String Title=validator.validateAuthorTitle("Title");
       String year=validator.validatePublishYear();

        Book book=new Book(bookid,Author,Title,year,"Available");
        
        System.out.println(GREEN+"Book Added Successfully !!!"+RESET);

        book.setId1("-");
        book.setNo("-");
        book.setDate("-");
        book.setname("-");

        books.add(book);

        
        try
        {
            FileWriter file_w = new FileWriter("lms.txt");

            file_w.write("ID\t"+"TITLE\t"+"AUTHOR\t"+"PUBLISH YEAR\t"+"STATUS\t"+"U_ID\t"+"NAME\t"+"NUMBER\t"+"DATE\n");

            for (Book bk:books){
                file_w.write(bk.getId() + "\t" + bk.getTitle() + "\t" + bk.getAuthor() + "\t" +
             bk.getPublishYear() + "\t" + bk.getStatus() + "\t" + bk.getId1() + "\t"
             + bk.getname() + "\t" + bk.getNo() + "\t" + bk.getDate() + "\n");
            }

            file_w.close();
            
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    

    public void showAllBooks() {
        boolean flag=false;
        System.out.println("\n----------------------------------------------------------------------------------------------");
        System.out.format(CYAN+"%s%15s%15s%15s%15s","ID","AUTHOR","TITLE","PUBLISH YEAR","STATUS"+RESET);
       System.out.println("\n----------------------------------------------------------------------------------------------");

          for (Book book:books){
              System.out.format("%s%15s%15s%15s%15s",book.getId(),book.getTitle(),book.getAuthor(),book.getPublishYear(),book.getStatus());
              System.out.println();
              flag=true;
            }
        System.out.println("\n----------------------------------------------------------------------------------------------");
       if(!flag)
           System.out.println(RED+"There are no Books in Library"+RESET);
    }
    public void showAllAvailableBooks(){
        boolean flag=false;
        System.out.println("\n----------------------------------------------------------------------------------------------");
        System.out.format(CYAN+"%s%15s%15s%15s%15s","ID","AUTHOR","TITLE","PUBLISH YEAR","STATUS"+RESET);
        System.out.println("\n----------------------------------------------------------------------------------------------");

        if(books.size()>0){
            for (Book book:books){
                if(book.getStatus().equals("Available")){
                    System.out.format("%s%15s%15s%15s%15s",book.getId(),book.getTitle(),book.getAuthor(),book.getPublishYear(),book.getStatus());
                    System.out.println();
                    flag=true;
                }
            }
        }
        else{
            System.out.println(RED+"No books are available in the library"+RESET);
        }
        System.out.println("\n----------------------------------------------------------------------------------------------");
        
        if(!flag)
            System.out.println(RED+"There are no books with the given status Available"+RESET);

    }

    public void showAdminAllAvailableBooks(){
        boolean flag=false;
        System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------");
        System.out.format(CYAN+"%s%15s%15s%15s%15s%5s%11s%11s%11s","ID","AUTHOR","TITLE","PUBLISH YEAR","STATUS","U_ID","NAME","NUMBER","DATE"+RESET);
        System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------");

        if(books.size()>0){
            for (Book book:books){
                if(book.getStatus().equals("Not Available")){
                    System.out.format("%s%15s%15s%15s%15s%5s%11s%11s%11s",book.getId(),book.getTitle(),book.getAuthor(),book.getPublishYear(),book.getStatus(),book.getId1(),book.getname(),book.getNo(),book.getDate());
                    System.out.println();
                    flag=true;
                }
            }
        }
        else{
            System.out.println(RED+"No books are available in the library"+RESET);
        }
        System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------");
        if(!flag)
            System.out.println(RED+"There are no books with the given status Available"+RESET);

    }

    public void borrowBook(){

       System.out.print("Enter your name: ");
       String name = sc.nextLine();
       
        System.out.print("Enter your userid: ");
       String idr = sc.nextLine();
       
       

        boolean chk = false;
        String date = "00-00-0000";
        
        
        do
        {
            System.out.print("Enter the date(dd-mm-yyyy): ");
            date = sc.nextLine();

            int cnt = 0;
            for(int i = 0; i < date.length(); i++)
            {
                if(date.charAt(i) == '-')
                {
                    cnt++;
                }
            }
            String arr1[]=date.split("-");
           
            
            if(cnt != 2 || arr1[0].length() != 2 || arr1[1].length() != 2 || arr1[2].length() != 4 || arr1[0].compareTo("31") > 0 || arr1[1].compareTo("12") > 0 || arr1[2].compareTo("1000") < 0)
            {
                System.out.println(RED + "Please enter date in valid form" + RESET);
                continue;
            }
            chk = true;
        }
        while(!chk);
       
       
       String number = "9876543210";
        boolean kk = true;
       do
       {
            System.out.print("Enter your phone number: ");
            number = sc.nextLine();
            if(number.length() == 9)
            {
                kk = false;
            }
            else
            {
                System.out.println(RED + "Please enter valid phone number" + RESET);
            }
       }
       while(kk);
              
       String bookid= validator.validateId();
       boolean flag=false;

       
       for(Book book:books){
           if(book.getId().equals(bookid) && book.getStatus().equals("Available")){
               flag=true;
               System.out.println(GREEN+"Book Borrowed Successfully !!!"+RESET);
               book.setStatus("Not Available");
               System.out.println("Borrowed Book Details: "+book);
               book.setId1(idr);
               book.setNo(number);
               book.setDate(date);
               book.setname(name);
               
           }
          }

        try
        {
            FileWriter file_w = new FileWriter("lms.txt");
            file_w.write("ID\t"+"TITLE\t"+"AUTHOR\t"+"PUBLISH YEAR\t"+"STATUS\t"+"U_ID\t"+"NAME\t"+"NUMBER\t"+"DATE\n");
            
            for (Book bk:books){
                file_w.write(bk.getId() + "\t" + bk.getTitle() + "\t" + bk.getAuthor() + "\t" +
             bk.getPublishYear() + "\t" + bk.getStatus() + "\t" + bk.getId1() + "\t"
             + bk.getname() + "\t" + bk.getNo() + "\t" + bk.getDate() + "\n");
            }

     
            file_w.close();
            
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

       if(!flag)
           System.out.println(RED+"This book is not available to borrow"+RESET);


    }
    public void showUserBook()
    {
        System.out.print("Enter your unique id: ");
        String checkUser = sc.nextLine();
        System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------");
        System.out.format(CYAN+"%s%15s%15s%15s%15s%5s%11s%11s%11s","ID","AUTHOR","TITLE","PUBLISH YEAR","STATUS","U_ID","NAME","NUMBER","DATE"+RESET);
        System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------");
        boolean ckl = false;
        for(Book book:books){
            if(book.getId1().equals(checkUser)){
                ckl = true;
                System.out.format("%s%15s%15s%15s%15s%5s%11s%11s%11s",book.getId(),book.getTitle(),book.getAuthor(),book.getPublishYear(),book.getStatus(),book.getId1(),book.getname(),book.getNo(),book.getDate());
                System.out.println();
            }
        }

        if(ckl == false)
        {
            System.out.println(RED + "You have now borrowed any book" + RESET);
        }
    }
    public void returnBook(){
        boolean flag=false;
        String bookid= validator.validateId();
        String userid = "-1";
        int gg = 0;
        boolean p1=false;
        while(p1==false)
        {
            System.out.println("Enter your user id");
            userid=sc.nextLine();
            for(Book book:books){
                if(((book.getStatus().equals("Not Available")) && (book.getId1().equals(userid))))
                {
                    p1=true;
                    gg = 1;
                    
                 
                    break;
                }
            }
            if(p1==false)
            {
                System.out.println("Enter valid userid");

                gg = 0;
                System.out.println("Enter 1 to try again and 0 to end");
                gg = sc.nextInt();
                sc.nextLine();

                if(gg == 0)
                {
                    break;
                }
            }
        }

        if(gg == 0)
        {
            return;
        }
        boolean chk = false;
        String d1 = "00-00-0000";
   
        
        do
        {
            System.out.println("Enter today's date (dd-mm-yyyy)");
            d1=sc.nextLine();

            int cnt = 0;
            for(int i = 0; i < d1.length(); i++)
            {
                if(d1.charAt(i) == '-')
                {
                    cnt++;
                }
            }
            String arr1[]=d1.split("-");

            
            if(cnt != 2 || arr1[0].length() != 2 || arr1[1].length() != 2 || arr1[2].length() != 4 || arr1[0].compareTo("31") > 0 || arr1[1].compareTo("12") > 0 || arr1[2].compareTo("1000") < 0)
            {
                System.out.println("Please enter date in valid form");
                continue;
            }
            chk = true;
        }
        while(!chk);

        for(Book book:books){
            if((book.getId().equals(bookid)) && (book.getStatus().equals("Not Available"))){
                flag=true;
                long d = book.diff(book.getDate(), d1);
                if(d < 0)
                {
                    System.out.println("You have entered invalid date...");
                    return;
                }
                if(d > 5)
                {
                    System.out.println("Please pay fine: " + (d - 5) * 25 + " Rs");
                }

                System.out.println(GREEN+"Book Returned Successfully !!!"+RESET);
                book.setId1("-");
                book.setNo("-");
                book.setDate("-");
                book.setname("-");
                book.setStatus("Available");
                System.out.println("Returned Book Details: "+book);
                
            }

        }

        

        if(!flag)
        {
            System.out.println(RED+"You have given invalid details of book\nPlease enter correct id\n"+RESET);
            return;
        }
        try
        {
            FileWriter file_w = new FileWriter("lms.txt");
            file_w.write("ID\t"+"TITLE\t"+"AUTHOR\t"+"PUBLISH YEAR\t"+"STATUS\t"+"U_ID\t"+"NAME\t"+"NUMBER\t"+"DATE\n");

     
            for (Book bk:books){
                file_w.write(bk.getId() + "\t" + bk.getTitle() + "\t" + bk.getAuthor() + "\t" +
             bk.getPublishYear() + "\t" + bk.getStatus() + "\t" + bk.getId1() + "\t"
             + bk.getname() + "\t" + bk.getNo() + "\t" + bk.getDate() + "\n");
            }

            file_w.close();
            
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }


}

class Validator {
    public static final String RED="\u001B[31m";
    public static final String RESET="\u001B[0m";
    private static Pattern ID_Match = Pattern.compile("^\\d{1,4}$");
    private static Pattern Title_Author_Match=Pattern.compile("^[a-zA-Z .]+$");
    private static Pattern Year_Match=Pattern.compile("^\\d{4}$");
    Scanner sc = new Scanner(System.in);

    public String validateId() {
        String bookid;
        while (true) {
            System.out.println("Enter Book ID ");
            bookid = sc.nextLine();
            if (!ID_Match.matcher(bookid).matches()) {
                System.out.println(RED+"SORRY! PLEASE ENTER VALID BOOK ID "+RESET);
            } else {
                break;
            }
        }
        return bookid;
    }
    public String validateAuthorTitle(String input){
        String result;
        while (true){
            if(input=="Title"){
                System.out.println("Enter Title");
            }else{
                System.out.println("Enter Author");
            }
            result=sc.nextLine();
            if(!Title_Author_Match.matcher(result).matches()){
                System.out.println(RED+"Please Enter Valid "+input+RESET);
            }
            else{
                break;
            }

        }
        return result;
    }
    public String validatePublishYear(){
        String year;
        while(true){
            System.out.println("Enter Publish Year of Book ");
            year=sc.nextLine();
            if(!Year_Match.matcher(year).matches()){
                System.out.println(RED+"Enter valid Publish Year"+RESET);
            }
            else{
                break;
            }
        }
        return year;
    }

}

public class Library_Management_System {
    public static void main(String[] args) {


        Scanner sc=new Scanner(System.in);
        LibraryService system=new LibraryService();
        system.addFromFile();
        boolean p=true;
        while(p)
        {
            System.out.println("Which library system do you want to access: \n"+
                            "1. Admin library management system\n"+
                            "2. User Library mangement system\n");

            int lib_system = sc.nextInt();

            if(lib_system == 2)
            {		
                boolean flag = false;
            do{
                System.out.println("_________________________________________________________________________________________________________________________________________\n");
                System.out.println("Welcome to Library Management System\n");
                System.out.println("1.Show All Books of Library\n2.Show All the Books which are currently available in Library\n"+
                "3.Borrow a Book from Library\n4.Return a Book to Library\n5.Show all your issued book\n6.Clear the screen\n7.Exit from System\n");
                System.out.println("_________________________________________________________________________________________________________________________________________\n");

                    System.out.print("Enter Your Choice: ");
                    int choice = sc.nextInt();

                switch (choice){
                    case 1:
                        system.showAllBooks();
                        break;
                    case 2:
                        system.showAllAvailableBooks();
                        break;
                    case 3:
                        system.borrowBook();
                        break;
                    case 4:
                        system.returnBook();
                        break;
                    case 5:
                        system.showUserBook();
                        break;
                    case 6:
                            System.out.println("Clearing Screen...");
                            int delay = 2000; 

                            long start = System.currentTimeMillis();
                            while(start >= System.currentTimeMillis() - delay); 
                            
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            break;

                    case 7:
                        System.out.println("Thank you for using our System!!!");
                        flag = true;
                        break;
                    default:
                        System.out.println("Please Enter Valid Choice...");

                }

                if(flag)
                {
                    break;
                }
                }
                while(true);
            }
            else
            {
                boolean flag = false;
            do{
                System.out.println("_________________________________________________________________________________________________________________________________________\n");
                System.out.println("Welcome to Library Management System\n");
                System.out.println("1.Add a Book to Library\n2.Show All Books of Library\n3.Show All the Books which are currently available in Library\n"+
                "4. Show all issued book"+"\n5.Clear the screen\n6.Exit from System\n");
                System.out.println("_________________________________________________________________________________________________________________________________________\n");

                    System.out.print("Enter Your Choice: ");
                    int choice = sc.nextInt();

                switch (choice){
                    case 1:
                        system.addBook();
                        break;
                    case 2:
                        system.showAllBooks();
                        break;
                    case 3:
                        system.showAllAvailableBooks();
                        break;
                    case 4:
                        system.showAdminAllAvailableBooks();
                        break;
                    case 5:
                            System.out.println("Clearing Screen...");
                            int delay = 2000; 

                            long start = System.currentTimeMillis();
                            while(start >= System.currentTimeMillis() - delay); 
                            
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            break;

                    case 6:
                        System.out.println("Thank you for using our System!!!");
                        flag = true;
                        break;
                    default:
                        System.out.println("Please Enter Valid Choice...");

                }

                if(flag)
                {
                    break;
                }
                }
                while(true);
            }
        System.out.println("Enter 1 if you want to continue to system, else enter 0");
        int n=sc.nextInt();
        if(n==1)
        {

        }
        else
        {
            p=false;
        }
        }
    }
}