package com.talos.invertedindex;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.talos.invertedindex.util.StringFunction;


/**
 * Unit test for simple App.
 */
public class InvertedIndexTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	String[] data = new String[]{ 
			 "A brilliant, festive study of JS Bach uses literature and painting to illuminate his 'dance-impregnated' music, writes Peter Conrad", 
			 "Fatima Bhutto on Malala Yousafzai's fearless and still-controversial memoir", 
			 "Grisham's sequel to A Time to Kill is a solid courtroom drama about racial prejudice marred by a flawless white hero, writes John O'Connell", 
			 "This strange repackaging of bits and pieces does the Man Booker winner no favours, says Sam Leith", 
			 "Another book with music related content" 
			    };

	@Test
	public void searchOnlyOneWord () throws Exception {		
		InvertedIndex invertedIndex = new InvertedIndex(data);
		String[] results = invertedIndex.get("music");
		assertTrue(results[0] == "A brilliant, festive study of JS Bach uses literature and painting to illuminate his 'dance-impregnated' music, writes Peter Conrad");
		assertTrue(results[1] == "Another book with music related content");
	}
	
	@Test
	public void searchAphrase () throws Exception {		
		InvertedIndex invertedIndex = new InvertedIndex(data);
		String[] results = invertedIndex.get("solid courtroom drama");
		assertTrue(results[0] == "Grisham's sequel to A Time to Kill is a solid courtroom drama about racial prejudice marred by a flawless white hero, writes John O'Connell");
	}
	
	@Test
	public void searchWithNoPhrase () throws Exception {		
		exception.expect(Exception.class);
		InvertedIndex invertedIndex = new InvertedIndex(data);
		String[] results = invertedIndex.get("");
	}
	
	@Test
	public void searchWithNoData () throws Exception {		
		exception.expect(Exception.class);
		InvertedIndex invertedIndex = new InvertedIndex(null);
		String[] results = invertedIndex.get("music");
	}

	@Test
	public void searchWithNoResult () throws Exception {		
		InvertedIndex invertedIndex = new InvertedIndex(data);
		String[] results = invertedIndex.get("hola");
		assertTrue(results == null);
	}
	
	@Test
	public void deleteEspecialCharacter () throws Exception {		
		String word = StringFunction.deleteSpecialCharacters("music,");
		assertTrue(word.equals("music"));
	}
	
}
