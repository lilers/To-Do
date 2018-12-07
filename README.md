# Pre-work - *To Do*

**To Do** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Lily Tran**

Time spent: **7.8** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **view a list of todo items**
* [x] User can **successfully add and remove items** from the todo list
* [x] User's **list of items persisted** upon modification and and retrieved properly on app restart

The following **optional** features are implemented:

* [x] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list

The following **additional** features are implemented:

* [x] Persist the todo items into SQLite instead of a text file using Room
* [x] Improve style of the todo items in the list using a custom adapter
* [x] Add support for completion due dates for todo items (and display within listview item)
* [x] Use a DialogFragment instead of new Activity for editing items
* [x] Improve the UI / UX of your app including icons, styling, color, spacing of your app.
* [x] Add support for selecting the priority of each todo item (and display in listview item)
* [x] Users can delete/clear all todo items
* [x] Users can cancel adding/editing items
* [x] Use a DatePickerDialog to allow users to pick a due date from a calendar

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://i.imgur.com/WOtpxFb.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Project Analysis

As part of your pre-work submission, please reflect on the app and answer the following questions below:

**Question 1:** "What are your reactions to the Android app development platform so far? Compare and contrast Android's approach to layouts and user interfaces in past platforms you've used."

**Answer:** I've had experience using Android Studio before, so nothing was new or unexpected for me. There are a number of differences between Android Studio and Xcode. For example Android Studio doesn't have an automatic split view for layouts and layout files like Xcode. Also when connecting any layout items to the layout file Android Studio requires you to find it by id unlike Xcode where you can click and drag to make a connection. Overall I think Android Studio is a good platform.

**Question 2:** "Take a moment to reflect on the `ArrayAdapter` used in your pre-work. How would you describe an adapter in this context and what is its function in Android? Why do you think the adapter is important? Explain the purpose of the `convertView` in the `getView` method of the `ArrayAdapter`."

**Answer:** The ArrayAdapter converted our list items into views that could be used to populate our ListView. Based on this information I concluded that adapters provide acess to data and are responsible for creating views for the data. This task is important because it allows the data to be presented to the user. The convertView checks if there's an existing view and reuses it if it exists, otherwise a view is created.

## Notes

Describe any challenges encountered while building the app.

I had trouble understanding how to implementing Room using only the CodePath guide and as a result ran into the "Cannot access database on main thread since it may potentially lock the UI for a long period of time". I found and used a [tutorial](https://www.youtube.com/playlist?list=PLrnPJCHvNZuDihTpkRs6SpZhqgBqPU118) to help me gain a deeper understanding. I chose to use LiveData and RxJava Observables instead of the other options (AsyncTask, using runInTransaction method, or  allowMainThreadQueries) because I thought that was best practice instead of what the tutorial and the CodePath guide suggested.

## License

Copyright [2018] [Lily Tran]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
