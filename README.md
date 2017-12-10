#Brain Extension

A knoweldge base written in Java

##Overall Objective

There are applications like Evernote. Howver, I started my own application approximately 17 years ago. At that time I built it in Perl. It had a range of modules like: 

- send and receive mails
- mind map drawing
- to do list and prioritization etc. 
- show and track investment portfolio in reeal time. 

Recently I decided to build the application from scratch again. This time as a single page application. I wanted to reduce the functionality to the absolute essence and have an extremely simple GUI. 

##Learning Objective

I wanted to use this application as an opportunity to improve my coding skills as I did not code for a while. I started to use IntelliJ instead of Netbeans and will use soon Elasticsearch in parallel to MySQL

##Architecture

The single page application is organized in a simple way:

When accessing the webpage, the server sends some HTML code that does nothign else than load a few JavaScript files. Each file represents a Class. 

- ajax: a Class for creating the AJAX requests
- controller: to manage the communication between the gui and the backend
- UxUi; A class for creating HTML elements and compontens

On the backend a simple layer architecture is used. Almost all communication between frontend and backend is handled via JSON objects: 

- As I want to understand web server construction better, i use a simple Java HTTP(s) server, instead of Apache or Nginx. 
- requests are then distributed into specific request ahndlers. 
- the handlers are then talking to a business layer where the business logic for a specific request is defined
- the business layer uses the data access layer to communicate with the database and the file system. 

##Roadmap

I use the application each and every day. To use it even more, a few modifications need to be made: 

- to handle more information and get search ansers quicly, Elasticsearch will be used
- to have unlimited space the files will be stored in Amazon Web Services S3. 
- Drag and drop functionality to arrange elements
- modify the file upload so that large files >50MB can be uploaded. Right now files are getting uploaded via JSON objects and AJAX and that does not work with big files
