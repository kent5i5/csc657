// Based on Fig. 18.24: TableDisplay.java in Deitel & Deitel "Java How To 
// Program", Third Edition, Prentice Hall 1999 
// This program displays the contents of the Student table in the
// 675Examples Database
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CreateData extends JFrame {  //CHANGE THIS!!
   private Connection connection;
   private JTable table;
    
   public CreateData() //CHANGE THIS!!
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

      getTable();

      setSize( 450, 150 );
      show();
   }

   private void getTable()
   {
      Statement statement;
      ResultSet resultSet;
      
      try {
	statement = connection.createStatement();
	//statement.executeUpdate("drop table sales_rep");
//	statement.executeUpdate("drop table course");
//	statement.executeUpdate("drop table enrolled");
	statement.executeUpdate("create table usertable (userid varchar(10),uname varchar(10),primary key(userid))");
	statement.executeUpdate("insert into usertable values('1','kent')");
	statement.executeUpdate("insert into usertable values('2','kelly')");
	statement.executeUpdate("insert into usertable values('3','john')");
	statement.executeUpdate("insert into usertable values('4','teddy')");
	statement.executeUpdate("insert into usertable values('5','zeo')");
	
	statement.executeUpdate("create table TaggedPhoto (tpid varchar(10),author varchar(10),from_user varchar(10),data int,photo varchar(20),userid varchar(10),primary key(tpid),foreign key(userid) references usertable)");
	statement.executeUpdate("insert into TaggedPhoto values ('001','kt','ak',2012,'flower.jpg', '1')");
	statement.executeUpdate("insert into TaggedPhoto values ('002','ac','ab',2011,'tiger.jpg','1')");
	statement.executeUpdate("insert into TaggedPhoto values ('003','ad','yk',2010,'tank.jpg','2')");
	statement.executeUpdate("insert into TaggedPhoto values ('004','dz','zz',2009,'building.jpg','2')");
	statement.executeUpdate("insert into TaggedPhoto values ('005','qj','ob',2008,'computer.jpg','5')");
	
	statement.executeUpdate("create table Tag(tno varchar(10),value varchar(10),tpid varchar(10),primary key(tno))");
	statement.executeUpdate("insert into Tag values('01','time','002')");
	statement.executeUpdate("insert into Tag values('02','CPScordinate', '003')");
	statement.executeUpdate("insert into Tag values('03','data','004')");
	statement.executeUpdate("insert into Tag values('04','time','003')");
	statement.executeUpdate("insert into Tag values('05','subject','005')");
	
	statement.executeUpdate("create table archive(ano varchar(10),servername varchar(10),location varchar(20),tpid varchar(10),primary key(ano))");
	statement.executeUpdate("insert archive values ('10','db1','www.ace.com123','001')");
	statement.executeUpdate("insert archive values ('11','db2','www.cbe.com234','001')");
	statement.executeUpdate("insert archive values ('12','db3','www.bed.com345','002')");
	statement.executeUpdate("insert archive values ('13','db4','www.ghj.com456','004')");
	statement.executeUpdate("insert archive values ('14','db5','www.ace.com123','001')");
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

      setTitle( "Student Table from SQL Examples" );  //CHANGE THIS!!

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
      final CreateData app = new CreateData();  //CHANGE THIS!

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
