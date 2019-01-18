chmod -R 0777 $(pwd)/mongodata
docker run --name orderbread-mongo -v $(pwd)/mongodata/:/etc/mongo -p 27017:27017 -d mongo:3.6.9
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' orderbread-mongo