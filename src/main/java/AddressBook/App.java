package AddressBook;

import java.io.*;
import java.util.*;

import AddressBook.json.JSONArray;
import AddressBook.json.JSONObject;
import AddressBook.json.XML;
import AddressBook.json.JSONException;

public class App
    {

    public static void main( String[] args ) throws JSONException, IOException {
        String loc = "";
        if (args.length == 1) {
            loc = args[0];
        } else {
            System.out.println("Invalid input");
            System.exit(1);
        }

        // gets and checks file type
        String[] fileSplit = loc.split("[.]");
        String fileExt = fileSplit[fileSplit.length-1];
        if (!(fileExt.equals("xml") || fileExt.equals("json"))) {
            System.out.println("Invalid file type");
            System.exit(1);
        }

        BufferedReader br = new BufferedReader(new FileReader(loc));
        String line = "", input = "";
        while ((line = br.readLine()) != null) {
            input += line;
        }

        // convert data to
        JSONObject jsonObject;
        if (fileExt.equals("xml")) {
           jsonObject = XML.toJSONObject(input);
        } else {
            jsonObject = new JSONObject(input);
        }

        // Validate each contact
        ArrayList<JSONObject> contactsMissingInfo = new ArrayList<JSONObject>();
        JSONArray contacts = jsonObject.getJSONObject("AddressBook").getJSONArray("Contact");

        for (Object contact: contacts) {
            JSONObject jsonContact = (JSONObject) contact;
            try {
                Contact currentContact = new Contact(jsonContact);
            } catch (JSONException e) {
                contactsMissingInfo.add(jsonContact);
            }
        }

        // display contracts that  are missing info
        System.out.println(contactsMissingInfo.size() + " Contacts missing 1 or more data fields");
        for (JSONObject missingContact: contactsMissingInfo) {
            System.out.println(missingContact);
        }
        System.out.println();

        // Convert data to other form and saves file
        String outputFileName, outputString;
        if (fileExt.equals("xml")) {
            System.out.println("Converting file to json:");
            outputString = jsonObject.toString(2);
            outputFileName = "output.json";

        } else {
            System.out.println("Converting file to xml:");
            outputString = XML.toString(jsonObject, 2);
            outputFileName = "output.xml";
        }

        File outputFile = new File(outputFileName);
        outputFile.createNewFile();
        FileWriter myWriter = new FileWriter(outputFileName);
        myWriter.write(outputString);
        myWriter.close();
    }
}
