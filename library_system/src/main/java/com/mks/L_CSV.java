// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.List;

// public class L_CSV {

//     public List<Book> loadBooks(String filePath) {
//         List<Book> books = new ArrayList<>();
//         String line;
//         try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//             // Skip the header line
//             br.readLine();
//             while ((line = br.readLine()) != null) {
//                 String[] values = line.split(",");
//                 if (values.length == 4) {
//                     String title = values[0];
//                     String author = values[1];
//                     int year;
//                     try {
//                         year = Integer.parseInt(values[2]);
//                     } catch (NumberFormatException e) {
//                         System.err.println("Skipping line due to invalid year format: " + line);
//                         continue; // Skip the line if year is not a valid integer
//                     }
//                     String genre = values[3];
//                     books.add(new Book(title, author, year, genre));
//                 } else {
//                     System.err.println("Skipping line due to incorrect format: " + line);
//                 }
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//         return books;
//     }
// }
