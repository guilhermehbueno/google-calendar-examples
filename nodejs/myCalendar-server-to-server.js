var google = require('googleapis');
var calendar = google.calendar('v3');
var key = require('./guilhermehbueno-bd29ee4439f0.json');
var SCOPES = ['https://www.googleapis.com/auth/calendar'];
var jwtClient = new google.auth.JWT(key.client_email, null, key.private_key, SCOPES, null);



jwtClient.authorize(function(err, tokens) {
  if (err) {
    console.log(err);
    return;
  }

  calendar.events.list({
    auth: jwtClient,
    calendarId: '8kml4ppr5358k1r5hi4uihaj9o@group.calendar.google.com',
    timeMin: (new Date()).toISOString(),
    maxResults: 10,
    singleEvents: true,
    orderBy: 'startTime'
  }, function(err, response) {
    if (err) {
      console.log('The API returned an error: ' + err);
      return;
    }
    var events = response.items;
    if (events.length == 0) {
      console.log('No upcoming events found.');
    } else {
      console.log('Upcoming 10 events:');
      for (var i = 0; i < events.length; i++) {
        var event = events[i];
        var start = event.start.dateTime || event.start.date;
        console.log('%s - %s', start, event.summary);
      }
    }
  });




var event = {
  'summary': 'Aula de inglês',
  'location': '800 Howard St., San Francisco, CA 94103',
  'description': 'A chance to hear more about Google\'s developer products.',
  'start': {
    'dateTime': '2016-03-25T09:00:00-07:00',
    'timeZone': 'America/Los_Angeles',
  },
  'end': {
    'dateTime': '2016-03-25T17:00:00-07:00',
    'timeZone': 'America/Los_Angeles',
  },
  'recurrence': [
    'RRULE:FREQ=DAILY;COUNT=2'
  ],
  'attendees': [
    {'email': 'lpage@example.com'},
    {'email': 'sbrin@example.com'},
  ],
  'reminders': {
    'useDefault': false,
    'overrides': [
      {'method': 'email', 'minutes': 24 * 60},
      {'method': 'popup', 'minutes': 10},
    ],
  },
};

calendar.events.insert({
  auth: jwtClient,
  calendarId: '8kml4ppr5358k1r5hi4uihaj9o@group.calendar.google.com',
  resource: event,
}, function(err, event) {
  if (err) {
    console.log('There was an error contacting the Calendar service: ' + err);
    return;
  }
  console.log('Event created: %s', event.htmlLink);
});


});