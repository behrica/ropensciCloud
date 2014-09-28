# ropen-sci-cloud

This is a web based tool, which allows to spin up cloud based virtual machines from existing (RStudio based) docker images.

The primary use case is the easy sharing of docker images which have been based on the ropensci RStudio image.
(available at https://registry.hub.docker.com/u/cboettig/ropensci/)

The application is currently hosted on a heroku, accessible here: [https://blooming-lake-3277.herokuapp.com]


The current features list is very limited. The application takes as input a docker file name, such as:
"mgymrek/docker-reproducibility-example" and a digital cloud api V2 token.

It creates then a new droplet in digitalocean based on CoreOS and starts automatically a docker image with the specified name.
It can take some minutes until RStudio is up and ruunning, even when teh Droplet is active already. Docker needs to download some staff.


The user can find the IP of the new droplet on his digitalocean dashboard. Sever name is by now: coreos-ropensci
The port of Rstudio is by now 49000 (mapped from 8787 of teh docker container based on RStudio)

## Planned enhancements:
- more configurations in web interface (digitialocean droplets specifications, ports, Rstudio user names)
- support other images registries then docker hub
- eventually wait until everything is up
- print ip adress and port of Droplet at the end
- support multiple cloud providers (at least Amazon EC2)


## Prerequisites

You will need [Leiningen][1] 1.7.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

## License

Copyright © 2014 FIXME
