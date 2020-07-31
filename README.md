# Bookings App
App that allows management of Users, Rooms and Bookings on a specific database. Database connection is done via PostgreSQL.
This can also work as a web app using the `LISTEN {port}` command. It is also ready to be hosted online using Heroku.

## Database Connection
Connection to the database is done via loading an environment variable containing the connection URL called `JDBC_DATABASE_URL`.

## HTML DSL
This app uses a Java HTML DSL that's also available in a [separate repo](https://github.com/PTKickass/JavaHtmlDSL)
