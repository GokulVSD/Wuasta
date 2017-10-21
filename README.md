<p align="center">
<a href="https://imgur.com/JF4WNim"><img src="https://i.imgur.com/JF4WNim.png" title="source: imgur.com" /></a></p>
<p align="center">
  
 <h3 align="center"> <br>
  Wake Up And Smell the Alarm
  <br> </h3>
 
<h5 align="center">A predictive <strong>Alarm Assistant</strong> which takes into account real-time traffic conditions.</h5>

## 

<p align="center">Wuasta is an Android app written in Java that makes your mornings a lot less stressful. </p>
<p align="center">With some parameters, such as your home location, work location, the amount of time you need in the morning to get ready, and the time at which you need to be at work, the app calls Google Maps Distance Matrix API, taking into account the traffic conditions based on historical traffic data, with an inital departure time. Based on the commute duration returned from the API call, the app calls the API again, now with an adjusted departure time based on whether or not the previous API call overshot or undershot its previous prediction (initial prediction being 30 mins). This process is repeated until a departure time is found such that the arrival time is within 3 mins of the specified time at which the user needs to be at work.   </p>

## 

### Placeholder for GIF

## 

<strong>Example:</strong>
<p align="center">Lets say you need to reach your work at 9:00 AM. The first API call is made with a departure time of 8:30 AM. The API then returns a commute duration of 20 mins (undershot, because leaving your home at 8:30 would result in reaching your work at 8:50 AM).</p>

<p align="center">The API is called again, now with a departure time of 9:00 AM minus 20 mins, i.e. 8:40 AM. The API returns a commute duration of 23 mins (since traffic would've increased if you depart at 8:40 AM, as opposed to 8:30 AM). This means that you'll arrive at work at 9:03 AM if you leave at 8:40 AM (overshot).</p>

<p align="center">The API is called again, now with a departure time of 9:00 AM minus 23 mins, i.e. 8:37 AM. The API returns a commute duration of 22 mins (since there is slightly lesser traffic by leaving 3 mins earlier than the previous departure time). This means that you'll arrive at work at 8:37 + 22 mins = 8:59 AM. Now 8:59 AM is greater than 8:57 AM, and lesser than 9:00 AM (3 mins of cushion time).</p>

<p align="center">Now, the app subtracts the time that the user requires to get ready in the morning, say 30 mins (referred to as "delay" in the app), and suggests setting an alarm at 8:07 AM (8:37 AM minus 30 mins). Therefore, if you wake up 8:07 AM, and all goes well, and you finish getting ready within those 30 mins and start the commute to your work, you'll reach your work exactly at the time you wanted to reach, i.e. 8:59 AM, approximately 9:00 AM.</p>

<br />

<p align="center">It usually takes about 3 to 4 API calls to get the correct predicted departure time. </p>

<br />

<p align="center">Some of you may be asking, "Why can't we just specify the arrival time instead of the departure time when calling the API, that way we don't need to make multiple API calls." Well I wish we could, but you cannot specify an arrival time for the API call if you want to take into account historical traffic conditions. So yes, it would reduce the number of API calls, but it's pointless since it won't be giving you an accurate commute duration, as it isn't considering traffic conditions. </p>

## 

<p align="center"><a href="https://imgur.com/ahjwoTN"><img src="https://i.imgur.com/ahjwoTN.png" title="source: imgur.com" /></a></p>

<br />

## Use Cases
> * The ordinary Joe, who wants to sleep for as long as possible, and wake up at the latest possible time, yet still reaching work on time.
> * Someone invites you to have dinner with them in a restaurant at 8:30 PM, and you want to be alerted as to when you have to leave your home in order to reach the restaurant at exactly 8:30 PM, taking into account traffic conditions.
> * Many, many more use cases.

<br />

## APIs Used
<p><strong>Google Maps Distance Matrix API</strong> for predicting departure time</p>
<p><strong>Google Maps Places API</strong> for picking home and work locations</p>
<p><strong>Weatherbit.io API</strong> for predicted weather information at the time of departure</p>



<br />

## Tools Utilised
 <p> <strong>Assets:</strong> Photoshop and Illustrator </p>
 <p> <strong>Code:</strong> IntelliJ Idea </p>
 <p> <strong>Markup:</strong> Sublime 3 </p>
 
 <br />

## InGenius 2017
  Written through the course of the 24-hour hackathon
  
#### *Started: September 20th, 2017*

#### *Finished: September 24th, 2017*

<br />

## Authors
<p><strong>Gokul Vasudeva</strong>   https://github.com/gokulvsd</p>
<p><strong>Anusha A</strong>   https://github.com/anushab05</p>

<br />

*You must use your own API keys, add the Google API key to the manifest, and the wuastaFragment.java by replacing "ADD_YOUR_KEY_HERE",
and add the Weatherbit.io API key to the wuastaFragment.java by replacing "ADD_YOUR_WEATHER_KEY_HERE".*

<br />

*The authors give permission to fork the repository and reuse code, but publishing code onto the play store with the core functionality of this app is prohibited.*
