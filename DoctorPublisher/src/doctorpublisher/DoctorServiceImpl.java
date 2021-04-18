package doctorpublisher;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import hms_db.Database;
import hms_db.DatabaseImpl;

public class DoctorServiceImpl implements DoctorService {
	private Connection connection = null;
	private Statement statement = null;
	private Database database;
	private ResultSet resultSet;
	
	public DoctorServiceImpl() {
		database = new DatabaseImpl();
		connection = database.getDatabaseConnection();
	}

	@Override
	public void getAvailableDoctors() {
		String sqlQuery = "SELECT * FROM doctors WHERE availability = 1";
		
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlQuery);
			
			// Display available doctor information to the console
			System.out.println("\n---------------------------Available Doctors--------------------------");
			System.out.println(String.format("%-10s %-25s %-20s %-20s", "Doctor ID", "Name", "Specialization", "Phone Number"));
			while (resultSet.next()) {
				String result = String.format("%-10s %-25s %-20s %-20s", resultSet.getInt("id"), "Dr. " + resultSet.getString("first_name") + 
						" " + resultSet.getString("last_name"), resultSet.getString("specialization"), resultSet.getString("phone_number"));
				System.out.println(result);
			}
			
		} catch (SQLException exc) {
			System.out.println("Issue with getting available doctor details !!!");
			System.out.println(exc.getMessage());
		} 
	}

	@Override
	public ResultSet getAllDoctors() {
		String sqlQuery = "SELECT * FROM doctors";
		
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlQuery);
		} catch (SQLException exc) {
			System.out.println("Issue with getting doctor details !!!");
			System.out.println(exc.getMessage());
		} finally {
			return resultSet;
		}
	}

	@Override
	public ResultSet searchDoctorDetails(Integer doctorId) {
		String sqlQuery = "SELECT * FROM doctors WHERE id = '"+ doctorId +"'";
		
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlQuery);
		} catch (SQLException exc) {
			System.out.println("Issue with getting doctor details !!!");
			System.out.println(exc.getMessage());
		} finally {
			return resultSet;
		}
	}

	@Override
	public void channelDoctor(Integer doctorId) {
		String sqlQuery1 = "UPDATE doctors SET availability = '"+ 0 +"' WHERE id = '"+ doctorId +"'";
		// Need to implement patient management bundle to implement rest of this function.
	}

	@Override
	public void insertDoctorDetails(Doctor doctor) {
		String sqlQuery = "INSERT INTO doctors(first_name, last_name, phone_number, specialization, availability) "
				+ "VALUES('"+ doctor.getFirstName() +"', '"+ doctor.getLastName() +"', '"+ doctor.getPhoneNumber() +"', "
						+ "'"+ doctor.getSpeciality() +"', '"+ doctor.getAvailability() + "')";
		
		try {
			statement = connection.createStatement();
			statement.executeUpdate(sqlQuery);
			System.out.println("Doctor details successfully inserted ...");
		} catch (SQLException exc) {
			System.out.println("Issue with inserting doctor details !!!");
			System.out.println(exc.getMessage());
		}
	}

}
