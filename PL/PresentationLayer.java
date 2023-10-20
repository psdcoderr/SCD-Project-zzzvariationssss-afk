package PL;

import java.util.List;
import java.util.Scanner;

import BLL.BusinessLayer;
import DAL.DataLayerDB;

public class PresentationLayer {

    private static final Scanner scanner = new Scanner(System.in);
    private static BusinessLayer businessLayer;

    public PresentationLayer(BusinessLayer businessLayerr) {
        this.businessLayer = businessLayerr;
        String btitle;
        String author;
        String Yearpassed;
        boolean a = false;

        while (!a) {
            System.out.println(
                    "Enter Choice:\n1) For adding Book\n2) For Updating book\n3) For deleting Book\n4) View books\n5) For exit");
            int ip = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (ip) {
                case 1: {
                    // Add book
                    System.out.println("Enter Book title:");
                    btitle = scanner.nextLine();
                    System.out.println("Enter Author:");
                    author = scanner.nextLine();
                    System.out.println("Enter Passing date:");
                    Yearpassed = scanner.nextLine();
                    businessLayer.addData(btitle, author, Yearpassed);
                    break;
                }
                case 2: {
                    // Update book
                	 System.out.println("Enter book name to search:");
                     btitle = scanner.nextLine().trim();
                     if (businessLayer.checkBook(btitle)) {
                         System.out.println("Book Found!\nEnter new details:");
                         System.out.println("Enter New Book title:");
                         String newBtitle = scanner.nextLine();
                         System.out.println("Enter Author:");
                         author = scanner.nextLine();
                         System.out.println("Enter Passing date:");
                         Yearpassed = scanner.nextLine();
                         businessLayer.updateBook(btitle, newBtitle, author, Yearpassed);
                     } else {
                         System.out.println("Book Not found!");
                     }
                    break;
                }
                case 3:
                    // Delete Books
                    break;
                case 4: {
                    // View all books
                    break;
                }
                case 5:
                    // End Program.
                    a = true;
                    break;
                default: {
                    System.out.println("Wrong Choice!");
                    break;
                }
            }
        }
    }
}