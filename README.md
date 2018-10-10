# blog-formation-backend
Backend java servant de support aux TP des formations de dev front

## Description

A Java REST Api that provides services for a blog.

This repository is used to make web (frontend) workshop easier with an working Api.

The project is develop in Java 1.8

We use Jooby ([Link](https://jooby.org)) as Web Framework for the Api.  
We use MongoDB ([Link](https://www.mongodb.com)) as data base.

The definition of the routes:
```
*      /**                                  
GET    /                                   
GET    /api/blogpost                       
POST   /api/blogpost                        
PUT    /api/blogpost                        
GET    /api/blogpost/:id                   
DELETE /api/blogpost/:id                    
DELETE /api/blogpost                        
GET    /api/blogpost/autor/:name            
GET    /api/blogpost/:blogId/comment        
POST   /api/blogpost/:blogId/comment        
GET    /api/blogpost/:blogId/comment/:id    
DELETE /api/blogpost/:blogId/comment  
```

*Incomming:* The swagger of the Api.

## Installation

First, you have to install and start a MongoDB service.  
Check their [documentation](https://docs.mongodb.com/manual/tutorial/getting-started/) for this point.

Then, you can start your application.  
We use Maven to manage our dependencies and tasks.  
In your IDE, you can 'Run' the 'App.java' if you have maven plugin.  
Otherwise, you can install Maven and then run `mvn jooby:run`.  