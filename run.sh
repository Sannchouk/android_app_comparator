#!/bin/bash

# Define Docker image and container name
IMAGE_NAME="app-comparator-image"
CONTAINER_NAME="app-comparator-container"
RESULTS_FILE_NAME="results"

# Check if a container with the same name already exists
echo "Checking if container ${CONTAINER_NAME} exists..."
if [ "$(docker ps -a -q -f name=${CONTAINER_NAME})" ]; then
    echo "Stopping and removing existing container ${CONTAINER_NAME}"
    docker stop ${CONTAINER_NAME} > /dev/null
    docker rm ${CONTAINER_NAME} > /dev/null
fi

# Build the Docker image
echo "Building Docker image ${IMAGE_NAME}"
docker build -t ${IMAGE_NAME} .

# Run the Docker container
echo "Running Docker container ${CONTAINER_NAME}"
docker run -d --name ${CONTAINER_NAME} ${IMAGE_NAME}

# Wait for the container to finish
echo "Waiting for the container to finish..."
docker wait ${CONTAINER_NAME}

# Copy the results file from the container to the current folder
echo "Copying results file from container ${CONTAINER_NAME}"
docker cp ${CONTAINER_NAME}:/app/${RESULTS_FILE_NAME} /tmp

echo "Docker container ${CONTAINER_NAME} finished successfully!"

echo "Retrieving file..."
mv /tmp/snap-private-tmp/snap.docker/tmp/${RESULTS_FILE_NAME} ${RESULTS_FILE_NAME}