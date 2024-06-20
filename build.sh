git pull
mvn clean
mvn install
docker build . -t simple-api-loop
docker login
docker push marioromeu / java-image-simple-api-loop