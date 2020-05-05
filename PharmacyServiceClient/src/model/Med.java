package model;

import java.sql.Statement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Med {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			//?useTimezone=true&serverTimezone=UTC
			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf?useTimezone=true&serverTimezone=UTC","root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String addMedDetails(String name, String code, String price, String description) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into pharmacy(`med_id`,`med_name`,`med_code`,`med_price`,`med_description`)"
					+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, name);
			preparedStmt.setInt(3, Integer.parseInt(code));
			preparedStmt.setInt(4, Integer.parseInt(price));
			preparedStmt.setString(5, description);
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			//output = "Inserted successfully";
			String newMeds = readMeds();
			 output = "{\"status\":\"success\", \"data\": \"" +newMeds + "\"}";
			
		} catch (Exception e) {
			//output = "Error while inserting the user.";
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the med.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readMeds() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\'1\'><tr><th>Medicine Name</th><th>Medicine Code</th><th>Price</th><th>Description</th></tr>";
			String query = "select * from pharmacy";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String medID = Integer.toString(rs.getInt("med_id"));
				String medName = rs.getString("med_name");
				String code = Integer.toString(rs.getInt("med_code"));
				String price = Integer.toString(rs.getInt("med_price"));
				String description = rs.getString("med_description");
				// Add into the html table
				output += "<tr><td><input id='hidMedIDUpdate' name='hidMedIDUpdate' type='hidden' value='" + medID + "'>" + medName + "</td>";
				
				/*output += "<tr><td><input id=\"hidUserIDUpdate\"name=\"hidUserIDUpdate\"type=\"hidden\" value=\""
						+ userID + "\">" + userName + "</td>";*/
				
				output += "<td>" + code + "</td>";
				output += "<td>" + price + "</td>";
				output += "<td>" + description + "</td>";
				
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td><td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-medid='"
						 + medID + "'>" + "</td></tr>";
				
				/*output += "<td><input name=\"btnUpdate\"type=\"button\" value=\"Update\"class=\" btnUpdate btn btn-secondary\"></td><td><form method=\"post\" action=\"User.jsp\"><input name=\"btnRemove\" type=\"submit\"value=\"Remove\" class=\"btn btn-danger\"><input name=\"hidUserIDDelete\" type=\"hidden\"value=\""
						+ userID + "\">" + "</form></td></tr>";*/
				
				/*
				 * output +=
				 * "<td><input name=\"btnUpdate\" type=\"button\"value=\"Update\" class=\"btn btn-secondary\"></td>"
				 * + "<td><form method=\"post\" action=\"items.jsp\">" +
				 * "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\"class=\"btn btn-danger\">"
				 * + "<input name=\"userID\" type=\"hidden\" value=\"" + userID + "\">" +
				 * "</form></td></tr>";
				 */
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the meds.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateMedDetails(String ID, String name, String code, String price, String description) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE pharmacy SET med_name=?,med_code=?,med_price=?,med_description=?WHERE med_id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setInt(2, Integer.parseInt(code));
			preparedStmt.setInt(3, Integer.parseInt(price));
			preparedStmt.setString(4, description);
			preparedStmt.setInt(5, Integer.parseInt(ID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			//output = "Updated successfully";
			
			String newMeds = readMeds();
			 output = "{\"status\":\"success\", \"data\": \"" +newMeds + "\"}";
			
		} catch (Exception e) {
			//output = "Error while updating the user.";
			output = "{\"status\":\"error\", \"data\":\"Error while updating the med.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteMeds(String med_id) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from pharmacy where med_id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(med_id));
			// execute the statement
			preparedStmt.execute();
			con.close();
			//output = "Deleted successfully";
			String newMeds = readMeds();
			 output = "{\"status\":\"success\", \"data\": \"" +newMeds + "\"}";
			
		} catch (Exception e) {
			//output = "Error while deleting the user.";
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the med.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	
}

