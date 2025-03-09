package com.example.persistance;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class StringStoreInstrumentedTest {

    @Test
    public void write_NamesToStringStore(){

        //Run Test Methods
        StringNameStore stringNameStore = new StringNameStore() ;
        Context testContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ArrayList<Name> names = new ArrayList<>(Arrays.asList(new Name("Oliver","Fitzgerald"), new Name("Ben","Burt"), new Name("Conor","Gray"), new Name("Jing","Peng")));

        stringNameStore.writeNames(testContext, names);

        //Validate File Exists
        File testFile = new File(testContext.getFilesDir(), "names.dsv");
        assertTrue(testFile.exists());

        //Validate File Content
        try {
            FileInputStream fis = testContext.openFileInput("names.dsv");
            byte[] rawTestFileContent = new byte[fis.available()];
            fis.read(rawTestFileContent);
            fis.close();

            String testFileContent = new String(rawTestFileContent);
            assertEquals("Oliver|Fitzgerald\nBen|Burt\nConor|Gray\nJing|Peng\n",testFileContent);

        } catch (IOException exception) {
            System.out.printf("ERROR: Reading from test file: %s", exception.getMessage());
        }
    }

    @Test
    public void read_namesFromStringStore() {

        //Initialize Test Data
        ArrayList<Name> expected = new ArrayList<>(Arrays.asList(new Name("Oliver","Fitzgerald"), new Name("Ben","Burt"), new Name("Conor","Gray"), new Name("Jing","Peng")));
        Context testContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        //Run Test Methods
        StringNameStore stringNameStore = new StringNameStore();
        stringNameStore.writeNames(testContext, expected);

        //Validate File Content
        ArrayList<Name> actual = new StringNameStore().getNames(testContext);
        assertEquals(actual.size(), expected.size());

        for (int i = 0; i < actual.size(); i++) {
            assertEquals(actual.get(i).getFirst(),expected.get(i).getFirst());
            assertEquals(actual.get(i).getLast(),expected.get(i).getLast());
        }
    }

    @Test
    public void write_NamesToJSONStore(){

        //Initialize Test DAta
        Context testContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ArrayList<Name> expectedNames = new ArrayList<>(Arrays.asList(new Name("Oliver","Fitzgerald"), new Name("Ben","Burt"), new Name("Conor","Gray"), new Name("Jing","Peng")));

        //Run Test Methods
        JSONNameStore stringNameStore = new JSONNameStore() ;
        stringNameStore.writeNames(testContext, expectedNames);

        //Validate File Exists
        File testFile = new File(testContext.getFilesDir(), "names-JSON.json");
        assertTrue(testFile.exists());

        //Read Actual json File Content
        ArrayList<Name> actualNames = new ArrayList<>();
        try {
            FileInputStream fis = testContext.openFileInput("names-JSON.json");
            JsonReader reader = new JsonReader(new InputStreamReader(fis, StandardCharsets.UTF_8));


            reader.beginArray();
            while (reader.hasNext()) {
                reader.beginObject();
                while (reader.hasNext()) {

                    reader.nextName();
                    String firstName = reader.nextString();
                    reader.nextName();
                    String lastName = reader.nextString();
                    actualNames.add(new Name(firstName, lastName));
                }
                reader.endObject();
            }
            reader.endArray();
            reader.close();

        } catch (IOException exception) {
            System.out.printf("ERROR: Reading from test file: %s", exception.getMessage());
        }

        //Validate File Contents
        assertEquals(actualNames.size(), expectedNames.size());
        for (int i = 0; i < actualNames.size(); i++) {
            assertEquals(expectedNames.get(i).getFirst(), actualNames.get(i).getFirst());
            assertEquals(expectedNames.get(i).getLast(), actualNames.get(i).getLast());
        }
    }

    @Test
    public void read_namesFromJSONStore() {

        //Initialize Test Data
        ArrayList<Name> expected = new ArrayList<>(Arrays.asList(new Name("Oliver","Fitzgerald"), new Name("Ben","Burt"), new Name("Conor","Gray"), new Name("Jing","Peng")));
        Context testContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        //Run Test Methods
        JSONNameStore jsonNameStore = new JSONNameStore();
        jsonNameStore.writeNames(testContext, expected);
        ArrayList<Name> actual = jsonNameStore.getNames(testContext);

        //Validate File Content
        assertEquals(actual.size(), expected.size());
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(actual.get(i).getFirst(),expected.get(i).getFirst());
            assertEquals(actual.get(i).getLast(),expected.get(i).getLast());
        }
    }
}