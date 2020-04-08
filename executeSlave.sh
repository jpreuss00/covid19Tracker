#!/bin/bash

echo -e "\n\033[35;1;4mStarting slave work...\033[0m"
echo -e "\n\033[34;1;4mGradle build...\033[0m"
gradle build
echo -e "\n\033[34;1;4mDocker build...\033[0m\n"
docker build -t jpreuss00/covid19tracker .
echo -e "\n\033[34;1;4mHeroku Login...\033[0m\n"

echo -e "\n\033[34;1;4mHeroku Container Login...\033[0m\n"
heroku container:login
echo -e "\n\033[34;1;4mPushing image to Heroku...\033[0m\n"
heroku container:push web --app enigmatic-retreat-23877
echo -e "\n\033[34;1;4mReleasing image to Heroku...\033[0m\n"
heroku container:release web --app enigmatic-retreat-23877
echo -e "\n\033[34;1;4mShowing logs...\033[0m\n"
heroku logs --tail --app enigmatic-retreat-23877
echo -e "\n\033[35;1;4mFinishing slave work...\033[0m\n"