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
- Login 
- Logout
- Sign Up
  - Accounts associates with a Role (Nurse, Personal Care Assisstant, or Manager)
- User can view schedule
  - Everyone's schedule is shown
  - User's own schedule is highlight
  - Manager can manually switch shifts
  - Shows 2 5-weekk Period Schedules
- User can request shift change
  - Manager can accept/ decline shift changes requests
  - User can view all pending shift requests
  -


## Project Timeline:

<dl>
<dt> Week 1 Goals
<dd>- Sign up, Login, and Logout(Would include making the template pages, user repo, user service, and user class.) : Loveleen
<dd>- Navbar(Would include the template page and role based pages. For example admins has an extra page for accepting schedule changes) : Loveleen
<dd>- Populate the users table with 50 dummy data users. : Joseph & Loveleen
<dd>- Database(Setting up our database and tables for users, shifts, and requests. The users table would have an email and 
  password with some role logic on if they are a nurse, pca, or manager. As well as the shifts table having an id, user id, date, and any of the 3 
  different types of shifts. The request table will have the requesters user id, the requestee's user id, the requesters shift id, the requestee's shift id, and the accepted field. Also for now the only table we will fill with data is the users table we will just set up the base stuff for the shifts table.) : Joseph
  

<dt> Week 2 Goals
<dd>- Setting up our request object, repo, and service. : Loveleen
<dd>- Setting up our shift object, repo, and service. : Joseph
<dd>- Setting up test cases for shift logic. : Joseph & Loveleen 
<dd>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;* Each user has 7 days for work and 7 days off of work.
<dd>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;* Make sure there is 1 manager, 2 pcas, and 5 nurses for each shift.
  

<dt> Week 3 Goals
<dd>- Create template for only manager accounts for accepting shift change requests. : Joseph
<dd>- Set up request logic for users to switch shift schedules. : Joseph
<dd>- Populating the shifts table within the database with dummy shift data for each user.(As of now what that might look like is randomly generating schedules)
  : Joseph & Loveleen
<dd>- Set up logic for showing 2 5-week periods. : Loveleen


<dt> Week 4 Goals
  <dd>- Setting up logic for highlighting a specific users schedule. : Loveleen
  <dd>- Style the site with either UIkit or Spectre. : Joseph & Loveleen
      <dd>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Customize login
      <dd>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-logout
      <dd>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- signup
      <dd>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Home page
      <dd>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Admin Requests page
      <dd>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- User Request page
      <dd>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Submitted Request page
      <dd>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- User Pending Request page
      <dd>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Navbar     
      <dd>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- If there is time set up the additional feature with an automatic email sent to each user once a shift has been changed. : Joseph & Loveleen
      <dd>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Bug Testing : Joseph & Loveleen


<dt> Week 5 Goals
<dd> Practice presentations
<dd> Finishing touches
</dl>

## Technologies
- Spring
- Java
- UIKit/Spectre
- Thymeleaf
- PostgreSQL

https://github.com/loveleen-kaur21/capstoneProject-joe-loveleen
