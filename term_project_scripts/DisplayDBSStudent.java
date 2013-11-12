// Based on Fig. 18.24: TableDisplay.java in Deitel & Deitel "Java How To 
// Program", Third Edition, Prentice Hall 1999 
// This program displays the contents of the Student table in the
// 675Examples Database
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DisplayDBSStudent extends JFrame {  //CHANGE THIS!!
   private Connection connection;
   private JTable table;
    
   public DisplayDBSStudent(int q) //CHANGE THIS!
   {   
      // The URL specifying the 675Examples database to which 
      // this program connects using JDBC to connect to a
      // Microsoft ODBC database.
      String url = "jdbc:odbc:SQLhw6";  //CHANGE THIS!
      String username = "sa";
      String password = "ying4817";

      // Load the driver to allow connection to the database
      try {
         Class.forName( "sun.jdbc.odbc.JdbcOdbcDriver" );

         connection = DriverManager.getConnection( 
            url, username, password );
      } 
      catch ( ClassNotFoundException cnfex ) {
         System.err.println( 
            "Failed to load JDBC/ODBC driver." );
         cnfex.printStackTrace();
         System.exit( 1 );  // terminate program
      }
      catch ( SQLException sqlex ) {
         System.err.println( "Unable to connect" );
         sqlex.printStackTrace();
      }

	 
      //int q = 10;
      getTable(q);

      setSize( 450, 150 );
      show();
   }

   private void getTable(int q)
   {
      Statement statement;
      ResultSet resultSet;
      
      try {
       String query = "";
        Scanner in2 = new Scanner(System.in);
		switch(q){
		
		case 1:
         query = "CREATE TaggedPhoto (tpid varchar(10),author varchar(10),from varchar(10),data datatime,photo varchar(10),primary key(tpid))";  //CHANGE THIS!
		 break;
		case 2:	
         query = "DELETE FROM TaggedPhoto WHERE tpid = 006";
         break;
        case 3: 
         query = "insert archive values (‘15’,’db1’,’www.ace.com123’,’001’)"; 
         break;
        case 4:
         query = "UPDATE TagSET value = ‘new_time’ WHERE tpid = 003"; 
         break;
        case 5:
         query = "INSERT INTO Tag () insert into Tag values(‘06’,’test’,’002’)"; 
         break;
        case 6:
		 System.out.print("enter tpid for the query 6:  ");
	    
	     String tpid= in2.nextLine();
	   
         query = "SELECT * FROM TaggedPhoto P JOIN Tag T ON P.tpid = T.tpid JOIN user U ON P.tpid = U.tpid WHERE  tpid = "+tpid ; 
         break;
        case 7: 
         System.out.print("enter q7name for the query 6:  ");
	    
	     String q7name = in2.nextLine();
         query = "SELECT tpid FROM TaggedPhoto WHERE uname = "+ q7name;
         break;
        case 8: 
         System.out.print("enter q8name for the query 6:  ");
	    
	     String q8name= in2.nextLine();
        
		 query = "SELECT tpid FROM TaggedPhoto WHERE From = "+q8name;
         break;
        case 9:
	     System.out.print("enter name for the query 9:  ");
	    
	     String name= in2.nextLine();
	     
	     System.out.print("enter data for the query 9:  ");
	    
	     String date = in2.nextLine();
         query = "SELECT tpid FROM TaggedPhoto WHERE author = '"+ name+ "' and data < " + date;
         break;
        case 10:
		 query = "SELECT tpid FROM Tag";
		 break;
		 default: query = "SELECT tpid FROM Tag";
		}
		System.out.print("query: "+query);
         statement = connection.createStatement();
         resultSet = statement.executeQuery( query );
         displayResultSet( resultSet );
         statement.close();
      }
      catch ( SQLException sqlex ) {
         sqlex.printStackTrace();
      }
   }

   private void displayResultSet( ResultSet rs )
      throws SQLException
   {
      // position to first record
      boolean moreRecords = rs.next();  

      // If there are no records, display a message
      if ( ! moreRecords ) {
         JOptionPane.showMessageDialog( this, 
            "ResultSet contained no records" );
         setTitle( "No records to display" );
         return;
      }

      setTitle( "Students Enrolled in DBS from SQL Examples" );  //CHANGE THIS!!

      Vector columnHeads = new Vector();
      Vector rows = new Vector();

      try {
         // get column heads
         ResultSetMetaData rsmd = rs.getMetaData();
      
         for ( int i = 1; i <= rsmd.getColumnCount(); ++i ) 
            columnHeads.addElement( rsmd.getColumnName( i ) );

         // get row data
         do {
            rows.addElement( getNextRow( rs, rsmd ) ); 
         } while ( rs.next() );

         // display table with ResultSet contents
         table = new JTable( rows, columnHeads );
         JScrollPane scroller = new JScrollPane( table );
         getContentPane().add( 
            scroller, BorderLayout.CENTER );
         validate();
      }
      catch ( SQLException sqlex ) {
         sqlex.printStackTrace();
      }
   }

   private Vector getNextRow( ResultSet rs, 
                              ResultSetMetaData rsmd )
       throws SQLException
   {
      Vector currentRow = new Vector();
      
      for ( int i = 1; i <= rsmd.getColumnCount(); ++i )
         switch( rsmd.getColumnType( i ) ) {
            case Types.VARCHAR:
                  currentRow.addElement( rs.getString( i ) );
               break;
            case Types.INTEGER:
                  currentRow.addElement( 
                     new Long( rs.getLong( i ) ) );
               break;
            default: 
               System.out.println( "Type was: " + 
                  rsmd.getColumnTypeName( i ) );
         }
      
      return currentRow;
   }

   public void shutDown()
   {
      try {
         connection.close();
      }
      catch ( SQLException sqlex ) {
         System.err.println( "Unable to disconnect" );
         sqlex.printStackTrace();
      }
   }

   public static void main( String args[] ) 
   {
	   System.out.print("enter numter 1-10 to choice of your favor query: ");
		Scanner in = new Scanner(System.in);
		int q = in.nextInt();
		String input = in.nextLine();
      final DisplayDBSStudent app = new DisplayDBSStudent(q);  //CHANGE THIS!

      app.addWindowListener( 
         new WindowAdapter() {
            public void windowClosing( WindowEvent e ) 
            {  
               app.shutDown();
               System.exit( 0 );
            }
         }
      );
   }
}
