package temp;

import java.util.Date;

public class GenerateEmailDemo {

	public static void main(String[] args) {
		
		Date date = new Date();
		String dateString = date.toString();
		String noSpaceDateString = dateString.replaceAll(" ", "");
		String noSpaceAndColonsDateString = noSpaceDateString.replaceAll("\\:", "");
		String emailWithTimeStamp = noSpaceAndColonsDateString+"@gmail.com";
		System.out.println(emailWithTimeStamp);
		
	}

}
