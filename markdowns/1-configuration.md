# Configuration

To fulfill our tests cases we will need some basis configurations. We will work with 3 MySQL instances, 
one `central` and two `local` databases.

For a quick and easy configuration I work with Docker.

## Data sample

For the use I will consider using 3 databases.

- A `central` database that contains a list of cities, with their localization, either `EU` or `US`.
- A `EU` database that contains the cities with general information (country, area, population)
- A `US` database that contains the cities with general information (country, area, population)

### JPA entities

All I need is to handle 2 entities, `CitiesDirectory` which is refering to a `central` database's table, 
and `City` refering to a `local` database's table.

```kotlin
@Entity
@Table(name = "cities_directory")
data class CitiesDirectory(
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  val id: Int = -1,
  val city: String = "",
  @Enumerated(EnumType.STRING)
  val region: Region = Region.UNKNOWN
)

@Entity
@Table(name = "cities")
data class City(
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  val id: Int = -1,
  val city: String = "",
  @Enumerated(EnumType.STRING)
  val region: Region = Region.UNKNOWN,
  val country: String = "",
  val area: Double = 0.0,
  val population: Long = 0
)

enum class Region { EU, US, UNKNOWN }
```

### Spring repositories

For the exercice I just need a `JpaRepository` for each entity.

```kotlin
@Repository interface CitiesDirectoryRepository : JpaRepository<CitiesDirectory, Int>
@Repository interface CityRepository : JpaRepository<City, Int>
```

## Databases installation

To quickly have a consistent environment I use Docker and MySQL to prepare my data samples.

> You need to have [Docker](https://docs.docker.com/install/) installed

To (pull and) start a new MySQL instance we need to run the following command

> docker run \
> --detach \
> --name=my-mysql \
> --env="MYSQL_ALLOW_EMPTY_PASSWORD=true" \
> --env="MYSQL_DATABASE=my-database" \
> --publish 6603:3306 \
> mysql

If you already have run this instance just start it with the command:

 > $ docker start my-mysql

For more details I invite you to visit the [Docker Hub](https://hub.docker.com/r/mysql/mysql-server/)'s page.

### Central database

To start a new instance of the `central` database, run the command:

> $ docker run \
> --detach \
> --name=central-mysql \
> --env="MYSQL_ALLOW_EMPTY_PASSWORD=true" \
> --env="MYSQL_DATABASE=centralshard" \
> --publish 3301:3306 \
> mysql

You can now connect to your MySQL instance with any client, and populate the database by executing the following code:

```sql
CREATE TABLE cities_directory
(
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    city varchar(25) NOT NULL,
    region varchar(2) NOT NULL
);
CREATE UNIQUE INDEX cities_directory_uindex ON cities_directory (city, region);

INSERT INTO cities_directory (city, region) VALUES ('London', 'EU');
INSERT INTO cities_directory (city, region) VALUES ('Paris', 'EU');
INSERT INTO cities_directory (city, region) VALUES ('Brussels', 'EU');
INSERT INTO cities_directory (city, region) VALUES ('Berlin', 'EU');
INSERT INTO cities_directory (city, region) VALUES ('Barcelona', 'EU');
INSERT INTO cities_directory (city, region) VALUES ('Roma', 'EU');
INSERT INTO cities_directory (city, region) VALUES ('Olso', 'EU');
INSERT INTO cities_directory (city, region) VALUES ('Praha', 'EU');
INSERT INTO cities_directory (city, region) VALUES ('New-York', 'US');
INSERT INTO cities_directory (city, region) VALUES ('Washington', 'US');
INSERT INTO cities_directory (city, region) VALUES ('Seatle', 'US');
INSERT INTO cities_directory (city, region) VALUES ('Dallas', 'US');
INSERT INTO cities_directory (city, region) VALUES ('Chicago', 'US');
INSERT INTO cities_directory (city, region) VALUES ('Miami', 'US');
INSERT INTO cities_directory (city, region) VALUES ('San Fransisco', 'US');
```

To check the data run:
> SELECT * FROM cities_directory;

### European database

To start a new instance of the `EU` database, run the command:

> $ docker run \
> --detach \
> --name=eu-mysql \
> --env="MYSQL_ALLOW_EMPTY_PASSWORD=true" \
> --env="MYSQL_DATABASE=eushard" \
> --publish 3302:3306 \
> mysql

You can now connect to your MySQL instance with any client, and populate the database by executing the following code:

```sql
CREATE TABLE cities
(
  id         int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  city       varchar(25)     NOT NULL,
  country    varchar(25)     NOT NULL,
  area       double          NOT NULL,
  population long            NOT NULL,
  region     varchar(2)      NOT NULL
);
CREATE UNIQUE INDEX cities_uindex
  ON cities (city, region, country);

INSERT INTO cities (city, country, area, population, region) VALUES ('London', 'England', 1737.9, 8787892, 'EU');
INSERT INTO cities (city, country, area, population, region) VALUES ('Paris', 'France', 2845, 2206488, 'EU');
INSERT INTO cities (city, country, area, population, region) VALUES ('Brussels', 'Belgium', 161.38, 1175173, 'EU');
INSERT INTO cities (city, country, area, population, region) VALUES ('Berlin', 'Germany', 891.7, 3711930, 'EU');
INSERT INTO cities (city, country, area, population, region) VALUES ('Barcelona', 'Spain', 101.4, 1620809, 'EU');
INSERT INTO cities (city, country, area, population, region) VALUES ('Rome', 'Italy', 1285, 2873874, 'EU');
INSERT INTO cities (city, country, area, population, region) VALUES ('Olso', 'Norway', 480.76, 673469, 'EU');
INSERT INTO cities (city, country, area, population, region) VALUES ('Praha', 'Czech Republic', 496, 1280508, 'EU');
```

### US database

To start a new instance of the `US` database, run the command:

> $ docker run \
> --detach \
> --name=us-mysql \
> --env="MYSQL_ALLOW_EMPTY_PASSWORD=true" \
> --env="MYSQL_DATABASE=usshard" \
> --publish 3303:3306 \
> mysql

You can now connect to your MySQL instance with any client, and populate the database by executing the following code:

```sql
CREATE TABLE cities
(
  id         int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  city       varchar(25)     NOT NULL,
  country    varchar(25)     NOT NULL,
  area       double          NOT NULL,
  population long            NOT NULL,
  region     varchar(2)      NOT NULL
);
CREATE UNIQUE INDEX cities_uindex
  ON cities (city, region, country);

INSERT INTO cities (city, country, area, population, region) VALUES ('New-York', 'United-States', 1213.37, 8622698, 'US');
INSERT INTO cities (city, country, area, population, region) VALUES ('Washington', 'United-States', 177, 693972, 'US');
INSERT INTO cities (city, country, area, population, region) VALUES ('Seatle', 'United-States', 369.2, 713700, 'US');
INSERT INTO cities (city, country, area, population, region) VALUES ('Dallas', 'United-States', 999.3, 1317929, 'US');
INSERT INTO cities (city, country, area, population, region) VALUES ('Chicago', 'United-States', 606.42, 2704958, 'US');
INSERT INTO cities (city, country, area, population, region) VALUES ('Miami', 'United-States', 145.20, 453579, 'US');
INSERT INTO cities (city, country, area, population, region) VALUES ('San Fransisco', 'United-States', 600.59, 884363, 'US');
```

Up next, we'll get into the subject of using databases with SpringBoot.