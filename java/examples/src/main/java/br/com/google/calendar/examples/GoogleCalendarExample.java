package br.com.google.calendar.examples;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

public class GoogleCalendarExample {

    private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static HttpTransport HTTP_TRANSPORT;

    static {
	try {
	    HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	} catch (Throwable t) {
	    t.printStackTrace();
	    System.exit(1);
	}
    }

    public static Credential authorize() throws IOException, GeneralSecurityException {
	GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream("src/main/resources/guilhermehbueno-bd29ee4439f0.json")).createScoped(Collections.singleton(CalendarScopes.CALENDAR));
	return credential;
    }

    public static com.google.api.services.calendar.Calendar getCalendarService() throws Exception {
	Credential credential = authorize();
	return new com.google.api.services.calendar.Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
    }

    public static void main(String[] args) throws Exception {
	com.google.api.services.calendar.Calendar service = getCalendarService();
	DateTime now = new DateTime(System.currentTimeMillis());
	Events events = service.events().list("8kml4ppr5358k1r5hi4uihaj9o@group.calendar.google.com").setMaxResults(10).setTimeMin(now).setOrderBy("startTime").setSingleEvents(true).execute();
	List<Event> items = events.getItems();
	if (items.size() == 0) {
	    System.out.println("No upcoming events found.");
	} else {
	    System.out.println("Upcoming events");
	    for (Event event : items) {
		DateTime start = event.getStart().getDateTime();
		if (start == null) {
		    start = event.getStart().getDate();
		}
		System.out.printf("%s (%s)\n", event.getSummary(), start);
	    }
	}
    }
}