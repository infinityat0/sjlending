#!/usr/bin/env bash

export PASSWORD="Sujji12!1"

# Usage
# ./create_table customers local
# ./create_table customers dev
# ./create_table customers prod

if [ -z "$1" ]; then
  echo "Please provide the table name in the first parameter"
  exit 1
elif [ -z "$2" ]; then
  echo "Please provide the environment name in the second parameter"
  exit 1
elif [ "$2" = "local" ]; then
  export TABLE_NAME="$2_$1"
  export DATABASE="$2"
  export USER="root"
  export HOST="localhost"
elif [ "$2" = "prod" ]; then
  export TABLE_NAME="$2_$1"
  export DATABASE="$2"
  export USER="sjlending"
  export HOST="sjlending.com"
else
  export TABLE_NAME="dev_$1"
  export DATABASE="dev"
  export HOST="sjlending.com"
fi

tmpfile=$(mktemp /tmp/mysql_"${TABLE_NAME}".XXXXXX)
cat <./create-"${1}".sql | sed "s/__DB_NAME__/${DATABASE}/g"| sed "s/__TABLE_NAME__/${TABLE_NAME}/g" >"$tmpfile"
echo "-------- Creating table -------------"
cat "$tmpfile"
mysql --host $HOST --port 3306 -u $USER -p < "$tmpfile"
rm "$tmpfile"
