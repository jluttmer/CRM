// Main driver class for CRM program.  Imports/Exports leadArray and noteArray. Calls the Pages.mainMenu()
// to start program. Also contains the createLead and createNote functions to create Lead and Note objects.

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class CRMDriver
{
	static Scanner input; // scanner
	public static Scanner inputLeadFile; // scanner for leadArray list
	public static Scanner inputNoteFile; // scanner for noteArray list
	public static java.io.File leadInFile; // inFile for leadArray
	public static java.io.File leadOutFile; // outFile for leadArray
	public static java.io.File noteInFile; // inFile for noteArray
	public static java.io.File noteOutFile; // outFile for noteArray
	public static final int LEAD_MAX = 500; // leadArray MAX size
	public static final int NOTE_MAX = 4000; // noteArray MAX size
	public static final int FULLLINE = 147; // full width line
	public static void main(String[] args) throws IOException, ParseException
	{
		int listSize = 0; // leadArray size
		int noteListSize = 0; // noteArray size
		leadInFile = new java.io.File("leads"); // inFile name for leadArray
		leadOutFile = new java.io.File("leads"); // outFile name for leadArray
		noteInFile = new java.io.File("notes"); // inFile name for noteArray
		noteOutFile = new java.io.File("notes"); // outFile name for noteArray

		input = new Scanner (System.in);
		inputLeadFile = new Scanner(leadInFile);
		inputNoteFile = new Scanner(noteInFile);

		Lead [] leadArray = new Lead [LEAD_MAX]; // Declaring and initializing array of leads
		Note [] noteArray = new Note [NOTE_MAX]; // Declaring and initializing array of notes
		listSize = importList ( leadArray );
		noteListSize = importNoteList ( noteArray );
		Pages.mainMenu(listSize, noteListSize, noteArray, leadArray);

	} // end main

	/***************************************************************************************
	 * Receives a Lead object and lastLeadNum created (allows user to input data members)
	 * @param lastLeadNum, the last assigned leadNum (will be incremented for new Lead)
	 * @param newLead, Lead class object (user will input data)
	 * @return newLead
	 ***************************************************************************************/
	public static Lead createLead (int lastLeadNum, Lead newLead)
	{
		String object = "newLead"; // identifies this object for other methods
		String field; // identifies the field for other methods

		String name; // lead full name
		String address; // lead mailing address
		String email; // lead email address
		String phone; // lead phone number
		String status; // lead status
		String leadSource = null; // lead source
		int rating; // lead rating
		int leadNum; // assigned lead number

		field = "name";
		Display.toolTips( object, field );
		System.out.println ( "\nEnter lead name: " );
		name = input.nextLine();
		newLead.setName ( name );
		Display.dashLine(FULLLINE);

		field = "address";
		Display.toolTips( object, field );
		System.out.println ( "\nEnter mailing address: " );
		address = input.nextLine ( );
		newLead.setAddress ( address );
		Display.dashLine(FULLLINE);

		field = "email";
		Display.toolTips( object, field );
		System.out.println ( "\nEnter email address: " );
		email = input.nextLine ( );
		newLead.setEmail ( email );
		Display.dashLine(FULLLINE);

		field = "phone";
		Display.toolTips( object, field );
		System.out.println ( "\nEnter phone number: " );
		phone = input.nextLine ( );
		phone = phone.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3"); // format phone number
		newLead.setPhone ( phone );
		Display.dashLine(FULLLINE);

		field = "status";
		Display.toolTips( object, field );
		System.out.println ( "\nEnter status: " );
		status = input.nextLine ( );
		newLead.setStatus ( status );
		Display.dashLine(FULLLINE);

		field = "leadSource";
		Display.toolTips( object, field );
		System.out.println ( "\nEnter lead source: " );
		leadSource = input.nextLine ( );
		newLead.setLeadSource ( leadSource );
		Display.dashLine(FULLLINE);

		field = "rating";
		Display.toolTips( object, field );
		System.out.println ( "\nEnter lead rating: " );
		rating = input.nextInt ( );
		input.nextLine(); // nextLine bug fix, Thank you Vicki!
		newLead.setRating ( rating );
		Display.dashLine(FULLLINE);

		field = "leadNum";
		leadNum = lastLeadNum + 1;
		System.out.println ( "\nAssigned Lead Number: " + leadNum );
		newLead.setLeadNum ( leadNum );
		Display.dashLine(FULLLINE);

		return newLead;
	} // end createLead


	/***************************************************************************************
	 * Receives a Note object and leadNum (allows user to input note tied to lead)
	 * @param leadNum, lead number for link to leadArray
	 * @param newNote, Note class object (user will input data)
	 * @return newNote
	 ***************************************************************************************/
	public static Note createNote ( int leadNum, Note newNote )
	{
		String object = "newLead"; // identifies this object for other methods
		String field; // identifies the field for other methods
		String contents; // note contents
		Date date; // current date

		field = "leadNum";
		newNote.setLeadNum ( leadNum );

		field = "date";
		date = new Date ();
		newNote.setDate ( date );

		field = "contents";
		Display.toolTips( object, field );
		System.out.println ( "\nEnter note: " );
		contents = input.nextLine ( );
		newNote.setContents ( contents );
		Display.dashLine(FULLLINE);

		return newNote;
	}

	/***************************************************************************************
	 * Imports lead list to leadArray
	 * @param leadArray, array of leads (name, address, email, phone, status, leadSource, rating, leadNum)
	 ***************************************************************************************/
	public static int importList(Lead [] leadArray)
	{
		Lead leadObject = new Lead (); // Declaring object of Lead type, used to fill array
		int index = 0; // array location
		int listSize = 0; // counter for array size
		if(!leadInFile.exists())
		{
			System.out.println ( "file not found" );
			System.exit(-1);
		} // end IF inFile exists

		while (inputLeadFile.hasNext())
		{

			String name = inputLeadFile.nextLine (); // lead full name
			String address = inputLeadFile.nextLine (); // lead mailing address
			String email = inputLeadFile.nextLine (); // lead email address
			String phone = inputLeadFile.nextLine (); // lead phone number
			String status = inputLeadFile.nextLine (); // lead status
			String leadSource = inputLeadFile.nextLine (); // lead source
			int rating = inputLeadFile.nextInt (); // lead rating
			int leadNum = inputLeadFile.nextInt (); // lead generated ID number
			inputLeadFile.nextLine(); // import bug fix, Thank you Sarah!

			leadObject = new Lead (leadNum, name, address, email, phone, status, leadSource, rating);
			leadArray [index] = leadObject;
			index ++;
			listSize++;
		} // end WHILE hasNext

		inputLeadFile.close ();
		return listSize;
	} // end importList

	/***************************************************************************************
	 * Imports note list to noteArray
	 * @param noteArray, array of notes (leadNum, date, contents)
	 * @throws ParseException
	 ***************************************************************************************/
	public static int importNoteList(Note [] noteArray) throws ParseException
	{
		Note noteObject = new Note (); // Declaring object of Lead type, used to fill array
		int index = 0; // array location
		int noteListSize = 0; // counter for array size
		if(!noteInFile.exists())
		{
			System.out.println ( "file not found" );
			System.exit(-1);
		} // end IF inFile exists

		while (inputNoteFile.hasNext())
		{

			int leadNum = inputNoteFile.nextInt (); // lead number
			inputNoteFile.nextLine(); // import bug fix

			String dateAsString = inputNoteFile.nextLine (); // note date
			SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
			Date date = sdf.parse(dateAsString);

			String contents = inputNoteFile.nextLine (); // note contents

			noteObject = new Note (leadNum,contents, date);
			noteArray [index] = noteObject;
			index ++;
			noteListSize++;
		} // end WHILE hasNext

		inputNoteFile.close ();
		return noteListSize;
	} // end importList

	/***************************************************************************************
	 * Writes changes to leadArray
	 * @param leadArray, array of leads (name, address, email, phone, status, leadSource, rating, leadNum)
	 * @throws FileNotFoundException
	 ***************************************************************************************/
	public static void exportList(Lead [] leadArray) throws FileNotFoundException
	{
		java.io.PrintWriter fout= new java.io.PrintWriter(leadOutFile);

		for (int index = 0; index < LEAD_MAX; index++)
		{
			if (leadArray [index] != null)
			{
				fout.println(leadArray [index].getName()); // lead full name
				fout.println(leadArray [index].getAddress()); // lead mailing address
				fout.println(leadArray [index].getEmail()); // lead email address
				fout.println(leadArray [index].getPhone()); // lead phone number
				fout.println(leadArray [index].getStatus()); // lead status
				fout.println(leadArray [index].getLeadSource()); // lead source
				fout.println(leadArray [index].getRating()); // lead rating
				fout.println(leadArray [index].getLeadNum()); // lead number
			} // end IF null
		} // end FOR array loop
		fout.println( ); // prints blank line at end of list
		fout.close();
	} // end exportList

	/***************************************************************************************
	 * Writes changes to noteArray
	 * @param noteArray, array of notes (leadNum, date, contents)
	 * @throws FileNotFoundException
	 ***************************************************************************************/
	public static void exportNoteList(Note [] noteArray) throws FileNotFoundException
	{
		java.io.PrintWriter fout= new java.io.PrintWriter(noteOutFile);

		for (int index = 0; index < NOTE_MAX; index++)
		{
			if (noteArray [index] != null)
			{
				fout.println(noteArray [index].getLeadNum()); // lead number

				SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
				String formatDate = sdf.format(noteArray [index].getDate());
				fout.println(formatDate); // note date

				fout.println(noteArray [index].getContents()); // lead email address
			} // end IF null
		} // end FOR array loop
		fout.println( ); // prints blank line at end of list
		fout.close();
	} // end exportList

} // end CRMDriver
