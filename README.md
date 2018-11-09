# Project 1 - *To Do*

**To Do** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Lily Tran**

Time spent: **2.1** hours spent in total

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
* [ ] Add support for completion due dates for todo items (and display within listview item)
* [ ] Use a DialogFragment instead of new Activity for editing items
* [ ] Improve the UI / UX of your app including icons, styling, color, spacing of your app.
* [x] Add support for selecting the priority of each todo item (and display in listview item)
* [x] Users can delete/clear all todo items
* [x] Users can cancel adding/editing items
* [ ] Anything else that you can get done to improve the app functionality or user experience!

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://i.imgur.com/A4ISkE5.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

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
