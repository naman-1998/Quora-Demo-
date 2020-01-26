package java_project;
import java.sql.*;
import java.util.*;

import com.mysql.jdbc.ResultSet;
public class quora{
	Scanner s=new Scanner(System.in);
	Statement stmt;
	Connection conn;
	String query;
	String que,ans;
	quora(){
		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quora","root","");
			stmt=conn.createStatement();
		}
		catch(Exception e) {
			e.printStackTrace();	
		}
	}
	void PostQuestion() throws SQLException{
		System.out.println("Input Question And Answer");
		que=s.nextLine();
		ans=s.nextLine();
		query = String.format("insert into QnA values('%s','%s')",que,ans);
		stmt.execute(query);
	}
	void ViewPreviousQuestions() throws SQLException{
		query="Select * from QnA";
		ResultSet pointer= (ResultSet) stmt.executeQuery(query);

		while(pointer.next()) {
			System.out.println(pointer.getString(1)+" : "+pointer.getString(2));
		}
	}

	void AskQuestion() throws SQLException{
		System.out.println("Input Question");
		que=s.nextLine();
		query = String.format("insert into QnA values('%s','null')",que);
		stmt.execute(query);
	}
	void AnswerQuestion() throws SQLException{	
		query="select * from QnA";
		ResultSet pointer1= (ResultSet) stmt.executeQuery(query);
		
		System.out.println("Here are the questions");
		while(pointer1.next()) {
			System.out.println(pointer1.getString(1));
		}
		System.out.println("Input Question And Answer");
		que=s.nextLine();
		ResultSet pointer2= (ResultSet) stmt.executeQuery(query);
		while(pointer2.next()) {
			if(que.equalsIgnoreCase(pointer2.getString(1))){
				ans=s.nextLine();
				query = String.format("update QnA set Answer='%s' where Question='%s'",ans,que);
				stmt.execute(query);
				break;
			}
		}
	}

	public static void main(String[] args) throws Exception{
		quora q= new quora();
		Scanner s = new Scanner(System.in);
		System.out.println("Press 1: For Posting Both Question And Answer");
		System.out.println("Press 2: For Viewing Previous Question And Answer");
		System.out.println("Press 3: For Asking Question");
		System.out.println("Press 4: For Answering A Question");
		int a = s.nextInt();
		switch(a) {
		case 1 : q.PostQuestion();
		break;
		case 2 : q.ViewPreviousQuestions();
		break;
		case 3: q.AskQuestion();
		break;
		case 4: q.AnswerQuestion();
		break;
		default: System.out.println("Wrong Input");

		}
	}
}