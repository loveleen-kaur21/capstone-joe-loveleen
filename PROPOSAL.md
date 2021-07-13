## Description and Motivation
We want to make an automated nursing schedule to alleviate stress on managers from manually scheduling monthly based shifts. This would make trading and viewing shifts
much more portable. Our application includes 3 shifts per day with 1 manager, 2 Personal Care Assistants, and 5 nurses per shift. The algorithm will ensure that each person will 
have 7 work days and 7 off days. Along with scheduling, a user can request trade shifts with another user, and that trade can be confirmed off of a manager. 


## Prior Art
NurseGrid is one of our prior arts that we are driving inspiration from. On Nurse Grid you are able to access your schedule, add shifts, direct message colleagues,
and connect with colleagues. The only difference our app will have is to request shift trades with other users. Google calendar is another prior art that we are 
drawing inspiration from. On Google Calendar you can request events with other users. However no one is able to accept the user request other than the user themselves.
On our app being in a professional setting, manager approval is required to swap shift trades.


## Core User Workflows
We are building an application that requires user login based on roles. A user can sign up with the role of either Nurse, Personal Care Assistant, or Manager. 
Nurses and Personal Care Assistants are limited to a user only view. This means that the user can only view their own schedules and others' schedule and send shift trade
requests. Whereas if a user was a Manager, the manager will have access to an Admin based view. The admin based view is given all accessibities of a User based view
with the added factor of accepting or denying shift trade requests. Managers can also personally move shifts around. This application will have a calendar representing
a schedule with the logged in user's schedule highlighted. 


## Project Timeline:

### Week 1 Goals
- Sign up, Login, and Logout(Would include making the template pages, user repo, user service, and user class.) : Loveleen
- Navbar(Would include the template page and role based pages. For example admins has an extra page for accepting schedule changes) : Loveleen
- Populate the users table with 50 dummy data users. : Joseph & Loveleen
- Database(Setting up our database and tables for users, shifts, and requests. The users table would have an email and 
  password with some role logic on if they are a nurse, pca, or manager. As well as the shifts table having an id, user id, date, and any of the 3 
  different types of shifts. The request table will have the requesters user id, the requestee's user id, the requesters shift id, the requestee's shift id, and the accepted field. 
  Also for now the only table we will fill with data is the users table we will just set up the base stuff for the shifts table.) : Joseph
  

### Week 2 Goals
- Setting up our request object, repo, and service. : Loveleen
- Setting up our shift object, repo, and service. : Joseph
- Setting up test cases for shift logic. : Joseph & Loveleen 
      - Each user has 7 days for work and 7 days off of work.
      - Make sure there is 1 manager, 2 pcas, and 5 nurses for each shift.
  

### Week 3 Goals
- Create template for only manager accounts for accepting shift change requests. : Joseph
- Set up request logic for users to switch shift schedules. : Joseph
- Populating the shifts table within the database with dummy shift data for each user.(As of now what that might look like is randomly generating schedules)
  : Joseph & Loveleen
- Set up logic for showing 2 5 week periods. : Loveleen


### Week 4 Goals
- Setting up logic for highlighting a specific users schedule. : Loveleen
- Style the site with either UIkit or Spectre. : Joseph & Loveleen
      - Customize login
      - logout
      - signup
      - Home page
      - Admin Requests page
      - User Request page
      - Submitted Request page
      - User Pending Request page
      - Navbar     
- If there is time set up the additional feature with an automatic email sent to each user once a shift has been changed. : Joseph & Loveleen
- Bug Testing : Joseph & Loveleen


### Week 5 Goals
- Practice presentations
- Finishing touches


### Technologies
- Spring
- Java
- UIKit/Spectre
- Thymeleaf
- PostgreSQL
