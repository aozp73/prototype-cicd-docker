mysql -u root -p$MYSQL_ROOT_PASSWORD -e "CREATE DATABASE ${MYSQL_DATABASE};"

mysql -u root -p$MYSQL_ROOT_PASSWORD -e "GRANT ALL PRIVILEGES ON ${MYSQL_DATABASE}.* TO $MYSQL_USER@'%';"

mysql -u $MYSQL_USER -p$MYSQL_PASSWORD -e "USE $MYSQL_DATABASE;"