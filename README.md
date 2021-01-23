# P L E N T - A smart event planner application 

## Members
Goh Yi Ern <br> 
Tan Xin Yi <br> 
Kiran Mohan Bodhapati <br> 
Arissa Rashid <br> 
Naomi Kong-Vega <br> 

## Description
PLENT is your one stop school events app for students and organisers. Events are an integral part of maintaining the vibrant student life here in SUTD. They range from fifth row introductory sessions, performances, workshops and talks. However, filtering through these event advertisements on numerous platforms coupled with the repetitive signup process can be a pain. Organisers have more work cut out due to Covid-19 measures as well, such as keeping track of a participants capacity.

Click [here](https://youtu.be/8L8xmo6JUxs) to see a preview!

#### PLENT solves all of those problems for you:
- It condenses all event information in scrollable categories
- Quicker sign up as your account information (eg. name, student ID, email)  will be automatically sent to the organizers when you just click the “SIGN UP” button. 
- The user can effortlessly track of all their events in a calendar view that compiles and colour-codes their events based on their categories (eg. Fifth Row, Industry Talks, Student Life). 
- The user is notified if any timing clashes between events are detected
- For organisers, creation of events is standardised with “create events form” that only students with event organizer permissions (eg. EXCO members of fifth rows) are able to access and upload.

## Features 
Description | Image 
---|---
The login page will be the first page the user is directed to. From there, users can choose to login with an existing account or create an account, which will redirect them to the sign up page where they can sign up with their name, student ID and email. | <img src="/Plent%20Screenshots/Login.png" width="1500" > 
In the home page, they can browse all upcoming events based on their categories (eg. fifth row, industry talk, student life). | <img src="Plent%20Screenshots/All%20Events.png" width="1500" > 
There is also a search feature to help them quickly find a specifc event based on its name. | <img src="Plent%20Screenshots/Search%20Events.png" width="1500" > 
When an event is clicked, the user will be redirected to the specific event page where the date, time, location, description and poster is displayed. In the case that the user is signed up for another event that clashes with the event they are about to sign up for, a warning label will be shown above the “sign up” button. Otherwise, they can just sign up by clicking on the button. If the user wants to join a telegram group/contact the organizer regarding some enquiries they might have, they will be either redirected to the telegram group link on their browser to join, or the organizer’s email will be copied to their clipboard.| <img src="/Plent%20Screenshots/Single%20Event.png" width="1500" > 
Users can also view their events all compiled onto one convenient calendar via the calendar view page, that they can either access through the menu button on the top right or the floating red button on the events page. Events are colour coded based on the category for easy identification. Users can toggle back to the events page easily by clicking on the floating red button as well.| <img src="/Plent%20Screenshots/Event%20Calendar.png" width="1500" > 
Organizers can create new events here, by inputting their event’s title, type (i.e. category), date, time, location, description, poster picture (uploaded from their phone) and telegram group link (optional). Once the form is filled, they can submit it via the button on the top right corner, and the new event will be immediately uploaded to the database so all the users can access| <img src="/Plent%20Screenshots/Create%20Event.png" width="1500" > 
Organizers are able to view all the events they have created here, as well as the participants’ information (i.e. name, student ID, email) when they click on the specific events.| <img src="/Plent%20Screenshots/Participants.png" width="1500" > 

## External Applications 

### Firebase 
Our team used Firebase to store the credentials of the users. During sign up, once the required fields are checked for certain criteria, the email and the password are used to create a user. If the task is successful, we then get the current user from Firebase and then pass over the other details to be stored in the MongoDB database.

### MongoDB
Our team used MongoDB to store information of both events and users. To connect our Java application with MongoDB, we created a Flask backend to process, retrieve and store data for our app.

### Flask
To connect our Java application to MongoDB, our team created an API using Flask. Our API included POST requests to create new users and events (eg. Sign Up page, Create Events page), GET requests to retrieve information about both users and events (eg. Find Events page, Calendar page, Login page) and PUT requests to edit information about users and events (eg. when users sign up or cancel attendance for events). 

### Retrofit 2
In order to make calls to the backend, our team used the Retrofit library which makes it easy to retrieve and upload JSON (or other structured data). In Retrofit we configured a GSon converter for data serialisation. We created model classes such as “Event” and “User” which are used as a JSON model and also defined an interface containing the various methods that were used to fetch and push data to the backend.




