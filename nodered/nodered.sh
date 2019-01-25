chmod -R 0777 $(pwd)

NODEREDNAME=orderbread-nodered

echo '1 - to start and 2 - to stop and delete container: '
read option

if [ $option -eq '1' ]
then
    # Create a new node red container
    # docker run -it -p 1880:1880 -e FLOWS=$(pwd)/my_flows.json --name NODEREDNAME -d nodered/node-red-docker
    docker run -it -p 1880:1880 -v "$(pwd)/data":/data --name $NODEREDNAME -d nodered/node-red-docker
    
    # Open a shell in the container
    docker exec -it $NODEREDNAME bash -c "cd /data & npm install node-red-node-mongodb"

    # Restart the container to load the new nodes
    docker stop $NODEREDNAME
    docker start $NODEREDNAME
else
    docker container stop $NODEREDNAME
    docker container rm $NODEREDNAME
fi