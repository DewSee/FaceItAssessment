The project uses Gradle 4.10.3.

Run:

$ gradle clean

$ gradle build

$ gradle run



Get all users:

$ curl -v localhost:8080/users

OR

Go to: http://localhost/users

Get a specific user (by nickname):

$ curl -v localhost:8080/users/{nickName}

OR

Go to: http://loalhost/users/{nickName}

Create a new user:

$ curl -X POST localhost:8080/users -H 'Content-type:application/json' -d '{"firstName":"Jeno","lastName":"Polgar","nickName":"PolgarJeno","password":"password","email":"polgarur@gmail.com","country":"Hungary"}'

Update an existing user:

$ curl -X PUT localhost:8080/users/update/{nickName} -H 'Content-type:application/json' -d '{"firstName":"Jeno","lastName":"Polgar","nickName":"PolgarJeno","password":"password","email":"polgarur@gmail.com","country":"Hungary"}'

Delete user:

curl -X DELETE localhost:8080/users/{nickName}
