echo "**********SETTING UP ENVIRONMENT***********";
hostname -I |
  {
    echo "API_HOST="'"'"http://";
    grep -Eo "^[0-9|"."]*" ;
    echo ":4567"'"'
  } | tr '\n' ' ' |
   tr -d ' ' >.env;
   echo "*********SETTING UP CLIENT**************";
  expo start
