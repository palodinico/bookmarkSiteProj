package com.bookmark.model;

import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

import static org.junit.Assert.*;

public class PersonTest {
    @Test
    public void canConstructAPersonWithANameAndId() {
    	AtomicLong counter = new AtomicLong();
    	
        Person person = new Person("Larry", counter.getAndIncrement());
        assertEquals("Larry", person.getName());
        assertEquals(0, person.getId());
        
        person = new Person("Bobbie", counter.getAndIncrement());
        assertEquals("Bobbie", person.getName());
        assertEquals(1, person.getId());
    }
}
