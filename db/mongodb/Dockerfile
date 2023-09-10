FROM mongo:4.4

COPY ./00-db-setting.sh /docker-entrypoint-initdb.d/
COPY ./01-data-setting.js /docker-entrypoint-initdb.d/

RUN chmod 755 /docker-entrypoint-initdb.d/00-db-setting.sh

EXPOSE 27017