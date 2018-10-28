<p align="center">
<a href="https://imgur.com/JF4WNim"><img src="https://i.imgur.com/JF4WNim.png" title="source: imgur.com" /></a></p>
<p align="center">
  
 <h3 align="center"> <br>
  Wake Up And Smell the Alarm
  <br> </h3>
 
<h5 align="center">A predictive <strong>Alarm Assistant</strong> which takes into account real-time traffic conditions.</h5>

## 

<p align="center">Wuasta is an Android app that tries to make your mornings less stressful by removing the guessing involved in figuring out the optimal time to wake up in order to be where you wanna be on time. </p>
<p align="center">With a home location, a work location, the amount of time you need in the morning to get ready before you leave, and the time at which you need to be at work, the app calls Google Maps' Distance Matrix API, taking into account the traffic conditions based on historical traffic data and current traffic conditions, with an initial departure time. Based on the commute duration returned from the API call, the API is called again, now with an adjusted departure time based on whether the previous API call overshot or undershot its previous prediction. This process is repeated until a departure time is found such that the arrival time is within 3 minutes of the specified time at which the user needs to be at work. The user may then choose to set an alarm within the app for the optimal wake up time that was predicted. Relevant weather info is also provided, along with navigational directions. The UI was made targetting API level 25.</p>

## 

<img src="/art/1.gif?raw=true" width="32%" /> <img src="/art/2.gif?raw=true" width="32%" /> 
<img src="/art/3.gif?raw=true" width="32%" />

## 

<strong>Explanation:</strong>
<p align="center">Lets say you need to reach your work at 9 AM. The first API call is made with a departure time of 9 AM. The API then returns a commute duration of 20 mins.</p>

<p align="center">The API is called again, now with a departure time of 9 AM minus 20 mins, i.e. 8:40 AM. The API returns a commute duration of 16 mins (since traffic would be a bit lighter if you depart at 8:40 AM, as opposed to 9 AM). This means that you'll arrive at work at 8:56 AM if you leave at 8:40 AM (undershot).</p>

<p align="center">The API is called again, now with a departure time of 9 AM minus 16 mins, i.e. 8:44 AM. The API returns a commute duration of 18 mins (since there is slightly heavier traffic when leaving at 8:44 as opposed to 8:40). This means that you'll arrive at work at 8:44 + 18 mins = 9:02 AM (overshot).</p>

<p align="center">The API is called again, now with a departure time of 9 AM minus 18 mins, i.e. 8:42 AM. The API returns a commute duration of 17 mins (since there is slightly lighter traffic by leaving at 8:42 as opposed to 8:44). This means that you'll arrive at work at 8:42 + 17 mins = 8:59 AM. Now 8:59 AM is greater than 8:57 AM, and lesser than 9 AM (3 mins of cushion time).</p>

<p align="center">Now, the app subtracts the time that the user requires to get ready in the morning, say 30 mins (referred to as "delay" in the app), and suggests setting an alarm at 8:12 AM (8:42 AM minus 30 mins). By this logic, if you wake up 8:12 AM, and all goes well, and you finish getting ready within those 30 mins and start the commute to your work, you'll reach your work exactly at the time you wanted to reach, i.e. 8:59 AM, fashionably early.</p>

<br />

<p align="center">It usually takes about 3 to 4 API calls to get the correct predicted departure time. The API Calls are made asynchronously.</p>

<br />

<p align="center"><strong>To answer the question</strong>, "Why can't we just specify the arrival time instead of the departure time when calling the API, that way we don't need to make multiple API calls." Well I wish we could, but you cannot specify an arrival time for the API call if you want to take into account traffic conditions. So yes, it would reduce the number of API calls, but it's literally pointless since it won't be giving you an accurate commute duration, as it isn't considering traffic conditions. The whole reason we made this app to begin with was because we were frustrated that Google Maps doesn't provide this functionality (which may change in the future. As of now, this functionality doesn't exist anywhere else that we could find).</p>

## 

<br />

<p align="center"><a href="https://imgur.com/chz5FaL"><img src="https://i.imgur.com/chz5FaL.png" title="source: imgur.com" /></a></p>

<br />

## Use Cases
 * The ordinary Joe, who wants to sleep for as long as possible, and wake up at the latest possible time, yet still reaching work on time.
 * Someone invites you to have dinner with them in a restaurant at 8:30 PM, and you want to be alerted as to when you have to leave your home in order to reach the restaurant at exactly 8:30 PM, taking into account traffic conditions.
 * Many, many more use cases.

<br />

## APIs Used
<p><strong>Google Maps Distance Matrix API</strong> for predicting departure time</p>
<p><strong>Google Maps Places API</strong> for picking home and work locations</p>
<p><strong>Weatherbit.io API</strong> for predicted weather information at the time of departure</p>



<br />

## Tools Utilised
 <p> <strong>Assets:</strong> Photoshop and Illustrator (for bitmaps)</p>
 <p> <strong>Code:</strong> IntelliJ Idea (and Android Studio for Gradle and Manifest stuff)</p>
 <p> <strong>XML markup:</strong> Sublime 3 (our favourite text editor!)</p>
 
 <br />

## InGenius 2017
  Designed and Written through the course of the 24-hour hackathon held on September 24th, 2017. Including planning and creation of assets, it took us about a week.

<br />

## Kalpana 2017
  Presented during the first ever iteration of PESIT's IEEE charity project exhibition, Kalpana, held on October 28th, 2017. The event had strong emphasis on empowerment of women in engineering, participation required at least one female team member, which this project conveniently had.

<br />

## Creators
<p><strong>Gokul Vasudeva</strong>   https://github.com/gokulvsd</p>
<p><strong>Anusha A</strong>   https://github.com/anushab05</p>
Coded on a single laptop, hence why the second collaborator doesn't have many commits.
<br />

##
```
You must use your own API keys, add the Google API key to the manifest, and the wuastaFragment.java 
by replacing "ADD_YOUR_KEY_HERE", and add the Weatherbit.io API key to the wuastaFragment.java by 
replacing "ADD_YOUR_WEATHER_KEY_HERE".
```
<br />

##
*The creators give permission to fork the repository and reuse code, but publishing code onto the play store with the core functionality of this app is prohibited, not to mention, thats a pretty douchy thing to do.*

